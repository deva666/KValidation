package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.NonNullValidator
import com.markodevcic.kvalidation.validators.NullValidator
import com.markodevcic.kvalidation.validators.Validator
import com.markodevcic.kvalidation.validators.ValidatorBase

@Suppress("UNCHECKED_CAST")
class RuleBuilder<T, TFor> {
    private val valueContext: ValueContext<T, TFor>
    private var currentValidator: Validator? = null

    internal constructor(valueContext: ValueContext<T, TFor>) {
        this.valueContext = valueContext
    }

    private fun setValidator(validator: Validator) {
        currentValidator = validator
        valueContext.validators.add(validator)
    }

    fun custom(validator: Validator): OptionsBuilder<T, TFor> {
        setValidator(validator)
        return OptionsBuilder(valueContext, currentValidator!!)
    }

    fun custom(predicate: (TFor) -> Boolean): OptionsBuilder<T, TFor> {
        val validator = object : ValidatorBase(){
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor)
            }
        }
        setValidator(validator)
        return OptionsBuilder(valueContext, currentValidator!!)
    }

    fun nonNull(): OptionsBuilder<T, TFor> {
        setValidator(NonNullValidator())
        return OptionsBuilder(valueContext, currentValidator!!)
    }

    fun isNull(): OptionsBuilder<T, TFor> {
        setValidator(NullValidator())
        return OptionsBuilder(valueContext, currentValidator!!)
    }

    fun equal(other: TFor): OptionsBuilder<T, TFor> {

        return OptionsBuilder(valueContext, currentValidator!!)
    }

    fun notEqual(other: TFor): OptionsBuilder<T, TFor> {

        return OptionsBuilder(valueContext, currentValidator!!)
    }
}
