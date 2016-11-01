package com.markodevcic.kvalidation;

import org.junit.Assert;
import org.junit.Test;

public class JavaTest {

    @Test
    public void testNonNullValidator() {
        TestObject testObject = new TestObject();
        TestObjectValidator validator = new TestObjectValidator(testObject);

        validator.forPropertyBuilder(TestObject::getName).nonNull();

        ValidationResult result = validator.validate();
        Assert.assertTrue(result.isValid());
        Assert.assertTrue(result.getValidationErrors().isEmpty());
    }

    @Test
    public void testRulesChaining() {
        TestObject testObject = new TestObject();
        TestObjectValidator validator = new TestObjectValidator(testObject);

        validator.forPropertyBuilder(TestObject::getName)
                .nonNull()
                .length(3, 6)
                .mustBe(s -> s.startsWith("J"))
                .onError()
                .propertyName("name");

        testObject.setName("Patrick");
        ValidationResult result = validator.validate();
        Assert.assertFalse(result.isValid());
        Assert.assertEquals(2, result.getValidationErrors().size());

        testObject.setName("John");
        result = validator.validate();
        Assert.assertTrue(result.isValid());
        Assert.assertEquals(0, result.getValidationErrors().size());
    }
}
