package com.markodevcic.kvalidation.messages

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class DefaultMessageBuilderTest {

    @Test
    fun testMessageSet() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.position } rules {
            gt(1000)
            notEqual(0)
            lt(1)
            inRange(300, 30000)
        } onError {
            errorMessage("you failed")
            propertyName("position")
        }

        val result = validator.validate()
        val errors = result.validationErrors
        errors.forEach { e -> Assert.assertTrue(e.message == "you failed") }
    }
}