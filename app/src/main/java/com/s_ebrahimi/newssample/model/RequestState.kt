package com.s_ebrahimi.newssample.model

/**
 * Model for api requests status which carries information of request
 * in four fields : status, error type, error code and error message
 */
class RequestState {

    companion object {
        val STATE_DEFAULT = 0
        val STATE_LOADING = 1
        val STATE_SUCCESS = 2
        val STATE_FAILURE = 3
        val STATE_NO_MORE_DATA = 4

        val ERROR_TYPE_CONNECTION = 1
        val ERROR_TYPE_BAD_REQUEST = 2
        val ERROR_TYPE_UNKNOWN = 3

        val MESSAGE_UNKNOWN_ERROR= "Unknown Error..."
        val MESSAGE_NO_MORE_DATA= "No more articles available"
    }

    var state: Int = STATE_DEFAULT
    var errorMessage: String? = null
    var errorCode: String? = null
    var errorType: Int? = 0

    constructor(state: Int) {
        this.state = state
        if(this.state == STATE_NO_MORE_DATA)
            this.errorMessage = MESSAGE_NO_MORE_DATA
    }

    constructor() {
        this.state = STATE_DEFAULT
    }

    /**
     * sets variables when a failure happens
     * @param errorType : type of occured error
     * @param errorCode : error code
     * @param message : message of occured error
     */
    fun setFailureState(errorType: Int, errorCode: String?, message: String?) {
        this.state = STATE_FAILURE
        this.errorType = errorType
        this.errorCode = errorCode
        this.errorMessage = message
    }

    override fun toString(): String {
        return "RequestState(state=$state, errorMessage=$errorMessage, errorCode=$errorCode, errorType=$errorType)"
    }
}