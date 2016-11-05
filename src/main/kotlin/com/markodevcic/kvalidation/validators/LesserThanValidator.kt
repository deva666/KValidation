package com.markodevcic.kvalidation.validators

internal class LesserThanValidator(private val target: Number) : PropertyValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result is Number) {
            return result.toDouble() < target.toDouble()
        } else if (result == null) {
            return true
        } else {
            return false
        }
    }

    override fun toString(): String {
        return "Lesser than validator, expected value to be less than: $target"
    }
}