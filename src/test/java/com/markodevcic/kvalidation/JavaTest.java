package com.markodevcic.kvalidation;

import com.markodevcic.kvalidation.validators.StatusBuilder;
import kotlin.Unit;
import org.junit.Assert;
import org.junit.Test;

import kotlin.jvm.functions.Function1;

public class JavaTest {

	@Test
	public void testNonNullValidator() {
		TestObject testObject = new TestObject();
		TestObjectValidator validator = new TestObjectValidator(testObject);

		validator.forValueBuilder(new Function1<TestObject,String>() {
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

		validator.forValueBuilder(new Fun())
				.nonNull()
				.length(3, 6)
				.mustBe(new Function1<String,Boolean>() {
					public Boolean invoke(String s) {
						return s.startsWith("J");
					}
				});

		validator.forValue(new Fun()).rules(new Function1<RuleBuilder<TestObject, String>, Unit>() {
			public Unit invoke(RuleBuilder<TestObject, String> builder) {
				builder.gt(23);
				return null;
			}
		}).onError(new Function1<StatusBuilder<TestObject, String>, Unit>() {
			public Unit invoke(StatusBuilder<TestObject, String> testObjectStringStatusBuilder) {
				return null;
			}
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

	private static class Fun implements Function1<TestObject, String>{
		public String invoke(TestObject testObject) {
			return testObject.getName();
		}
	}
}
