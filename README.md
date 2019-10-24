[![Build Status](https://travis-ci.org/deva666/KValidation.svg?branch=master)](https://travis-ci.org/deva666/KValidation) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# KValidation 

### Validation library for Kotlin/Java ###

---------------

### Installation:
Add `jcenter` repository

Gradle:
```
implementation 'com.markodevcic.kvalidation:KValidation:1.0.0'
```

Maven:
```
<dependency>
  <groupId>com.markodevcic.kvalidation</groupId>
  <artifactId>KValidation</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### Example: ###

Create the validator class by extending the generic `ValidatorBase<T>` class.

```
class UserValidator extends ValidatorBase<User> {
}
```

Java

```
User user = new User();
UserValidator validator = new UserValidator(user);

validator.forProperty(User::getName)
             .length(4)
             .mustBe(n -> n.startsWith("J"))
             .notEqual("John")
             .onError()
             .errorMessage("Name should start with J, be 4 characters in length and not be John");

ValidationResult result = validator.validate();

if (result.isValid()) {
  // woohooo
} else {
  for(ValidationError err : result.getValidationErrors()) {
    System.out.print(err.toString());
  }
}
```

Kotlin has even nicer syntax

```
val user = User()
val validator = UserValidator(user)
    
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
class User(private val name: String, private val age: Int) {
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
