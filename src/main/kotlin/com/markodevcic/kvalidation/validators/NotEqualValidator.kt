package com.markodevcic.kvalidation.validators

class NotEqualValidator(private val other: Any) : ValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return !result!!.equals(other)
    }

    override fun toString(): String {
        return "Not equal validator, expected not to be equal to: ${other.toString()}"
    }
}