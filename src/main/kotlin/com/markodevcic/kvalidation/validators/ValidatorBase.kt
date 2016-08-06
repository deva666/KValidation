package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.MessageBuilder

abstract class ValidatorBase : Validator {

    protected var _precondition: ((Any) -> Boolean)? = null
    override var precondition: ((Any) -> Boolean)?
        get() = _precondition
        set(value) {
            _precondition = value
        }

    protected var _messageBuilder: MessageBuilder? = null
    override var messageBuilder: MessageBuilder?
        get() = _messageBuilder
        set(value) {
            _messageBuilder = value
        }

    private var _errorCode: Int? = null
    override var errorCode: Int?
        get() = _errorCode
        set(value) {
            _errorCode = value
        }

    protected var _errorLevel = ErrorLevel.ERROR
    override var errorLevel: ErrorLevel
        get() = _errorLevel
        set(value) {
            _errorLevel = value
        }
}