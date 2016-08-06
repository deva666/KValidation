package com.markodevcic.kvalidation

import com.markodevcic.kvalidation.errors.ErrorLevel
import org.junit.Assert
import org.junit.Test

class BaseValidatorTests {

    @Test
    fun testOnAllCall() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.name }
                .length(4)
                .mustBe { t -> t!!.startsWith("J") }
                .equal("John")
                .onAll()
                .errorLevel(ErrorLevel.WARNING)
        validator.strategy = ValidationStrategy.FULL

        testObject.name = "Patrick"

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(3, result.validationErrors.size)
        result.validationErrors.forEach { e -> Assert.assertEquals(ErrorLevel.WARNING, e.level) }
    }
}