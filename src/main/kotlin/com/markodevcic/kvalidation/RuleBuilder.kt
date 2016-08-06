package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.errors.MessageBuilder
import com.markodevcic.kvalidation.validators.*

@Suppress("UNCHECKED_CAST")
class RuleBuilder<T, TFor> {
    private val valueContext: ValueContext<T, TFor>
    private var currentValidator: Validator? = null
    private var setOnCurrent = true

    internal constructor(valueContext: ValueContext<T, TFor>) {
        this.valueContext = valueContext
    }

    private fun setValidator(validator: Validator) {
        currentValidator = validator
        valueContext.validators.add(validator)
    }

    fun mustBe(validator: Validator): RuleBuilder<T, TFor> {
        setValidator(validator)
        return this
    }

    fun mustBe(predicate: (TFor?) -> Boolean): RuleBuilder<T, TFor> {
        val validator = object : ValidatorBase() {
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidator(validator)
        return this
    }

    fun nonNull(): RuleBuilder<T, TFor> {
        setValidator(NonNullValidator())
        return this
    }

    fun isNull(): RuleBuilder<T, TFor> {
        setValidator(NullValidator())
        return this
    }

    fun length(max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(0, max))
        return this
    }

    fun length(min: Int, max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(min, max))
        return this
    }

    fun equal(other: TFor): RuleBuilder<T, TFor> {
        setValidator(EqualValidator(other!!))
        return this
    }

    fun notEqual(other: TFor): RuleBuilder<T, TFor> {
        setValidator(NotEqualValidator(other!!))
        return this
    }

    //options
    fun errorMessage(message: String): RuleBuilder<T, TFor> {
        if (setOnCurrent) {
            currentValidator?.errorMessage = message
        } else {
            valueContext.validators.forEach { v -> v.errorMessage = message }
        }
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): RuleBuilder<T, TFor> {
        if (setOnCurrent) {
            currentValidator?.messageBuilder = messageBuilder
        } else {
            valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        }
        return this
    }

    fun errorCode(code: Int): RuleBuilder<T, TFor> {
        if (setOnCurrent) {
            currentValidator?.errorCode = code
        } else {
            valueContext.validators.forEach { v -> v.errorCode = code }
        }
        return this
    }

    fun whenIs(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        if (setOnCurrent) {
            currentValidator?.precondition = { c -> precondition.invoke(c as T) }
        } else {
            valueContext.validators.forEach { v -> v.precondition = { c -> precondition.invoke(c as T) } }
        }
        return this
    }

    fun errorLevel(errorLevel: ErrorLevel): RuleBuilder<T, TFor> {
        if (setOnCurrent) {
            currentValidator?.errorLevel = errorLevel
        } else {
            valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
        }
        return this
    }

    fun onAll(): RuleBuilder<T, TFor> {
        setOnCurrent = false
        return this
    }
}
