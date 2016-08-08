# KValidation #

### Validation library for JVM languages ###

* simple - not bloated with features
* fast - no reflection
* written in Kotlin

---------------
### Example: ###

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
------------

### How to use it ###
[Download](https://bitbucket.org/deva666/kvalidation/downloads/kvalidation-1.0-SNAPSHOT.jar) the jar file and include it in your project.

---------------

Written by [Marko Devcic](http://www.markodevcic.com)

License [GPL3](https://www.gnu.org/licenses/gpl-3.0.en.html)