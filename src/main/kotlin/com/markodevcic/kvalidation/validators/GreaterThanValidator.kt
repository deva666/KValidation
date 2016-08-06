package com.markodevcic.kvalidation.validators

class GreaterThanValidator(private val target: Number): ValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        if (result is Number) {
            return result.toDouble() > target.toDouble()
        } else {
            throw IllegalArgumentException("Greater than validator can operate only on numbers")
        }
    }

    override fun toString(): String {
        return "Greater than validator, expected value to be greater than: $target"
    }
}