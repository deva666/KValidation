package com.markodevcic.kvalidation.validators

class LesserThanValidator(private val target: Number) : ValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result is Number) {
            return result.toDouble() < target.toDouble()
        } else {
            throw IllegalArgumentException("Lesser than validator can operate only on numbers")
        }
    }

    override fun toString(): String {
        return "Lesser than validator, expected value to be less than: $target"
    }
}