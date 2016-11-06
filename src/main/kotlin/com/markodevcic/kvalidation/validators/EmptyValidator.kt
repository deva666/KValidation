package com.markodevcic.kvalidation.validators

internal class EmptyValidator : PropertyValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result == null) {
            return true
        } else if (result is Iterable<*>) {
            return !result.any()
        } else {
            return false
        }
    }

    override fun toString(): String {
        return "Empty validator"
    }
}