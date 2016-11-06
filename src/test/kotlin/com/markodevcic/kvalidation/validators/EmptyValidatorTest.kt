package com.markodevcic.kvalidation.validators

import com.markodevcic.kvalidation.ValidatorBase
import com.markodevcic.kvalidation.rules
import org.junit.Assert
import org.junit.Test

class EmptyValidatorTest {
    private class Testable {
        var collection = listOf<String>()
    }

    private val testable = Testable()
    private val validator: ValidatorBase<Testable> = object : ValidatorBase<Testable>(testable) {}

    @Test
    fun testEmptyCollection() {
        validator.clearAll()
        validator.forProperty { t -> t.collection } rules {
            empty()
        }

        testable.collection = listOf()

        val result = validator.validate()
        Assert.assertTrue(result.isValid)
    }

    @Test
    fun testNonEmptyCollection() {
        validator.clearAll()
        validator.forProperty { t -> t.collection } rules {
            empty()
        }

        testable.collection = listOf("aa")

        val result = validator.validate()
        Assert.assertFalse(result.isValid)
    }
}