## Immutability

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