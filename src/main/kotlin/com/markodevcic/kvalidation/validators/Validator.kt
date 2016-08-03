package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel

interface Validator{
    fun isValid(result: Any?): Boolean
    var precondition: (Any) -> Boolean
    var errorMessage: String?
    var errorMessageId: Int?
    var errorCode: Int?
    var errorLevel: ErrorLevel
}