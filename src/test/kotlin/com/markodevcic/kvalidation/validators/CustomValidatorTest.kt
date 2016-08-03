package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class CustomValidatorTest {
    @Test
    fun testCustomValidator(){
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.position }
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
}