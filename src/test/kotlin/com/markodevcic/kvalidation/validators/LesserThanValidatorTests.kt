package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class LesserThanValidatorTests {

    @Test
    fun testLesserThanInt() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forPropertyBuilder { t -> t.position }
                .lt(200)

        testObject.position = 100
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.position = 200
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

    @Test
    fun testLesserThanDouble() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forPropertyBuilder { t -> t.weight }
                .lt(100.0)

        testObject.weight = 99.9
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.weight = 100.0
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }
}