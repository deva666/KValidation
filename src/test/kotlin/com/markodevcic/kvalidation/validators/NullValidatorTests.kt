package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class NullValidatorTests {
    @Test
    fun testNonNull() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)
        validator.forValueBuilder { t -> t.name }
                .isNull()

        testObject.name = null
        var validationResult = validator.validate()
        Assert.assertTrue(validationResult.isValid)
        Assert.assertEquals(0, validationResult.validationErrors.size)

        testObject.name = "John"
        validationResult = validator.validate()
        Assert.assertFalse(validationResult.isValid)
        Assert.assertEquals(1, validationResult.validationErrors.size)
    }
}