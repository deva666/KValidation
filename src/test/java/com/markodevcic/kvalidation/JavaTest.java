package com.markodevcic.kvalidation;

import org.junit.Assert;
import org.junit.Test;

import kotlin.jvm.functions.Function1;

public class JavaTest {

	@Test
	public void testNonNullValidator() {
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

	@Test
	public void testRulesChaining() {
		TestObject testObject = new TestObject();
		TestObjectValidator validator = new TestObjectValidator(testObject);

		validator.newRule(new Fun())
				.nonNull()
				.length(3, 6)
				.mustBe((n) -> n.startsWith("J"));

		testObject.setName("Patrick");
		ValidationResult result = validator.validate();
		Assert.assertFalse(result.isValid());
		Assert.assertEquals(2, result.getValidationErrors().size());

		testObject.setName("John");
		result = validator.validate();
		Assert.assertTrue(result.isValid());
		Assert.assertEquals(0, result.getValidationErrors().size());
	}

	private static class Fun implements Function1<TestObject, String>{
		@Override
		public String invoke(TestObject testObject) {
			return testObject.getName();
		}
	}
}
