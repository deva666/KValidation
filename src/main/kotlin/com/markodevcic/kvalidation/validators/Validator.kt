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

interface GenericValidator<T>{
    fun isValid(result: T?): Boolean
//    var precondition: (T) -> Boolean
//    var errorMessage: String?
//    var errorMessageId: Int?
//    var errorCode: Int?
//    var errorLevel: ErrorLevel
}

class NumberValidator : GenericValidator<Number> {
    override fun isValid(result: Number?): Boolean {
        return true
    }
}

fun s(){
    val n = NumberValidator()
}


