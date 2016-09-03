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

package com.markodevcic.kvalidation.async

import java.util.concurrent.Executor
import java.util.concurrent.Executors


inline fun <T, TR> T.doAsync(crossinline valueFactory: () -> TR,
                             crossinline callback: (TR?, Exception?) -> Unit,
                             workExecutor: Executor = Executors.newSingleThreadExecutor(),
                             callbackExecutor: Executor? = null) {
    return workExecutor.execute {
        var result: TR? = null
        try {
            result = valueFactory()
        } catch (e: Exception) {
            if (callbackExecutor != null) {
                callbackExecutor.execute { callback(null, e) }
            } else {
                callback(null, e)
            }
        }
        if (result != null) {
            if (callbackExecutor != null) {
                callbackExecutor.execute { callback(result, null) }
            } else {
                callback(result, null)
            }
        }
    }
}