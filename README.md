# bitcoin_order_book

## Project Notes

The following features and techniques have been implemented/used:
* Network caching with `OkHttp`
* `Retrofit` for Rest API
* MVI architecture is used in the feature modules for separating responsibilities, utilising [Orbit2](https://github.com/babylonhealth/orbit-mvi)
* Unit-tests with `JUnit5`, `Mockito` and `Mockito kotlin` for [MarketInfoViewModel](https://github.com/The-Shader/bitcoin_order_book/blob/main/app/src/test/java/com/fireblade/bitcoinorderbook/MarketInfoViewModelTest.kt)
* UI tests with `Espresso` and `Kakao` for [MarketInfoFragment](https://github.com/The-Shader/bitcoin_order_book/blob/main/app/src/androidTest/java/com/fireblade/bitcoinorderbook/MarketInfoScreenWalkthroughTest.kt)
* `Dagger2` for dependency injection
* `RxJava2` streams for async data loading both from the database and from the network
* Using `AndroidX` libraries

Missing/can be improved:
* Upgrading to `RxJava3`
* Better state handling with init-state and timeouts on failing to load
* Start using styles and themes
* Introduce `Dagger Hilt` for `AssistedInject` and `ViewModelInject`
* More unit-tests(testing the network service) and UI tests
* Bitrise integration for continuous integration and automatic deploy into the Play store
* Firebase integration for crash and user analytics
* More screens for more details

## Screenshot

![home page image](https://github.com/The-Shader/bitcoin_order_book/blob/main/screen.png)