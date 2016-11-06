# KValidation #

### Validation library for JVM languages ###

* simple - not bloated with features
* fast - no reflection
* readable
* written in Kotlin

---------------
### Example: ###

In Java

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
Contributions are always welcome, just start the project and submit a pull request.

---------------------------------------------------

Written by [Marko Devcic](http://www.markodevcic.com)

License [APL 2.0 ](http://www.apache.org/licenses/LICENSE-2.0)