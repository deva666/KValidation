package com.markodevcic.kvalidation.validators

class NullValidator : ValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return result == null
    }

    override fun toString(): String {
        return "Null validator"
    }
}