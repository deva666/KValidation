package com.markodevcic.kvalidation.validators

class LesserOrEqualThanValidator(private val target: Number): ValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result is Number) {
            return result.toDouble() <= target.toDouble()
        } else if (result == null) {
            return true
        } else {
            throw IllegalArgumentException("Lesser or equal than validator can operate only on numbers")
        }
    }

    override fun toString(): String {
        return "Lesser or equal than validator, expected value to be less than: $target"
    }
}