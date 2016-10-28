package com.markodevcic.kvalidation.messages

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Test

class DefaultMessageBuilderTest {

    @Test
    fun testDebugMessageGet() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forValue { t -> t.position } rules {
            gt(1000)
            notEqual(0)
            lt(1)
            inRange(300, 30000)
        } onError {
            propertyName("position")
        }

        testObject.position = 2

        val result = validator.validate()
        val errors = result.validationErrors
        errors.forEach {  }
    }
}