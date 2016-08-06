package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ValidationError
import java.util.*

class ValidationResult() {

    val validationErrors = ArrayList<ValidationError>()

    val isValid: Boolean
        get () {
            return validationErrors.size == 0
        }
}