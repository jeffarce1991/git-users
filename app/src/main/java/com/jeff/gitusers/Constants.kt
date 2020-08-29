package com.jeff.template

class Constants private constructor() {
    object Gateways {
        const val COVID19API = "https://api.covid19api.com"
        const val JSONPLACEHOLDER = "https://jsonplaceholder.typicode.com"
    }
    object DaoExceptions {
        const val ERROR_EMPTY_RESULT = "Empty results[] from DAO request"
        const val ERROR_NULL_RESULT = "Null results[] from DAO request"
    }
}