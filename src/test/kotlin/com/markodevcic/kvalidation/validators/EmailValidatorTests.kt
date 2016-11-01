package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class EmailValidatorTests{
    @Test
    fun testValidEmail() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            email()
        }

        testObject.name = "david@gmail.com"

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
    }

    @Test
    fun testInValidEmail() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            email()
        }

        testObject.name = "david@david@gmail.com"

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
    }
}
