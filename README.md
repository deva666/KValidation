# KValidation #

### Validation library for JVM languages ###

* simple - not bloated with features
* fast - no reflection
* written in Kotlin

---------------
Example:

```java
Person person = new Person();
PersonValidator validator = new PersonValidator(person);

validator.newRule(p -> p.name)
         .length(4)
         .mustBe(n -> n.startsWith("J"))
         .notEqual("John")
         .onAll()
         .errorMessage("Name should start with J, be 4 characters in length and not be John");

ValidationResult result = validator.validate();
```


---------------

Written by [Marko Devcic](http://www.markodevcic.com)