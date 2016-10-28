package com.markodevcic.kvalidation.validators

class NonNullValidator : PropertyValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return result != null
    }

    override fun toString(): String {
        return "Non null validator"
    }
}