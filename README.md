[![Build Status](https://travis-ci.org/deva666/KValidation.svg?branch=master)](https://travis-ci.org/deva666/KValidation) [![Coverage Status](https://coveralls.io/repos/github/deva666/KValidation/badge.svg?branch=master)](https://coveralls.io/github/deva666/KValidation?branch=master) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# KValidation 

### Validation library for Kotlin/Java ###

---------------
### Example: ###

Java

```
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

If you don't want to create a validator class, add InnerValidator inside the validated class

```
    class Person(private val name: String, private val age: Int) {
        val validator = InnerValidator(this) setRules {
            forProperty { p -> p.name } rules {
                equal("John")
            }
        }
     }
```
----------------------------------------------------
### Contributing ###
Contributions are always welcome, just fork the project and submit a pull request.

---------------------------------------------------

Written by [Marko Devcic](http://www.markodevcic.com)

License [APL 2.0 ](http://www.apache.org/licenses/LICENSE-2.0)