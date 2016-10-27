package com.markodevcic.kvalidation;

import kotlin.jvm.functions.Function1;
import org.junit.Assert;
import org.junit.Test;

public class JavaTest {

    @Test
    public void testNonNullValidator() {
        TestObject testObject = new TestObject();
        TestObjectValidator validator = new TestObjectValidator(testObject);

        validator.forValueBuilder(TestObject::getName).nonNull();

        ValidationResult result = validator.validate();
        Assert.assertTrue(result.isValid());
        Assert.assertTrue(result.getValidationErrors().isEmpty());
    }

    @Test
    public void testRulesChaining() {
        TestObject testObject = new TestObject();
        TestObjectValidator validator = new TestObjectValidator(testObject);

        validator.forValueBuilder(TestObject::getName)
                .nonNull()
                .length(3, 6)
                .mustBe(s -> {
                    return s.startsWith("J");
                });

        validator.forValue(TestObject::getName).rules(builder -> {
            builder.gt(23);
            return null;
        }).onError(e -> {
            e.errorCode(1);
            return null;
        });

        testObject.setName("Patrick");
        ValidationResult result = validator.validate();
        Assert.assertFalse(result.isValid());
        Assert.assertEquals(2, result.getValidationErrors().size());

        testObject.setName("John");
        result = validator.validate();
        Assert.assertTrue(result.isValid());
        Assert.assertEquals(0, result.getValidationErrors().size());
    }

    private static class Fun implements Function1<TestObject, String> {
        public String invoke(TestObject testObject) {
            return testObject.getName();
        }
    }
}
