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
import com.markodevcic.kvalidation.validators.*

@Suppress("UNCHECKED_CAST")
class RuleBuilder<T, TFor>(private val valueContext: ValueContext<T, TFor>) : EndBuilder<T, TFor>(null) {
    //    private var currentValidator: Validator? = null
    private var canBeNull: Boolean = false

    private fun setValidator(validator: Validator) {
        currentValidator = validator
        valueContext.validators.add(validator)
    }

    fun mustBe(validator: Validator): RuleBuilder<T, TFor> {
        setValidator(validator)
        return this
    }

    fun mustBe(predicate: (TFor?) -> Boolean): RuleBuilder<T, TFor> {
        val validator = object : ValidatorBase() {
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidator(validator)
        return this
    }

    fun canBeNull(value: Boolean): RuleBuilder<T, TFor> {
        valueContext.canBeNull = value
        return this
    }

//    fun nonNull(): RuleBuilder<T, TFor> {
//        setValidator(NonNullValidator())
//        return this
//    }

    fun isNull(): EndBuilder<T, TFor> {
        if (valueContext.validators.size != 0) {
            throw IllegalStateException("can't set is null validator with other validators")
        }
        setValidator(NullValidator())
        return this
    }

    fun length(max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(0, max))
        return this
    }

    fun length(min: Int, max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(min, max))
        return this
    }

    fun equal(other: TFor): RuleBuilder<T, TFor> {
        setValidator(EqualValidator(other!!))
        return this
    }

    fun notEqual(other: TFor): RuleBuilder<T, TFor> {
        setValidator(NotEqualValidator(other!!))
        return this
    }

    fun lt(other: Number): RuleBuilder<T, TFor> {
        setValidator(LesserThanValidator(other))
        return this
    }

    fun gt(other: Number): RuleBuilder<T, TFor> {
        setValidator(GreaterThanValidator(other))
        return this
    }

    fun lte(other: Number): RuleBuilder<T, TFor> {
        setValidator(LesserOrEqualThanValidator(other))
        return this
    }

    fun gte(other: Number): RuleBuilder<T, TFor> {
        setValidator(GreaterOrEqualThanValidator(other))
        return this
    }

    fun isContainedIn(source: Collection<TFor>): RuleBuilder<T, TFor> {
        setValidator(ContainsValidator(source))
        return this
    }

    //    fun errorMessage(message: String): RuleBuilder<T, TFor> {
//        currentValidator?.messageBuilder = CustomMessageBuilder(message)
//        return this
//    }
//
//    fun errorMessage(messageBuilder: MessageBuilder): RuleBuilder<T, TFor> {
//        currentValidator?.messageBuilder = messageBuilder
//        return this
//    }
//
//    fun errorCode(code: Int): RuleBuilder<T, TFor> {
//        currentValidator?.errorCode = code
//        return this
//    }
//
    fun whenIs(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        currentValidator?.precondition = { c -> precondition.invoke(c as T) }
        return this
    }

    //
//    fun errorLevel(errorLevel: ErrorLevel): RuleBuilder<T, TFor> {
//        currentValidator?.errorLevel = errorLevel
//        return this
//    }
//
    fun onAll(): AllRulesOptionsBuilder<T, TFor> {
        return AllRulesOptionsBuilder(valueContext)
    }
}

open class EndBuilder<T, TFor>(protected var currentValidator: Validator?) {
    fun errorMessage(message: String): EndBuilder<T, TFor> {
        currentValidator?.messageBuilder = CustomMessageBuilder(message)
        return this
    }

    fun errorMessage(messageBuilder: MessageBuilder): EndBuilder<T, TFor> {
        currentValidator?.messageBuilder = messageBuilder
        return this
    }

    fun errorCode(code: Int): EndBuilder<T, TFor> {
        currentValidator?.errorCode = code
        return this
    }

//        fun whenIs(precondition: (T) -> Boolean): EndBuilder<T, TFor> {
//            currentValidator?.precondition = { c -> precondition.invoke(c as T) }
//            return this
//        }

    fun errorLevel(errorLevel: ErrorLevel): EndBuilder<T, TFor> {
        currentValidator?.errorLevel = errorLevel
        return this
    }
}

