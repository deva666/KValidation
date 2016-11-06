package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class LesserOrEqualThanValidatorTests {

    @Test
    fun testLesserThanInt() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.position }
                .lte(200)

        testObject.position = 200
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.position = 201
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

    @Test
    fun testLesserThanDouble() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.weight }
                .lte(100.0)

        testObject.weight = 100.0
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.weight = 100.1
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

}