/*
Copyright 2016, Marko Devcic, madevcic@gmail.com, http://www.markodevcic.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.DefaultMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

class OnErrorBuilder<T, TFor>(private val propertyContext: PropertyContext<T, TFor>) {

    infix fun errorMessage(message: String): OnErrorBuilder<T, TFor> {
        val messageBuilder = DefaultMessageBuilder(message)
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorMessage(messageBuilder: MessageBuilder): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    infix fun errorCode(code: Int): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    infix fun errorLevel(errorLevel: ErrorLevel): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }

    infix fun propertyName(name: String): OnErrorBuilder<T, TFor> {
        propertyContext.propertyName = name
        return this
    }
}