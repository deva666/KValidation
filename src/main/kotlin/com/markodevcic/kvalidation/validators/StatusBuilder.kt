package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.RuleBuilder
import com.markodevcic.kvalidation.ValueContext
import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.CustomMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

class StatusBuilder<T, TFor>(protected val valueContext: ValueContext<T, TFor>) {

    infix fun errorMessage(message: String): StatusBuilder<T, TFor> {
        val messageBuilder = CustomMessageBuilder(message)
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorMessage(messageBuilder: MessageBuilder): StatusBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorCode(code: Int): StatusBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    infix fun errorLevel(errorLevel: ErrorLevel): StatusBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }
}