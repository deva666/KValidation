package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.async.doAsync
import com.markodevcic.kvalidation.errors.ValidationError
import com.markodevcic.kvalidation.messages.DefaultMessageBuilder
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.Executor

abstract class AbstractValidator<T>(private val consumer: T) where T : Any {
    private val valueContexts: MutableList<ValueContext<T, *>> = ArrayList()
    private lateinit var valueFactory: (T) -> Any?

    var strategy = ValidationStrategy.FULL

    fun <TFor> newRule(valueFactory: (T) -> TFor): RuleBuilder<T, TFor> {
        val valueContext = ValueContext(valueFactory)
        this.valueFactory = valueFactory
        valueContexts.add(valueContext)
        return RuleBuilder(valueContext)
    }

    fun validate(): ValidationResult {
        val strategyCopy = strategy
        val result = ValidationResult()
        valueContexts.forEach { context ->
            val validators = context.validators
            val value = context.valueFactory(consumer)
            validators.forEach { validator ->
                if (validator.precondition?.invoke(consumer) ?: true && !validator.isValid(value)) {
                    result.validationErrors.add(ValidationError(validator.messageBuilder?.getErrorMessage()
                            ?: DefaultMessageBuilder(getValueClass(valueFactory), validator).getErrorMessage()
                            , validator.errorLevel))
                    if (strategyCopy == ValidationStrategy.STOP_ON_FIRST) {
                        return result
                    }
                }
            }
        }
        return result
    }

    @Suppress("UNCHECKED_CAST")
    private fun <TFor> getValueClass(valueFactory: (T) -> TFor): Class<TFor> {
        val method: Method
        try {
            method = valueFactory.javaClass
                    .getMethod("invoke", consumer.javaClass)
        } catch (e: NoSuchMethodException) {
            method = valueFactory.javaClass
                    .getMethod("invoke", Object::class.java)
        }
        val valueClass = method.returnType as Class<TFor>
        return valueClass
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit) {
        doAsync({ validate() }, callback)
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit, callbackExecutor: Executor) {
        doAsync({ validate() }, callback, callbackExecutor)
    }
}