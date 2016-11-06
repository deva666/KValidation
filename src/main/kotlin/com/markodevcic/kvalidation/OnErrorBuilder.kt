package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.DefaultMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

class OnErrorBuilder<T, TFor>(private val propertyContext: PropertyContext<T, TFor>) {

    infix fun errorMessage(message: String): OnErrorBuilder<T, TFor> {
        val messageBuilder = DefaultMessageBuilder(message)
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorMessage(messageBuilder: MessageBuilder): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorCode(code: Int): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    infix fun errorLevel(errorLevel: ErrorLevel): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }

    infix fun propertyName(name: String): OnErrorBuilder<T, TFor> {
        propertyContext.propertyName = name
        return this
    }
}