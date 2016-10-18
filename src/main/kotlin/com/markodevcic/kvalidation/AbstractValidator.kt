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

import com.markodevcic.kvalidation.async.doAsync
import com.markodevcic.kvalidation.errors.ValidationError
import com.markodevcic.kvalidation.messages.DefaultMessageBuilder
import com.markodevcic.kvalidation.validators.NullValidator
import com.markodevcic.kvalidation.validators.StatusBuilder
import com.markodevcic.kvalidation.validators.Validator
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.Executor

abstract class AbstractValidator<T>(private val consumer: T) where T : Any {
    private val valueContexts: MutableList<ValueContext<T, *>> = ArrayList()
    private lateinit var valueFactory: (T) -> Any?

    var strategy = ValidationStrategy.FULL

    fun <TFor : Any> newRule(valueFactory: (T) -> TFor?): RuleBuilder<T, TFor> {
        val valueContext = ValueContext(valueFactory)
        this.valueFactory = valueFactory
        valueContexts.add(valueContext)
        return RuleBuilder(valueContext)
    }

    fun <TFor : Any> newRule(valueFactory: (T) -> TFor?, builder: RuleBuilder<T,TFor>.() -> Unit) {
        val valueContext = ValueContext(valueFactory)
        this.valueFactory = valueFactory
        valueContexts.add(valueContext)
        builder(RuleBuilder(valueContext))
    }

    fun <TFor : Any> newRule(valueFactory: (T) -> TFor?,
                             ruleInit: RuleBuilder<T,TFor>.() -> Unit,
                             statusInit: StatusBuilder<T, TFor>.() -> Unit) {
        val valueContext = ValueContext(valueFactory)
        this.valueFactory = valueFactory
        valueContexts.add(valueContext)
        ruleInit(RuleBuilder(valueContext))
        statusInit(StatusBuilder(valueContext))
    }

    fun <TFor : Any> newFor(valueFactory: (T) -> TFor?): Creator<T, TFor> {
        val valueContext = ValueContext(valueFactory)
        this.valueFactory = valueFactory
        valueContexts.add(valueContext)
        return Creator(valueContext)
    }

    class Creator<T, TFor>(private val valueContext: ValueContext<T, TFor>){
        infix fun rules(ruleInit: RuleBuilder<T,TFor>.() -> Unit) : Creator<T, TFor> {
            ruleInit(RuleBuilder(valueContext))
            return this
        }

        infix fun onError(statusInit: StatusBuilder<T, TFor>.() -> Unit){
            statusInit(StatusBuilder(valueContext))
        }
    }

    fun validate(): ValidationResult {
        val result = ValidationResult()
        valueContexts.forEach { context ->
            val validators = context.validators
            val value = context.valueFactory(consumer)
            validators.forEach { validator ->
                if (validator.precondition?.invoke(consumer) ?: true && !validator.isValid(value)) {
                    val error = createValidationError(validator)
                    result.validationErrors.add(error)
                    if (strategy == ValidationStrategy.STOP_ON_FIRST) {
                        return result
                    }
                }
            }
        }
        return result
    }

    private fun createValidationError(validator: Validator): ValidationError {
        val error = ValidationError(validator.messageBuilder?.getErrorMessage()
                ?: DefaultMessageBuilder(getPropertyClass(valueFactory), validator).getErrorMessage()
                , validator.errorLevel)
        return error
    }

    private inline fun <reified TFor: Any> getPropertyClass(noinline valueFactory: (T) -> TFor?): Class<TFor> {
        return TFor::class.java
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit) {
        doAsync({ validate() }, callback)
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit, callbackExecutor: Executor) {
        doAsync({ validate() }, callback, callbackExecutor)
    }
}