# KValidation #

### Validation library for JVM languages ###

* simple - not bloated with features
* fast - no reflection
* readable
* written in Kotlin

---------------
### Example: ###

Java

```java
Person person = new Person();
PersonValidator validator = new PersonValidator(person);

validator.forProperty(Person::getName)
         .length(4)
         .mustBe(n -> n.startsWith("J"))
         .notEqual("John")
         .onError()
         .errorMessage("Name should start with J, be 4 characters in length and not be John");

ValidationResult result = validator.validate();
```

Kotlin has even nicer syntax

```
    val person = Person()
    val validator = PersonValidator(person)
    
    validator.forProperty { p -> p.name } rules {
        length(4)
        mustBe { n -> n.startsWith("J") }
        notEqual("John")
    } onError {
        errorMessage("Name should start with J, be 4 characters in length and not be John")
    }
    
    val result = validator.validate()
```
----------------------------

### How to use it ###
[Download](https://bitbucket.org/deva666/kvalidation/downloads/kvalidation-1.0-SNAPSHOT.jar) the jar file and include it in your project.

---------------

Written by [Marko Devcic](http://www.markodevcic.com)

License [APL 2.0 ](http://www.apache.org/licenses/LICENSE-2.0)