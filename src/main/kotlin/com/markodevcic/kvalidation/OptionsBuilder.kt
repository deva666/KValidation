package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.errors.MessageBuilder

@Suppress("UNCHECKED_CAST")
class OptionsBuilder<T, TFor>(private val valueContext: ValueContext<T, TFor>) {

    fun errorMessage(message: String): OptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorMessage = message }
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): OptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    fun errorCode(code: Int): OptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    fun whenIs(precondition: (T) -> Boolean): OptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.precondition = { c -> precondition.invoke(c as T)} }
        return this
    }

    fun errorLevel(errorLevel: ErrorLevel): OptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }
}
