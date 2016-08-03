package com.markodevcic.kvalidation.validators

class NonNullValidator : ValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        return result != null
    }
}