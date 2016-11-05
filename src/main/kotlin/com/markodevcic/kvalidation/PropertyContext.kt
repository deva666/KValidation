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

import com.markodevcic.kvalidation.validators.PropertyValidator
import java.util.*

/**
 * Context that holds property value producer, all the validators for specified property and optional property name
 * @param propertyName if defined, property name will be added to debug messages of [ValidationError] for easier debugging
 */
class PropertyContext<in T, out TFor>(val valueFactory: (T) -> TFor?, var propertyName: String? = null) {
    val validators = ArrayList<PropertyValidator>()
}