package com.s_ebrahimi.newssample.util

/**
 * General configurations for app
 * Static constants which are used in entire app are defined in this class
 */
class Constants {
    companion object {

        const val API_KEY = "1982da221eb745e88f83160f3c3ac600"
        const val BASE_URL = "https://newsapi.org/v2/"

        // A message to show to follow newsapi.org rules
        const val API_SOURCE_MESSAGE = "This application is just a demo \n Powered By \n https://newsapi.org free API"

        /**
         * General network communication configuration
         */
        const val NETWORK_TIMEOUT_CONNECT = 20L
        const val NETWORK_TIMEOUT_READ = 30L
        const val NETWORK_TIMEOUT_WRITE = 15L

        /**
         * Page size of fetched data from API ( number of items to be fetched in each request )
         */
        const val PAGE_SIZE : Int = 20
    }
}