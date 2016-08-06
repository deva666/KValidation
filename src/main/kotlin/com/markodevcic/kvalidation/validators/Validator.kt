package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.MessageBuilder

interface Validator{
    fun isValid(result: Any?): Boolean
    var precondition: (Any) -> Boolean
    var errorMessage: String?
    var messageBuilder: MessageBuilder?
    var errorCode: Int?
    var errorLevel: ErrorLevel
}