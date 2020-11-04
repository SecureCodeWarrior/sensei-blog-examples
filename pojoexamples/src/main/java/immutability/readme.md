## Immutability


Steps to converting this into an immutable class:

- make class `final'
- make constructors private and use factory methods to create objects
- make fields final, forcing them to be instantiated in a constructor

TODO:

- remove setter methods and rely on the static factory methods
- change `void` methods to return a new object
    - given that a `void` method can be highly variable in its functionality, we might choose to just remove `void` and declare the returning of an Object, to trigger a syntax error which I as the programmer choose how to best fix in an immutable way.


## Make class `final`

A simple immutable class can be impacted if we can extend it.

Making it `final` might help.

```
Name: Immutability: use final classes to prevent extension
Description: Make the classes final to prevent people extending as mutable
Level: Warning
```

Recipe Settings:

```
search:
  class:
    allOf:
    - modifier: "public"
    without:
      modifier: "final"
```


QuickFix:

```
availableFixes:
- name: "Remove public modifier"
  actions:
  - changeModifiers:
      final: true
```

## Make constructors private
 
Making the constructors private gives more control over the naming, but also increases the ability to add  
 caching of immutable objects if necessary e.g. the Integer class `valueOf` method maintains a cache for range `-128` to `127`.
 
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
      method: "public final static {{{ name }}} create({{#sed}}{{#encodeString}}s/(.*),/$1/{{/encodeString}},{{#encodeString}}\n\
        {{#containingClass.fields}}final {{typeElement}} {{name}}, {{/containingClass.fields}}{{/encodeString}}{{/sed}}){\n\
        return new {{{ name }}}({{#sed}}{{#encodeString}}s/(.*),/$1/{{/encodeString}},{{#encodeString}}{{#containingClass.fields}}{{name}},\
        \ {{/containingClass.fields}}{{/encodeString}}{{/sed}});\n}"
      position:
        after:
          constructor: {}
      target: "parentClass"
```

It might be appropriate to create smaller rules to handle more intermediate step transformations, rather than trying to do a lot in an single recipe.

## Make fields `final`

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