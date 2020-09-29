## Adding Parameters to Annotations

- Demonstrate matching annotations
- Amending annotation using template

Disabled tests without a reason can prove problematic over the long term
because we forget why we disabled it.

Over time the code base moves on, the test is not kept in step with
the code so eventually becomes redundant.

It is generally a good idea to add a description.

We can write a rule for that, and a Quick Fix that reminds us to add the actual description.

Task:

- match the Disabled annotation without any parameters
- change the Disabled annotation to have a parameter with marker text "TODO: add a description here"


Solution:

General

~~~~~~~~
name: remember to add disabled description
short description: @Disabled should really have a description explaining why
Level: Error
~~~~~~~~


Search

~~~~~~~~
search:
  annotation:
    type: "org.junit.jupiter.api.Disabled"
    without:
      parameters:
      - {}
~~~~~~~~

QuickFix:

~~~~~~~~
availableFixes:
- name: "Add a todo comment parameter"
  actions:
  - rewrite:
      to: "{{{ . }}}(\"TODO: add a description here\")"
      target: "self"
~~~~~~~~

