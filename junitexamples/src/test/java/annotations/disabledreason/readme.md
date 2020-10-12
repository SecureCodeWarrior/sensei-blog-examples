## Adding Parameters to Annotations

- Demonstrate matching annotations
- Amending annotation using template

Disabled tests without a reason can prove problematic over the long term
because we forget why we disabled it.

```
@Disabled
void thisTestMethodHasNoDisabledReason(){
    Assertions.fail("This test is disabled and should not run");
}
```

Over time the code base moves on, the test is not kept in step with
the code so eventually becomes redundant.

It is generally a good idea to add an explanatory description as the annotation parameter.

```
@Disabled("Disabled to demonstrate adding a reason")
void thisTestMethodHasDisabledReason(){
    Assertions.fail("This test is disabled and should not run");
}
```

## A Sensei Recipe

We can write a recipe to detect when `@Disabled` is used with no explanation, and a Quick Fix that reminds us to add the actual explantation.

### Task:
- match the Disabled annotation without any parameters
- change the Disabled annotation to have a parameter with marker text "TODO: add a description here"


### Solution

I use `Alt+Enter` to Create a new Recipe and add the basic descriptive text.

General:

~~~~~~~~
name: remember to add disabled description
short description: @Disabled should really have a description explaining why
Level: Error
~~~~~~~~

In the recipe editor, I change the Search to match an annotation.

This will highlight all annotations in the preview.

Having done that, I want to filter on the `type'. I could just use `Disabled` but I fully qualify the class with the package so that it only matches the annotation from JUnit 5. Because the preview is displayed, I can easily copy and paste this from the code.

I then want to match only annotations without Parameters, and I can use the GUI to do that.

i.e. Search:

~~~~~~~~
search:
  annotation:
    type: "org.junit.jupiter.api.Disabled"
    without:
      parameters:
      - {}
~~~~~~~~

For my QuickFix I will use a rewrite action.

I use the `Show Variables` functionality to show me the Mustache variables and preview the contents. And then I add the extra code needed to create the place marker comment.

i.e. QuickFix:

~~~~~~~~
availableFixes:
- name: "Add a todo comment parameter"
  actions:
  - rewrite:
      to: "{{{ . }}}(\"TODO: add a description here\")"
      target: "self"
~~~~~~~~

