package com.markodevcic.kvalidation;

import org.junit.Assert;
import org.junit.Test;

import kotlin.jvm.functions.Function1;

public class JavaTest {
	@Test
	public void testJavaCode() {
		TestObject testObject = new TestObject();
		TestObjectValidator validator = new TestObjectValidator(testObject);

		validator.newRule(new Function1<TestObject,String>() {
			public String invoke(TestObject testObject) {
				return testObject.getName();
			}
		}).nonNull();

		ValidationResult result = validator.validate();
		Assert.assertTrue(result.isValid());
		Assert.assertTrue(result.getValidationErrors().size() == 0);
	}
}
