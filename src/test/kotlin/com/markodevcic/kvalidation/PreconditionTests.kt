package com.markodevcic.kvalidation

import org.junit.Assert
import org.junit.Test

class PreconditionTests {
    @Test
    fun test1PredicateOnRule() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.newRule { t -> t.name }
                .mustBe { v -> v!!.length == 5 }
                .whenIs { t -> t.name != null }

        testObject.name = null

        var result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertTrue(result.validationErrors.size == 0)

        testObject.name = ""
        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertTrue(result.validationErrors.size == 1)

        testObject.name = "Frank"
        result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertTrue(result.validationErrors.size == 0)
    }
}