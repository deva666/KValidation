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

package com.markodevcic.kvalidation.validators

internal class LesserThanValidator(private val target: Number) : PropertyValidatorBase() {
    override fun isValid(result: Any?): Boolean {
        if (result is Number) {
            return result.toDouble() < target.toDouble()
        } else if (result == null) {
            return true
        } else {
            return false
        }
    }

    override fun toString(): String {
        return "Lesser than validator, expected value to be less than: $target"
    }
}