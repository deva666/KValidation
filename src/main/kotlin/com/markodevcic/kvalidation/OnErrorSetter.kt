package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.CustomMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

class OnErrorSetter<T, TFor>(private val valueContext: ValueContext<T, TFor>) {
    infix fun errorMessage(message: String) {
        val messageBuilder = CustomMessageBuilder(message)
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
    }

    infix fun errorMessage(messageBuilder: MessageBuilder) {
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
    }

    infix fun errorCode(code: Int) {
        valueContext.validators.forEach { v -> v.errorCode = code }
    }

    infix fun errorLevel(errorLevel: ErrorLevel) {
        valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
    }

    infix fun propertyName(name: String)  {
        valueContext.propertyName = name
    }
}