package com.markodevcic.kvalidation.validators

internal class NullValidator : PropertyValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return result == null
    }

    override fun toString(): String {
        return "Null validator"
    }
}