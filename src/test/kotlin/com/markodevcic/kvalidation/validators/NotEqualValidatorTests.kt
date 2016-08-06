package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class NotEqualValidatorTests {

    @Test
    fun testNotEqualDoubles() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.weight }
                .notEqual(Double.NaN)

        testObject.weight = 75.0
        var result = validator.validate()
        Assert.assertTrue(result.isValid)

        testObject.weight = Double.NaN
        result = validator.validate()
        Assert.assertFalse(result.isValid)
    }
}