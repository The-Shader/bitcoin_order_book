package com.fireblade.bitcoinorderbook.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.babylon.orbit2.livedata.state
import com.fireblade.bitcoinorderbook.R
import com.fireblade.bitcoinorderbook.business.MarketInfoScreenState
import com.fireblade.bitcoinorderbook.business.MarketInfoViewModel
import com.fireblade.bitcoinorderbook.business.OrderBookItem
import com.fireblade.bitcoinorderbook.business.Ticker
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_market_info.*
import java.math.BigDecimal
import javax.inject.Inject

class MarketInfoFragment : Fragment() {

    @Inject
    lateinit var viewModel: MarketInfoViewModel

    private lateinit var gestureDetectorCompat: GestureDetectorCompat

    private val bidsAdapter: GroupAdapter<GroupieViewHolder> by lazy { GroupAdapter<GroupieViewHolder>() }

    private val asksAdapter: GroupAdapter<GroupieViewHolder> by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gestureDetectorCompat = GestureDetectorCompat(
            activity,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent?): Boolean {
                    viewModel.refresh()
                    viewModel.loadTickerInfo()
                    viewModel.loadOrderBook()
                    return super.onDown(e)
                }
            }
        )
        return inflater.inflate(R.layout.fragment_market_info, container, false).apply {
            setOnTouchListener { v, event ->
                gestureDetectorCompat.onTouchEvent(event)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_of_bids.adapter = bidsAdapter
        list_of_asks.adapter = asksAdapter

        viewModel.container.state.observe(viewLifecycleOwner, {
            render(it)
        })
        viewModel.loadTickerInfo()
        viewModel.loadOrderBook()
    }

    private fun render(screenState: MarketInfoScreenState) {
        when(screenState) {
            MarketInfoScreenState.Loading -> progress_bar.visibility = View.VISIBLE
            is MarketInfoScreenState.Success -> showInfo(screenState)
            is MarketInfoScreenState.Error -> showError(screenState)
        }
    }

    private fun showInfo(screenState: MarketInfoScreenState.Success) {
        progress_bar.visibility = View.GONE

        renderTickerSection(screenState.ticker)
        setBids(screenState.bids)
        setAsks(screenState.asks)
    }

    private fun renderTickerSection(ticker: Ticker) {
        ticker_price.text = getString(R.string.btc_usd_price, ticker.price)

        if (ticker.changeInPercent >= BigDecimal.ZERO) {
            ticker_price_change.text = getString(R.string.price_change_up, ticker.changeInPercent)
            ticker_price_change.setTextColor(
                resources.getColor(
                    R.color.colorPositiveGreen,
                    null
                )
            )
        } else {
            ticker_price_change.text = getString(R.string.price_change_down, ticker.changeInPercent)
            ticker_price_change.setTextColor(
                resources.getColor(
                    R.color.colorNegativeRed,
                    null
                )
            )
        }

        ticker_volume.text = getString(R.string.ticker_volume, ticker.volume)
        ticker_price_low.text = getString(R.string.ticker_price_low, ticker.low)
        ticker_price_high.text = getString(R.string.ticker_price_high, ticker.high)
    }

    private fun setBids(bids: List<OrderBookItem>) {
        bidsAdapter.clear()
        bidsAdapter.addAll(
            bids.map { OrderBookViewItem(it) }
        )
    }

    private fun setAsks(asks: List<OrderBookItem>) {
        asksAdapter.clear()
        asksAdapter.addAll(
            asks.map { OrderBookViewItem(it) }
        )
    }

    private fun showError(screenState: MarketInfoScreenState.Error) {
        progress_bar.visibility = View.GONE
        Toast.makeText(context, screenState.error, Toast.LENGTH_SHORT).show()
    }
}