package com.fireblade.bitcoinorderbook.ui

import com.fireblade.bitcoinorderbook.R
import com.fireblade.bitcoinorderbook.business.OrderBookItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.order_list_item.view.*

class OrderBookViewItem(private val orderBookItem: OrderBookItem): Item() {

    override fun getLayout(): Int = R.layout.order_list_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            when(orderBookItem) {
               is OrderBookItem.Bid -> {
                   order_amount.text = orderBookItem.amount.toBigDecimal().setScale(8).toString()
                   order_price.text = orderBookItem.price.toBigDecimal().toString()
                    order_price.setTextColor(resources.getColor(R.color.colorPositiveGreen, null))
                }
                is OrderBookItem.Ask -> {
                    order_amount.text = orderBookItem.amount.toBigDecimal().setScale(8).toString()
                    order_price.text = orderBookItem.price.toBigDecimal().toString()
                    order_price.setTextColor(resources.getColor(R.color.colorNegativeRed, null))
                }
            }
        }
    }
}