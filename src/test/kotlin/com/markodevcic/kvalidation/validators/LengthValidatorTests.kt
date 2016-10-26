package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class LengthValidatorTests {

    @Test
    fun testMaxLength() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forValueBuilder { t -> t.name }
                .length(6)

        testObject.name = "John"
        var result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)

        testObject.name = "Patrick"
        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)
    }

    @Test
    fun testMinAndMaxLength() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forValueBuilder { t -> t.name }
                .length(3, 6)

        testObject.name = "John"
        var result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)

        testObject.name = "Patrick"
        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)

        testObject.name = ""
        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)
    }
}