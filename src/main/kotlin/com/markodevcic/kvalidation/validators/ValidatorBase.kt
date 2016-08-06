package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.errors.MessageBuilder

abstract class ValidatorBase : Validator {

    protected var _precondition: (Any) -> Boolean = { true }
    override var precondition: (Any) -> Boolean
        get() = _precondition
        set(value) {
            _precondition = value
        }

    override var errorMessage: String?
        get() = throw UnsupportedOperationException()
        set(value) {
        }

    protected var _messageBuilder: MessageBuilder? = null
    override var messageBuilder: MessageBuilder?
        get() = _messageBuilder
        set(value) {
            _messageBuilder = value
        }

    override var errorCode: Int?
        get() = throw UnsupportedOperationException()
        set(value) {
        }

    protected var _errorLevel = ErrorLevel.ERROR
    override var errorLevel: ErrorLevel
        get() = _errorLevel
        set(value) {
            _errorLevel = value
        }
}