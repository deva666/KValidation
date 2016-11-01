package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.*
import java.util.regex.Pattern

@Suppress("UNCHECKED_CAST")
class RuleSetter<T, TFor>(private val propertyContext: PropertyContext<T, TFor>) {
    private var currentPropertyValidator: PropertyValidator? = null

    private fun setValidator(validator: PropertyValidator) {
        currentPropertyValidator = validator
        propertyContext.validators.add(validator)
    }

    infix fun mustBe(propertyValidator: PropertyValidator) {
        setValidator(propertyValidator)
    }

    infix fun mustBe(predicate: (TFor?) -> Boolean) {
        val validator = object : PropertyValidatorBase() {
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
        if (propertyContext.validators.size != 0) {
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

    fun email() {
        setValidator(EmailValidator())
    }

    infix fun pattern(pattern: Pattern) {
        setValidator(PatternValidator(pattern))
    }

    infix fun whenIs(precondition: (T) -> Boolean) {
        currentPropertyValidator?.precondition = { c -> precondition.invoke(c as T) }
    }

    infix fun whenIsOnAll(precondition: (T) -> Boolean) {
        propertyContext.validators.forEach { v ->
            v.precondition = { c -> precondition.invoke(c as T) }
        }
    }

}