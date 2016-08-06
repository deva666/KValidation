package com.markodevcic.kvalidation.messages

class CustomMessageBuilder(private val message: String) : MessageBuilder {
    override fun getErrorMessage(): String {
        return message
    }
}