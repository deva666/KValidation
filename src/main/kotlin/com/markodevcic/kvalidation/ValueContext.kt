package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.Validator
import java.util.*

class ValueContext<in T, out TFor>(val valueFactory: (T) -> TFor) {
    val validators = ArrayList<Validator>()
}