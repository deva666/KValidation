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

    /**
     * Sets the message to display when validation fails
     */
    infix fun errorMessage(message: String): OnErrorBuilder<T, TFor> {
        val messageBuilder = DefaultMessageBuilder(message)
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    /**
     * Sets the [MessageBuilder] to display error message for failed validation
     * For example, in Android getting a string from resources requires an instance of Context class
     * So custom message builder which gets a string from Android resources can be passed here
     */
    infix fun errorMessage(messageBuilder: MessageBuilder): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    /**
     * Sets the error code for validated property
     */
    infix fun errorCode(code: Int): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    /**
     * Sets the error level for validated property
     */
    infix fun errorLevel(errorLevel: ErrorLevel): OnErrorBuilder<T, TFor> {
        propertyContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }

    /**
     * Set the name of property being validated.
     * The name is displayed in each @see ValidationError debug message, for easier debugging
     */
    infix fun propertyName(name: String): OnErrorBuilder<T, TFor> {
        propertyContext.propertyName = name
        return this
    }
}