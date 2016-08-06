package com.markodevcic.kvalidation.async

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import com.markodevcic.kvalidation.ValidationResult
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

class AsyncTests {

    @Test
    fun testNullCallbackExecutor() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.name }
                .equal("John")

        testObject.name = "John"

        val callBackResult: AtomicReference<ValidationResult?> = AtomicReference(null)
        val fail: AtomicReference<Exception?> = AtomicReference(null)
        validator.validateAsync { result, exception ->
            callBackResult.set(result)
            fail.set(exception)
        }

        while (callBackResult.get() == null && fail.get() == null) {
            Thread.sleep(50)
        }

        Assert.assertNotNull(callBackResult.get())
        Assert.assertNull(fail.get())
        Assert.assertTrue(callBackResult.get()!!.isValid)
    }
}