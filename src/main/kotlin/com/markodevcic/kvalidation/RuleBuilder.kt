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

import com.markodevcic.kvalidation.validators.*

@Suppress("UNCHECKED_CAST")
open class RuleBuilder<T, TFor>(protected val valueContext: ValueContext<T, TFor>) {

    private var currentValidator: Validator? = null

    protected fun setValidator(validator: Validator) {
        currentValidator = validator
        valueContext.validators.add(validator)
    }

    infix fun mustBe(validator: Validator): RuleBuilder<T, TFor> {
        setValidator(validator)
        return this
    }

    infix fun mustBe(predicate: (TFor?) -> Boolean): RuleBuilder<T, TFor> {
        val validator = object : ValidatorBase() {
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidator(validator)
        return this
    }

    fun nonNull(): RuleBuilder<T, TFor> {
        setValidator(NonNullValidator())
        return this
    }

    fun isNull() {
        if (valueContext.validators.size != 0) {
            throw IllegalStateException("can't set is null validator with other validators")
        }
        setValidator(NullValidator())
    }

    infix fun length(max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(0, max))
        return this
    }

    fun length(min: Int, max: Int): RuleBuilder<T, TFor> {
        setValidator(LengthValidator(min, max))
        return this
    }

    infix fun equal(other: TFor): RuleBuilder<T, TFor> {
        setValidator(EqualValidator(other))
        return this
    }

    infix fun notEqual(other: TFor): RuleBuilder<T, TFor> {
        setValidator(NotEqualValidator(other))
        return this
    }

    infix fun lt(other: Number): RuleBuilder<T, TFor> {
        setValidator(LesserThanValidator(other))
        return this
    }

    infix fun gt(other: Number): RuleBuilder<T, TFor> {
        setValidator(GreaterThanValidator(other))
        return this
    }

    infix fun lte(other: Number): RuleBuilder<T, TFor> {
        setValidator(LesserOrEqualThanValidator(other))
        return this
    }

    infix fun gte(other: Number): RuleBuilder<T, TFor> {
        setValidator(GreaterOrEqualThanValidator(other))
        return this
    }

    infix fun isContainedIn(source: Collection<TFor>): RuleBuilder<T, TFor> {
        setValidator(ContainsValidator(source))
        return this
    }

    fun inRange(min: Number, max: Number): RuleBuilder<T, TFor>{
        setValidator(RangeValidator(min, max))
        return this
    }

    infix fun whenIs(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        currentValidator?.precondition = { c -> precondition.invoke(c as T) }
        return this
    }

    infix fun whenIsOnAll(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        valueContext.validators.forEach { v ->
            v.precondition = { c -> precondition.invoke(c as T) }
        }
        return this
    }

    fun onError(): OnErrorBuilder<T, TFor> {
        return OnErrorBuilder(valueContext)
    }
}

