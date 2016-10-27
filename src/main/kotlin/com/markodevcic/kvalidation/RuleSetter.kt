package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.*

@Suppress("UNCHECKED_CAST")
class RuleSetter<T, TFor>(private val valueContext: ValueContext<T, TFor>) {
    private var currentValidator: Validator? = null

    private fun setValidator(validator: Validator) {
        currentValidator = validator
        valueContext.validators.add(validator)
    }

    infix fun mustBe(validator: Validator) {
        setValidator(validator)
    }

    infix fun mustBe(predicate: (TFor?) -> Boolean) {
        val validator = object : ValidatorBase() {
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidator(validator)
    }

    fun nonNull() {
        setValidator(NonNullValidator())
    }

    fun isNull() {
        if (valueContext.validators.size != 0) {
            throw IllegalStateException("can't set is null validator with other validators")
        }
        setValidator(NullValidator())
    }

    infix fun length(max: Int) {
        setValidator(LengthValidator(0, max))
    }

    fun length(min: Int, max: Int) {
        setValidator(LengthValidator(min, max))
    }

    infix fun equal(other: TFor) {
        setValidator(EqualValidator(other))
    }

    infix fun notEqual(other: TFor) {
        setValidator(NotEqualValidator(other))
    }

    infix fun lt(other: Number) {
        setValidator(LesserThanValidator(other))
    }

    infix fun gt(other: Number) {
        setValidator(GreaterThanValidator(other))
    }

    infix fun lte(other: Number) {
        setValidator(LesserOrEqualThanValidator(other))
    }

    infix fun gte(other: Number) {
        setValidator(GreaterOrEqualThanValidator(other))
    }

    infix fun isContainedIn(source: Collection<TFor>) {
        setValidator(ContainsValidator(source))
    }

    fun inRange(min: Number, max: Number) {
        setValidator(RangeValidator(min, max))
    }

    infix fun whenIs(precondition: (T) -> Boolean) {
        currentValidator?.precondition = { c -> precondition.invoke(c as T) }
    }

    infix fun whenIsOnAll(precondition: (T) -> Boolean) {
        valueContext.validators.forEach { v ->
            v.precondition = { c -> precondition.invoke(c as T) }
        }
    }

}