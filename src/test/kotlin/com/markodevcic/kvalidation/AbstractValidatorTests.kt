package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import org.junit.Assert
import org.junit.Test

class AbstractValidatorTests {

    @Test
    fun testOnAllErrorSet() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            length(4)
            mustBe { t -> t!!.startsWith("J") }
            equal("John")
        } onError {
            errorLevel(ErrorLevel.WARNING)
        }
        validator.strategy = ValidationStrategy.FULL

        testObject.name = "Patrick"

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(3, result.validationErrors.size)
        result.validationErrors.forEach { e -> Assert.assertEquals(ErrorLevel.WARNING, e.level) }
    }

    @Test
    fun testOnAllMessageSet() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            length(4)
            mustBe { t -> t!!.startsWith("J") }
            equal("John")
        } onError {
            errorMessage("fail")
        }
        validator.strategy = ValidationStrategy.FULL

        testObject.name = "Patrick"

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(3, result.validationErrors.size)
        result.validationErrors.forEach { e -> Assert.assertEquals("fail", e.message) }
    }

    @Test
    fun testNoRulesSet() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)
    }
}