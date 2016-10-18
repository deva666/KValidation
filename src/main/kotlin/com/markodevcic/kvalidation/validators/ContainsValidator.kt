package com.markodevcic.kvalidation.validators

class ContainsValidator<out TFor>(val source: Collection<TFor>) : ValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return source.contains(result)
    }
}