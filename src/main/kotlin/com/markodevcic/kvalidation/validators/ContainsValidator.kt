package com.markodevcic.kvalidation.validators

class ContainsValidator<out TFor>(val source: Collection<TFor>) : ValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        return source.contains(result)
    }

    override fun toString(): String {
        return "Lesser than validator, expected value to be in collection $source"
    }
}