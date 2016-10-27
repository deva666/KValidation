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
import com.markodevcic.kvalidation.validators.Validator
import java.util.*
import java.util.concurrent.Executor

abstract class AbstractValidator<T>(private val consumer: T) where T : Any {
    private val valueContexts: MutableList<ValueContext<T, *>> = ArrayList()

    var strategy = ValidationStrategy.FULL

    fun <TFor : Any> forValueBuilder(valueFactory: (T) -> TFor?): RuleBuilder<T, TFor> {
        val valueContext = ValueContext(valueFactory)
        valueContexts.add(valueContext)
        return RuleBuilder(valueContext)
    }

    fun <TFor : Any> forValue(valueFactory: (T) -> TFor?): Creator<T, TFor> {
        val valueContext = ValueContext(valueFactory)
        valueContexts.add(valueContext)
        return Creator(valueContext)
    }

    class Creator<T, TFor>(private val valueContext: ValueContext<T, TFor>) {
        infix fun rules(ruleInit: RuleSetter<T, TFor>.() -> Unit): Creator<T, TFor> {
            ruleInit(RuleSetter(valueContext))
            return this
        }

        infix fun onError(onErrorInit: OnErrorSetter<T, TFor>.() -> Unit) {
            onErrorInit(OnErrorSetter(valueContext))
        }
    }

    fun validate(): ValidationResult {
        val result = ValidationResult()
        valueContexts.forEach { context ->
            val validators = context.validators
            val value = context.valueFactory(consumer)
            validators.forEach { validator ->
                if (validator.precondition?.invoke(consumer) ?: true && !validator.isValid(value)) {
                    val error = createValidationError(validator, value, context.propertyName)
                    result.validationErrors.add(error)
                    if (strategy == ValidationStrategy.STOP_ON_FIRST) {
                        return result
                    }
                }
            }
        }
        return result
    }

    private fun <TFor: Any> createValidationError(validator: Validator, value: TFor?, propertyName: String?): ValidationError {
        val debugMessage = "$validator, received value: $value" +
                if (propertyName != null) ", property name: $propertyName" else ""
        val error = ValidationError(validator.messageBuilder?.getErrorMessage()
                ?: debugMessage, validator.errorLevel, validator.errorCode, debugMessage)
        return error
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit) {
        doAsync({ validate() }, callback)
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit, callbackExecutor: Executor) {
        doAsync({ validate() }, callback, callbackExecutor)
    }
}