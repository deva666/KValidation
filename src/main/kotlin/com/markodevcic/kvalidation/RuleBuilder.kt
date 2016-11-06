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
import java.util.regex.Pattern

@Suppress("UNCHECKED_CAST")
class RuleBuilder<T, TFor>(val propertyContext: PropertyContext<T, TFor>) {

    private var currentPropertyValidator: PropertyValidator? = null

    private fun setValidatorInternal(validator: PropertyValidator) {
        currentPropertyValidator = validator
        propertyContext.validators.add(validator)
    }

    /**
     * Sets a custom validator
     */
    infix fun setValidator(validator: PropertyValidator): RuleBuilder<T, TFor> {
        setValidatorInternal(validator)
        return this
    }

    /**
     * Sets a custom rule
     * @param predicate method accepting validated property and returning a boolean value indicating if the property is valid
     */
    infix fun mustBe(predicate: (TFor?) -> Boolean): RuleBuilder<T, TFor> {
        val validator = object : PropertyValidatorBase() {
            override fun isValid(result: Any?): Boolean {
                return predicate.invoke(result as TFor?)
            }
        }
        setValidatorInternal(validator)
        return this
    }

    /**
     * Sets the rule that property must not be null
     */
    fun nonNull(): RuleBuilder<T, TFor> {
        setValidatorInternal(NonNullValidator())
        return this
    }

    /**
     * Sets the rule that property must be null
     * Cannot be used with other rules and validators
     * @throws [IllegalStateException] if there are other rules defined
     */
    fun isNull() {
        if (propertyContext.validators.size != 0) {
            throw IllegalStateException("can't set is null validator with other validators")
        }
        setValidatorInternal(NullValidator())
    }

    /**
     * Sets the rule that defines maximum length of string value of property
     */
    infix fun length(max: Int): RuleBuilder<T, TFor> {
        setValidatorInternal(LengthValidator(0, max))
        return this
    }

    /**
     * Sets the rule that defines minimum and maximum length of string value of property
     */
    fun length(min: Int, max: Int): RuleBuilder<T, TFor> {
        setValidatorInternal(LengthValidator(min, max))
        return this
    }

    /**
     * Sets the rule that property should be equal to another object of same type
     * Uses that type's equals method for comparison
     */
    infix fun equal(other: TFor): RuleBuilder<T, TFor> {
        setValidatorInternal(EqualValidator(other))
        return this
    }

    /**
     * Sets the rule that property should not be equal to another object of same type
     * Uses that type's equals method for comparison
     */
    infix fun notEqual(other: TFor): RuleBuilder<T, TFor> {
        setValidatorInternal(NotEqualValidator(other))
        return this
    }

    /**
     * Sets the rule that property should be less than defined [Number]
     * If property being validated is not subclass of [Number], validation fails
     */
    infix fun lt(other: Number): RuleBuilder<T, TFor> {
        setValidatorInternal(LesserThanValidator(other))
        return this
    }

    /**
     * Sets the rule that property should be greater than defined [Number]
     * If property being validated is not subclass of [Number], validation fails
     */
    infix fun gt(other: Number): RuleBuilder<T, TFor> {
        setValidatorInternal(GreaterThanValidator(other))
        return this
    }

    /**
     * Sets the rule that property should be less or equal than defined [Number]
     * If property being validated is not subclass of [Number], validation fails
     */
    infix fun lte(other: Number): RuleBuilder<T, TFor> {
        setValidatorInternal(LesserOrEqualThanValidator(other))
        return this
    }

    /**
     * Sets the rule that property should be greater or equal than defined [Number]
     * If property being validated is not subclass of [Number], validation fails
     */
    infix fun gte(other: Number): RuleBuilder<T, TFor> {
        setValidatorInternal(GreaterOrEqualThanValidator(other))
        return this
    }

    /**
     * Sets the rule that property should be inside defined [Iterable]
     */
    infix fun isContainedIn(source: Iterable<TFor>): RuleBuilder<T, TFor> {
        setValidatorInternal(ContainsValidator(source))
        return this
    }

    /**
     * Sets the rule that property should be between two numbers
     * If property being validated is not subclass of [Number], validation fails
     */
    fun inRange(min: Number, max: Number): RuleBuilder<T, TFor> {
        setValidatorInternal(RangeValidator(min, max))
        return this
    }

    /**
     * Sets the rule that property should not contain any elements
     * If property being validated is not subclass of [Iterable], validation fails
     */
    fun empty(): RuleBuilder<T, TFor> {
        setValidatorInternal(EmptyValidator())
        return this
    }

    /**
     * Sets the rule that string value of property should match an email regex
     */
    fun email(): RuleBuilder<T, TFor> {
        setValidatorInternal(EmailValidator())
        return this
    }

    /**
     * Sets the rule that string value of property should match passed [Pattern]
     */
    fun pattern(pattern: Pattern): RuleBuilder<T, TFor> {
        setValidatorInternal(PatternValidator(pattern))
        return this
    }

    /**
     * Sets a precondition for previously defined property
     * If precondition returns false, previous rule will not be executed
     * @sample
     *  validator.forProperty { t -> t.name } rules {
     *      nonNull()
     *      length(7)
     *      whenIs { t -> t.position > 3 } //length validator will be executed only when position is larger than 3, nonNull validator executes always
     *  }
     */
    infix fun whenIs(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        currentPropertyValidator?.precondition = { c -> precondition.invoke(c as T) }
        return this
    }

    /**
     * Sets a precondition for all previously defined properties
     * If precondition returns false, previous rule will not be executed
     * @sample
     *  validator.forProperty { t -> t.name } rules {
     *      nonNull()
     *      length(7)
     *      whenIsOnAll { t -> t.position > 3 } //length and nonNull validator will be executed only when position is larger than 3
     *  }
     */
    infix fun whenIsOnAll(precondition: (T) -> Boolean): RuleBuilder<T, TFor> {
        propertyContext.validators.forEach { v ->
            v.precondition = { c -> precondition.invoke(c as T) }
        }
        return this
    }

    /**
     * Returns an [OnErrorBuilder] object for defining on error properties
     */
    fun onError(): OnErrorBuilder<T, TFor> {
        return OnErrorBuilder(propertyContext)
    }
}

