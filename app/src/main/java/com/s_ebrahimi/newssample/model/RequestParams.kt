package com.s_ebrahimi.newssample.model

/**
 * Model for request parameters
 * Class variables are sent to api to filter the response based on category, query, country and language
 * Since setting at least on param is needed to fetch data successfully, language is set to "en" by default
 */
data class RequestParams(
    var category: String = "",
    var query: String = "",
    var country: String ="",
    var language: String ="en"
) {

    constructor():this("","","","en")

    override fun toString(): String {
        return "RequestParams(category='$category', query='$query', country='$country', language='$language')"
    }
}