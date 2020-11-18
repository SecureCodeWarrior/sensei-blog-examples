## Basic Immutability

Immutability is often promoted as a way of avoiding entire classes of problems e.g.

- unexpected extension
- concurrent updates
- objects are always represent a valid state


Some of the basic principles of immutability are easy to adopt and Static Analysis tooling can help enforce and remind us to think about adopting those principles.

In this section we will explore some simple transformations we can use to convert a mutable class to an immutable class.

### A Mutable Class

What follows is a very simple Co-ordinates class. It consists of two properties `x` and `y` and these are set and retrieved via methods, with the co-ordinate properties being maintained with a `transformPositionBy` method.

```
public class MutableCoordinates {

    private int x;
    private int y;

    public MutableCoordinates(){
        x=0;
        y=0;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void transformPositionBy(int xadjust, int yadjust){
        this.x+=xadjust;
        this.y+=yadjust;
    }
}
```

The basic Steps to converting this into an immutable class:

- make class `final'
- make constructors private and use factory methods to create objects
- make fields final, forcing them to be instantiated in a constructor

While it would be tempting to try and create a single recipe 'this class is mutable - fix it to be immutable'. Such a recipe would be hard to build and maintain. It is often easier to create smaller recipes to handle intermediate step transformations, and allow the programmer to make decisions about which steps to apply, rather than trying to cram a lot in an single recipe.



### Make class `final`

A simple immutable class can be impacted if we can extend it.

Making it `final` might help prevent that.

**Before:**

```
public class MutableCoordinates {
```

**After:**

```
public final class MutableCoordinates {
```

#### Sensei Recipe

To warn me about this, and create a QuickFix action to help fix it I can use Sensei as follows:

```
Name - Immutable: use final classes to prevent people extending as mutable
Description - Make the classes final to prevent extension with a mutable subclass
Level - Warning
```

**Recipe Settings:**

```
search:
  class:
    allOf:
    - modifier: "public"
    without:
      modifier: "final"
```


**QuickFix:**

```
availableFixes:
- name: "Remove public modifier and make final"
  actions:
  - changeModifiers:
      final: true
```


## Make constructors private and use static factory methods
 
Making the constructors private gives more control over the naming of static methods which create Objects, and also increases the ability to add caching of immutable objects if necessary e.g. the Integer class `valueOf` method maintains a cache for range `-128` to `127`.
 
e.g.

**Before:**

```
    public class MutableCoordinates {
    
        private int x;
        private int y;
    
        public MutableCoordinates(){
            x=0;
            y=0;
        }
    ...
```

**After:**

```
public class MutableCoordinates {

    private int x;
    private int y;

    private MutableCoordinates(final int x, final int y ){
        this.x=x;
        this.y=y;
    }

    public final static MutableCoordinates create(final int x, final int y ){
        return new MutableCoordinates(x, y );
    }
   ...
```

Declaring the static method `final` might seem like a step too far, but if our class allows extension then an extending class can hide the static method. Declaring the method as `final` prevents that at compile time.

#### Sensei Recipe

```
Name - Immutable: default constructor should set field values from parameters
Description - avoid default constructor and create a private constructor that sets the field values
Level - Warning
```

In the recipe below I match on a default constructor:



```
search:
  constructor:
    without:
      child:
        argument: {}
```

And then perform two actions:

- rewrite the constructor to be private, and force instantiation of the fields
- add a static factory method which returns a new object

```
availableFixes:
- name: "amend to private constructor that sets fields with a static factory create\
    \ method"
  actions:
  - rewrite:
      to: "private {{{ name }}}({{#sed}}{{#encodeString}}s/(.*),/$1/{{/encodeString}},{{#encodeString}}\n\
        {{#containingClass.fields}}final {{typeElement}} {{name}}, {{/containingClass.fields}}{{/encodeString}}{{/sed}}){\n\
        {{#containingClass.fields}}this.{{name}}={{name}};\n        {{/containingClass.fields}}\n\
        }"
      target: "self"
  - addMethod:
      method: "public static final {{{ name }}} create({{#sed}}{{#encodeString}}s/(.*),/$1/{{/encodeString}},{{#encodeString}}\n\
        {{#containingClass.fields}}final {{typeElement}} {{name}}, {{/containingClass.fields}}{{/encodeString}}{{/sed}}){\n\
        return new {{{ name }}}({{#sed}}{{#encodeString}}s/(.*),/$1/{{/encodeString}},{{#encodeString}}{{#containingClass.fields}}{{name}},\
        \ {{/containingClass.fields}}{{/encodeString}}{{/sed}});\n}"
      position:
        after:
          constructor: {}
      target: "parentClass"
```


The above recipe looks pretty complicated because of all the embedded `sed` functionality. That is simply to remove trailing `, `.

Also I used the Mustache `foreach` construct to iterate over all the fields. When writing a `foreach` in the template it is important to use `{{` rather than `{{{` and to have no spaces when matching on the `#` i.e. `{{#containingClass.fields}}` not `{{{ #containingClass.fields }}}`- why might the 2nd representation crop up? Because when we add variables from the `Show Variables` list, in order to have the text unescaped (i.e. normal) we add it to the template as `{{{`.



## Make fields `final`

Making fields `final` can be a simple check on the class code to help prevent us adding any unintended side-effects in our methods, because when our fields are `final`, we can only assign them values in the constructor.

**Before:**

```
    private int x;
    private int y;
```

**After:**

```
    private final int x;
    private final int y;
```

### Sensei Recipe

```
Name: Immutable: Fields should be final and set in the constructor
Description: Making fields final can highlight mutability issues
Level: Warning
```

```
search:
  field:
    without:
      modifier: "final"
```

```
availableFixes:
- name: "make fields `final`"
  actions:
  - rewrite:
      to: "{{{ modifierList }}} final {{{ typeElement }}} {{{ name }}};"
      target: "self"
```

## More Steps

If I applied all of the above recipes on my class I would still have some steps to implement because my code would no longer be syntactically correct.

Because my fields are final, my setter methods would no longer pass a syntax check:

```
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
```

And my `transformPositionBy` method would fail for the same reason:

```
    public void transformPositionBy(int xadjust, int yadjust){
        this.x+=xadjust;
        this.y+=yadjust;
    }
```

### Remove void setter methods

I can automatically remove any `public` `void` setter methods, and rely on the static factory method or constructor.

#### Sensei Recipe

In  my recipe I match any method with a name beginning with `set` which has been declared as `public` with a return type of `void`. And then have a rewrite action to delete the code.

```
Name - delete public void methods
Description - void setters can be replaced with use of constructor or static factory methods
Level - Warning
```

```
search:
  method:
    with:
      modifier: "public"
      returnType: "void"
    name:
      matches: "set.*"
```      

```
availableFixes:
- name: "Delete The public void method"
  actions:
  - rewrite:
      to: ""
      target: "self"
```

### Warn about non-void setter methods

If I had a non-void setter method then I probably don't want to automatically transform this code, as I would need to manually amend my code to avoid the need to return a value from a setter in the first place.

I could create a recipe to alert me to this condition.

```
Name - Immutable: avoid setters that return values
Description - avoid setters methods that return values
Level - Error
```

```
search:
  method:
    with:
      modifier: "public"
      returnType:
        not: "void"
    name:
      matches: "set.*"
```

```
availableFixes:
- name: "Fix the code by manual refactoring"
  actions: []
```      

### Avoid void methods

`void` methods suggest that some sort of side-effect has taken place. If we want to write immutable code then `void` suggests we might have a risk in the code.f

I will write a recipe to warn me about non-private `void` methods.

Given that a `void` method can be highly variable in its functionality, I will choose to replace the `void` declaration and declare the returning of an `Object` instead. This will trigger a syntax error which I as the programmer choose how to best fix in an immutable and context sensitive way.


```
Name - Immutable: avoid void methods
Description - void methods have side-effects, return a new oject or primitve instead
Level - Warning
```

```
search:
  method:
    not:
      modifier: "private"
    returnType: "void"
```

```
availableFixes:
- name: "Make this return an Object"
  actions:
  - changeType:
      type: "Object"
```

## Summary

Some of the basic concepts of immutability are simple:

- prevent extension of the class by making it `final`
- avoid setter methods and rely on fully constructed objects either from a constructor, or a static factory method
- avoid public `void` methods to reduce external side-effects
- make fields final to prevent internal side-effects

But the correct combination of transformations to choose varies depending on the context of the class and how far we want to take immutability.

To support the adoption of immutability, creating a contextual set of Sensei recipes can help flag the problem, and offer choice to the programmer about which set of transformations to apply.