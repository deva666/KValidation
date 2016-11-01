package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import org.junit.Assert
import org.junit.Test

class EqualValidatorTests {

    @Test
    fun testEqualDoubles() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forPropertyBuilder { t -> t.weight } equal Double.NaN

        testObject.weight = 75.0
        var result = validator.validate()
        Assert.assertFalse(result.isValid)

        testObject.weight = Double.NaN
        result = validator.validate()
        Assert.assertTrue(result.isValid)
    }
}