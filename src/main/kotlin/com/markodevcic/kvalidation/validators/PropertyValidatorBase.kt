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

package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.errors.ErrorLevel
import com.markodevcic.kvalidation.messages.MessageBuilder

abstract internal class PropertyValidatorBase : PropertyValidator {

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

    override fun toString(): String {
        return "Custom validator"
    }
}