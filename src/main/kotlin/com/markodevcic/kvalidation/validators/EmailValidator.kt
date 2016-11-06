package com.markodevcic.kvalidation.validators

import java.util.regex.Pattern

internal class EmailValidator : PatternValidator(emailPattern) {

    override fun toString(): String {
        return "Email validator"
    }

    companion object {
        val emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    }
}