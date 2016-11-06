package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.TestObject
import com.markodevcic.kvalidation.TestObjectValidator
import com.markodevcic.kvalidation.rules
import org.junit.Assert
import org.junit.Test

class ContainsValidatorTests{

    @Test
    fun testContains() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        val set = hashSetOf("John", "Patrick", "David")

        validator.forProperty { t -> t.name } rules {
            isContainedIn(set)
        }

        testObject.name = "John"

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
    }

    @Test
    fun testNotContained() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        val set = hashSetOf("John", "Patrick", "David")

        validator.forProperty { t -> t.name } rules {
            isContainedIn(set)
        }

        testObject.name = "Johnny"

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
    }

    @Test
    fun testNullValue() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        val set = hashSetOf("John", "Patrick", "David")

        validator.forProperty { t -> t.name } rules {
            isContainedIn(set)
        }

        testObject.name = null

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
    }
}
