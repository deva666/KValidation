package com.markodevcic.kvalidation

/**
 * Validator class that can be used inside of validated object
 * @sample class Person(private val name: String, private val age: Int) {
 *              val validator = InnerValidator(this) setRules {
 *                  forProperty { p -> p.name } rules {
 *                      equal("John")
 *                  }
 *              }
 *          }
 */
class InnerValidator<T>(instance: T) : ValidatorBase<T>(instance) where T : Any {
}

infix fun <T> InnerValidator<T>.setRules(initializer: InnerValidator<T>.() -> Unit): InnerValidator<T> where T : Any {
    initializer(this)
    return this
}