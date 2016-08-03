package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.NonNullValidator
import com.markodevcic.kvalidation.validators.NullValidator
import com.markodevcic.kvalidation.validators.Validator
import com.markodevcic.kvalidation.validators.ValidatorBase

@Suppress("UNCHECKED_CAST")
class RuleBuilder<T, TFor> {
    private val propertyContext: PropertyContext<T, TFor>
    private var currentValidator: Validator? = null

    internal constructor(propertyContext: PropertyContext<T, TFor>) {
        this.propertyContext = propertyContext
    }

    private fun setValidator(validator: Validator) {
        currentValidator = validator
        propertyContext.validators.add(validator)
    }

    fun mustBe(validator: Validator): OptionsBuilder<T, TFor> {
        setValidator(validator)
        return OptionsBuilder(propertyContext, currentValidator!!)
    }

    fun mustBe(predicate: (TFor?) -> Boolean): OptionsBuilder<T, TFor> {
        val validator = object : ValidatorBase(){
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidator(validator)
        return OptionsBuilder(propertyContext, currentValidator!!)
    }

    fun nonNull(): OptionsBuilder<T, TFor> {
        setValidator(NonNullValidator())
        return OptionsBuilder(propertyContext, currentValidator!!)
    }

    fun isNull(): OptionsBuilder<T, TFor> {
        setValidator(NullValidator())
        return OptionsBuilder(propertyContext, currentValidator!!)
    }

    fun equal(other: TFor): OptionsBuilder<T, TFor> {

        return OptionsBuilder(propertyContext, currentValidator!!)
    }

    fun notEqual(other: TFor): OptionsBuilder<T, TFor> {

        return OptionsBuilder(propertyContext, currentValidator!!)
    }
}
