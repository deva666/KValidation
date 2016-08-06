package com.markodevcic.kvalidation.messages

class DefaultMessageBuilder(private val valueClass: Class<*>) : MessageBuilder{

    override fun getErrorMessage(): String {
        return "Field of type: ${valueClass.toString()} failed the validation."
    }
}