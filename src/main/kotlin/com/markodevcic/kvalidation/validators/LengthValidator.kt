package com.markodevcic.kvalidation.validators

class LengthValidator(private val min: Int, private val max: Int) : ValidatorBase() {

    init {
        if (max < min) {
            throw IllegalArgumentException("max can't be lower than min")
        }
    }

    override fun isValid(result: Any?): Boolean {
        val text = result.toString()
        return text.length >= min && text.length <= max
    }
}