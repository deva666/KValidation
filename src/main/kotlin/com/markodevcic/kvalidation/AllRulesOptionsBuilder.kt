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
import com.markodevcic.kvalidation.messages.CustomMessageBuilder
import com.markodevcic.kvalidation.messages.MessageBuilder

@Suppress("UNCHECKED_CAST")
class AllRulesOptionsBuilder<T, TFor>(private val valueContext: ValueContext<T, TFor>) {

    fun errorMessage(message: String): AllRulesOptionsBuilder<T, TFor> {
        val builder = CustomMessageBuilder(message)
        valueContext.validators.forEach { v -> v.messageBuilder = builder}
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.messageBuilder = messageBuilder }
        return this
    }

    fun errorCode(code: Int): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorCode = code }
        return this
    }

    fun whenIs(condition: (T) -> Boolean): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.precondition = { c -> condition.invoke(c as T)} }
        return this
    }

    fun errorLevel(errorLevel: ErrorLevel): AllRulesOptionsBuilder<T, TFor> {
        valueContext.validators.forEach { v -> v.errorLevel = errorLevel }
        return this
    }
}
