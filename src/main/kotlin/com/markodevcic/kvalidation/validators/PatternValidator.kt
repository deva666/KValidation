package com.markodevcic.kvalidation.validators

import java.util.regex.Pattern

open internal class PatternValidator(private val pattern: Pattern) : PropertyValidatorBase() {

    override fun isValid(result: Any?): Boolean {
        if (result == null) {
            return true
        }

        val matcher = pattern.matcher(result.toString())
        return matcher.matches()
    }

    override fun toString(): String {
        return "Pattern validator, expected pattern to match $pattern"
    }
}