package com.markodevcic.kvalidation.validators

internal class LengthValidator(private val min: Int, private val max: Int) : PropertyValidatorBase() {

    init {
        if (max < min) {
            throw IllegalArgumentException("max can't be lower than min")
        }
    }

    override fun isValid(result: Any?): Boolean {
        val text = result?.toString() ?: ""
        return text.length >= min && text.length <= max
    }

    override fun toString(): String {
        return "Length validator, expected text length between: $min - $max"
    }
}