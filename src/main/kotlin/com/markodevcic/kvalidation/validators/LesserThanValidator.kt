package com.markodevcic.kvalidation.validators

class LesserThanValidator(private val target: Number) : ValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result is Number == false) {
            throw IllegalArgumentException("Lesser than validator can operate only on numbers")
        }
        return (result as Number).toDouble() < target.toDouble()
    }

    override fun toString(): String {
        return "Lesser than validator, expected value to be less than: $target"
    }
}