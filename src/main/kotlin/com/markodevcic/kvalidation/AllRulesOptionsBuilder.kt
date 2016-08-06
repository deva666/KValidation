package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.CustomMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

@Suppress("UNCHECKED_CAST")
class AllRulesOptionsBuilder<T, TFor>(private val valueContext: ValueContext<T, TFor>) {

    fun errorMessage(message: String): AllRulesOptionsBuilder<T, TFor> {
        val builder = CustomMessageBuilder(message)
        valueContext.validators.forEach { v -> v.messageBuilder = builder}
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    fun errorCode(code: Int): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    fun whenIs(condition: (T) -> Boolean): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.precondition = { c -> condition.invoke(c as T)} }
        return this
    }

    fun errorLevel(errorLevel: ErrorLevel): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }
}
