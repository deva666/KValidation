package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.validators.Validator
import com.markodevcic.kvalidation.errors.ErrorLevel

abstract class ValidatorBase : Validator {
    override fun isValid(result: Any?): Boolean {
        return true
    }

    override var precondition: (Any) -> Boolean
        get() = { true }
        set(value) {
        }

    override var errorMessage: String?
        get() = throw UnsupportedOperationException()
        set(value) {
        }

    override var errorMessageId: Int?
        get() = 1
        set(value) {
        }

    override var errorCode: Int?
        get() = throw UnsupportedOperationException()
        set(value) {
        }

    override var errorLevel: ErrorLevel
        get() = ErrorLevel.ERROR
        set(value) {
        }
}