## Improving Personal Process using @Disabled

- Demonstrate method name matching with regex
- Demonstrate use of SED to rewrite method contents
- TODO: 'alternatives' without using SED use other method attributes from variables list


When I was learning JUnit, I could only keep so much in my head at any one time
and I constantly forgot how to skip tests when they were not working.

Ideally I would write:

~~~~~~~~
    @Disabled
    @Test
    void canWeAddTwoNumbers(){
        Assertions.fail("this test was skipped and should not run");
    }
~~~~~~~~

I had to train myself to use `@Disabled`.

When I forgot, I would instead rename the test and remove the `@Test` annotation.

~~~~~~~~
class SkipThisTest {

    void SKIPTHIScanWeAddTwoNumbers(){
        Assertions.fail("this test was skipped and should not run");
    }   
}
~~~~~~~~

It wasn't good, but it got the job done. If only I'd had something like Sensei
where I could have created a rule to help me remember. That's what this example
is all about.

Task:

Create a rule which finds methods which have been 'skipped' or 'disabled' by renaming the method and then create a quickfix which renames the method and adds an `@Test` and `@Disabled` annotation.


Notes:

- When creating the annotation use "@Disabled" not "Disabled"
- Adding the annotation as a fully qualified class path will also add an `import` statement

## Recipe Settings

- `Name: Make @Disabled @Test from SKIPTHIS`
- `Short Description: Stop naming methods SKIPTHIS, use @Disabled @Test instead`

~~~~~~~~
search:
  method:
    name:
      matches: "SKIPTHIS.*"
~~~~~~~~


## QuickFix Settings

~~~~~~~~
availableFixes:
- name: "Add @Disabled and @Test Annotation"
  actions:
  - addAnnotation:
       annotation: "@org.junit.jupiter.api.Test"
  - addAnnotation:
      annotation: "@org.junit.jupiter.api.Disabled"
  - rewrite:
       to: "{{#sed}}s/(.*) SKIPTHIS(.*)/$1 $2/,{{{.}}}{{/sed}}"
~~~~~~~~

The QuickFix that I created:

- adds two annotations, both are fully qualified to bring in the associated import statements
- uses `sed` to rewrite the method name

The `sed` implementation:

- takes two arguments separated by `,`
- the first argument is the  sed command
- the second argument is the text to apply the command to
- requires that when the arguments themselves contain commas, that they are warpped with `{{#encodeString}}` and `{{/encodeString}}`
    - e.g. `{{#encodeString}}{{{.}}}{{/encodeString}}`

# Reverse Recipe

Reversing out the above change.

Since we can use this in demos we may want to reverse it.

And we might choose to do that through a Git revert or a bunch of `Ctrl+Z`

Or we could create a recipe.

Thinking it through I want to find a method annotated with @Disabled
but only in the class SkipThisTest where I do the demo:

Recipe Settings Search

~~~~~~~~
search:
  method:
    annotation:
      type: "Disabled"
    in:
      class:
        name: "SkipThisTest"
~~~~~~~~

But to avoid making the code look like it is an error when I finish,
I change the general setting on the recipe to be a Warning.

This highlights it in the code but doesn't make it look like a major problem.

And for the Quick fix, since we have matched the method I use the rewrite action
and populate the template using the variables.

~~~~~~~~
availableFixes:
- name: "Remove Disabled and rename to SKIPTHIS..."
  actions:
  - rewrite:
      to: ""
      target: "self"
  - rewrite:
      to: "{{{ returnTypeElement }}} SKIPTHIS{{{ nameIdentifier }}}{{{ parameterList\
        \ }}}{{{ body }}}"
~~~~~~~~

I basically add every variable except the modifier (since I want to get rid of the annotations), and add the `SKIPTHIS` text into the template.

This makes it easy to revert the changes during a demo, because it is isolated to this demo class, and has the side-effect of highlighting the code we are working on in the demo.

One weakness with this, is that by removing the modifiers, I removed any other annotations as well.

## Add another Action

I can add another action, to give me a choice when the `alt+enter` is used to display quick fix.

~~~~~~~~
availableFixes:
- name: "Remove Disabled and rename to SKIPTHIS..."
  actions:
  - rewrite:
      to: ""
      target: "self"
  - rewrite:
      to: "{{{ returnTypeElement }}} SKIPTHIS{{{ nameIdentifier }}}{{{ parameterList\
        \ }}}{{{ body }}}"
      target: "self"
- name: "Remove Disabled, keep other annotations, and rename to SKIPTHIS..."
  actions:
  - rewrite:
      to: ""
      target: "self"
  - rewrite:
      to: "{{#sed}}s/(@Disabled\n.*@Test)//,{{{ modifierList }}}{{/sed}}\n\
        {{{ returnTypeElement }}} SKIPTHIS{{{ nameIdentifier }}}{{{ parameterList\
        \ }}}{{{ body }}}"
      target: "self"
~~~~~~~~

Here, I am adding an additional line in the Quick Fix.

~~~~~~~~
{{#sed}}s/(@Disabled\n.*@Test)//,{{{ modifierList }}}{{/sed}}
~~~~~~~~

This takes the modifier list, encodes it as a string, then uses `sed` to remove the line with `@Disabled` from the string, but leave all other lines in the modifier, i.e. all other annotations, alone.


NOTE: Remember to add the "," in the `sed`, otherwise you will see a comment added to your preview, because this is how Sensei alerts you to syntax errors in the `sed` command.

I could clone the recipe, but if I do, then I have to remember to uncheck the "Add disable entry for cloned recipe" checkbox when I do, otherwise only the cloned recipe will be available for use.

Since both fixes are available from the same search, I will make them actions on the same recipe.


## Nested `sed` calls

I was lucky that I could match both the `@Disabled` and `@Test` in a single search and replace.

If I wanted to have a sequence of sed commands then I would nest them:

```
{{#sed}}s/@Test//,{{#sed}}s/@Disabled\n//,{{{ modifierList }}}{{/sed}}{{/sed}}
```

In the above example I apply the `@Test` replacement to the results of applying the `@Disabled` replacement on the `{{{ modifierList }}}`.

