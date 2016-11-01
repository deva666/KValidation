package com.markodevcic.kvalidation.validators

import java.util.regex.Pattern

open class PatternValidator(private val pattern: Pattern) : PropertyValidatorBase(){

    override fun isValid(result: Any?): Boolean {
        if (result == null) {
            return true
        }

        val matcher = pattern.matcher(result.toString())
        return matcher.matches()
    }
}