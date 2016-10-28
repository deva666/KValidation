package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.MessageBuilder

interface PropertyValidator {
    fun isValid(result: Any?): Boolean
    var precondition: ((Any) -> Boolean)?
    var messageBuilder: MessageBuilder?
    var errorCode: Int?
    var errorLevel: ErrorLevel
}