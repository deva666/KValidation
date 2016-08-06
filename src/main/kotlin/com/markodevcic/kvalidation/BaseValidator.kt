package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.async.doAsync
import com.markodevcic.kvalidation.errors.ValidationError
import java.util.*
import java.util.concurrent.Executor

abstract class BaseValidator<T>(private val consumer: T) where T : Any {
    private val valueContexts: MutableList<ValueContext<T, *>> = ArrayList()
    private val valueMap = HashMap<Class<*>, ValueContext<T, *>>()

    var strategy = ValidationStrategy.FULL

    fun <TFor> newRule(valueFactory: (T) -> TFor): RuleBuilder<T, TFor> {
        val propertyContext = ValueContext(valueFactory)
        val valueClass = getValueClass(valueFactory)
        propertyContext.clazz = valueClass
        valueContexts.add(propertyContext)
        return RuleBuilder(propertyContext)
    }

    private fun <TFor> getValueClass(valueFactory: (T) -> TFor): Class<TFor> {
        val method = valueFactory.javaClass
                .declaredMethods
                .filter { m ->
                    m.parameterTypes.count() == 1
                            && m.parameterTypes[0] == consumer.javaClass
                            && m.name == "invoke"
                }.single()
        val valueClass = method.returnType as Class<TFor>
        return valueClass
    }

    fun validate(): ValidationResult {
        val strategyCopy = strategy
        val result = ValidationResult()
        valueContexts.forEach { context ->
            val validators = context.validators
            val value = context.valueFactory(consumer)
            validators.forEach { validator ->
                if (validator.precondition.invoke(consumer) && !validator.isValid(value)) {
                    result.validationErrors.add(ValidationError("Fail", validator.errorLevel))
                    if (strategyCopy == ValidationStrategy.STOP_ON_FIRST) {
                        return result
                    }
                }
            }
        }
        return result
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit) {
        doAsync({ validate() }, callback)
    }

    fun validateAsync(callback: (ValidationResult?, Exception?) -> Unit, callbackExecutor: Executor) {
        doAsync({ validate() }, callback, callbackExecutor)
    }
}