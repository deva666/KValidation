package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class GreaterOrEqualThanValidatorTests {

    @Test
    fun testGreaterThanInt() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.position }
                .gte(100)

        testObject.position = 100
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.position = 90
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

    @Test
    fun testGreaterThanDouble() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.weight }
                .gte(100.0)

        testObject.weight = 100.0
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.weight = 99.9
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidArgument() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.name }
                .gte(200)

        val result = validator.validate()
    }
}