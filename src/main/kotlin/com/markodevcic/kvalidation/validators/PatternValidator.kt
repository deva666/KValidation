package com.markodevcic.kvalidation.validators

import java.util.regex.Pattern

open class PatternValidator(private val pattern: Pattern) : PropertyValidatorBase(){

    override fun isValid(result: Any?): Boolean {
        if (result == null || result !is String) {
            return true
        }

        val matcher = pattern.matcher(result)
        return matcher.matches()
    }
}