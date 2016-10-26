package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class CustomValidatorTests {
    @Test
    fun testCustomValidator() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forValueBuilder { t -> t.position }
                .mustBe { v -> v == 10 }

        testObject.position = 1
        val failResult = validator.validate()
        Assert.assertFalse(failResult.isValid)
        Assert.assertTrue(failResult.validationErrors.size == 1)

        testObject.position = 10
        val okResult = validator.validate()
        Assert.assertTrue(okResult.isValid)
        Assert.assertTrue(okResult.validationErrors.size == 0)
    }

    @Test
    fun testNullableField() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forValue{ t -> t.position } rules {
            notEqual(Int.MAX_VALUE)
            lte(10)
            gte(5)
        } onError {
        }

        testObject.position = 33
        val result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)

        validator.forValue { t -> t.position } rules {
            lt(45)
            gt(5)
            notEqual(40)
            whenIsOnAll { t -> t.position != null }
        } onError {

        }
    }
}