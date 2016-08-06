package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.errors.MessageBuilder
import com.markodevcic.kvalidation.validators.Validator

@Suppress("UNCHECKED_CAST")
class OptionsBuilder<T, TFor>(private val valueContext: ValueContext<T, TFor>,
                              private val currentValidator: Validator) {

    fun errorMessage(message: String): OptionsBuilder<T, TFor> {
        currentValidator.errorMessage = message
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): OptionsBuilder<T, TFor> {
        currentValidator.messageBuilder = messageBuilder
        return this
    }

    fun errorCode(code: Int): OptionsBuilder<T, TFor> {
        currentValidator.errorCode = code
        return this
    }

    fun whenIs(precondition: (T) -> Boolean): OptionsBuilder<T, TFor> {
        currentValidator.precondition = { c -> precondition.invoke(c as T) }
        return this
    }

    fun errorLevel(errorLevel: ErrorLevel): OptionsBuilder<T, TFor> {
        currentValidator.errorLevel = errorLevel
        return this
    }

    fun and(): RuleBuilder<T, TFor> {
        return RuleBuilder(valueContext)
    }
}