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