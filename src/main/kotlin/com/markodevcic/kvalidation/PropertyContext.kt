package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.validators.Validator
import java.util.*

class PropertyContext<T, TFor>(val valueFactory: (T) -> TFor) {
    val validators = ArrayList<Validator>()
    var clazz:Class<TFor>? = null
}