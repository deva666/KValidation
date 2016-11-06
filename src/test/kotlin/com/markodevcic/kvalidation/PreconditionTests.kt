package com.markodevcic.kvalidation

import org.junit.Assert
import org.junit.Test

class PreconditionTests {

    @Test
    fun testPredicateOnRuleBuilder() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name }
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

    @Test
    fun testPredicateOnRuleSetter() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            mustBe { v -> v!!.length == 5 }
            whenIs { t -> t.name != null }
        }

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

    @Test
    fun testPredicatesOn2Rules() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name }
                .mustBe { v -> v!!.length == 0 }
                .whenIs { t -> t.position == 0 }
                .mustBe { v -> v!!.length == 1 }
                .whenIs { t -> t.position == 1 }

        testObject.name = ""
        testObject.position = 3

        var result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)

        testObject.name = "AB"
        testObject.position = 0

        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)

        testObject.name = ""
        testObject.position = 0

        result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)

        testObject.name = "A"
        testObject.position = 1

        result = validator.validate()
        Assert.assertTrue(result.isValid)
        Assert.assertEquals(0, result.validationErrors.size)

        testObject.name = ""
        testObject.position = 1

        result = validator.validate()
        Assert.assertFalse(result.isValid)
        Assert.assertEquals(1, result.validationErrors.size)
    }

    @Test
    fun testRuleBuilderOnAllPrecondition() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name }
                .mustBe { v -> v != null && v.length == 5 }
                .length(10)
                .whenIsOnAll { t -> t.position == 1 }

        testObject.name = ""
        testObject.position = 0

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
    }

    @Test
    fun testRuleSetterOnAllPrecondition() {
        val testObject = TestObject()
        val validator = TestObjectValidator(testObject)

        validator.forProperty { t -> t.name } rules {
            mustBe { v -> v != null && v.length == 5 }
            length(10)
            whenIsOnAll { t -> t.position == 1 }
        }

        testObject.name = ""
        testObject.position = 0

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
    }
}