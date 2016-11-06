package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class GreaterThanValidatorTests {

    @Test
    fun testGreaterThanInt() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.position }
                .gt(100)

        testObject.position = 101
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

        validator.forProperty { t -> t.weight }
                .gt(100.0)

        testObject.weight = 100.1
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.weight = 99.9
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

}