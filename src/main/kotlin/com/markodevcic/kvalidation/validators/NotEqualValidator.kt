package com.markodevcic.kvalidation.validators

internal class NotEqualValidator(private val other: Any?) : PropertyValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return result != other
    }

    override fun toString(): String {
        return "Not equal validator, expected not to be equal to: $other"
    }
}