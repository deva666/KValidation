package com.markodevcic.kvalidation.messages

import com.markodevcic.kvalidation.validators.Validator

class DefaultMessageBuilder(private val valueClass: Class<*>, private val validator:Validator) : MessageBuilder{

    override fun getErrorMessage(): String {
        return "Field of type: ${valueClass.toString()} failed the validation: ${validator.toString()}."
    }
}