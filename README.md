# Sensei Blog Examples

Example code to be used in Sensei blog posts.

TLDR; [Sensei](https://plugins.jetbrains.com/plugin/14015-sensei-by-secure-code-warrior-) is a free IntelliJ plugin from [Secure Code Warrior](https://securecodewarrior.com/) that helps you codify quality coding practices as real-time warnings in your IDE, and create Quick Fix solutions to educate you or rewrite your code.

- [Download the plugin from the IntelliJ Marketplace](https://plugins.jetbrains.com/plugin/14015-sensei-by-secure-code-warrior-)
- [Official Documentation](https://sensei-docs-beta.securecodewarrior.com/)


## About This Project

This is a Multi module maven project where modules are for different technologies and examples.

If you clone this project, or download it as a zip, remember to open the top level `pom.xml` as the project.

The `.sensei` folder contains example recipes which support the examples and embedded `readme.md` files. 

This is a mixed media project, it contains `readme.md` files within the code structure. This helps make the project easier to review and understand when viewed on Github, and when using the markdown preview pane inside IntelliJ itself.

## Example Use Cases for Sensei

- [Converting System.out.println to using a Logger](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/pojoexamples/src/test/java/logging)
- [Adding and Removing Annotations](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/junitexamples/src/test/java/annotations/avoidingreason)
- [Adding Parameters to Annotations](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/junitexamples/src/test/java/annotations/disabledreason)
- [Creating Library Documentation Links to Tutorials and Examples](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/junitexamples/src/test/java/annotations/documentationlinks) 
- [Adding a Private Constructor to a Utility Class](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/pojoexamples/src/test/java/privateconstructors)
- [Detecting Forgotten Guice Dependency Injection Wiring](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/guiceexamples/src/test/java/reporters)
- [Amending Visibility Modifiers of Methods and Classes](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/junitexamples/src/test/java/modifier/visibility)
---

## What is Sensei?

The Sensei plugin provides an easy way to find specific code patterns in your source code, and then apply rewrite rules to amend the matching code. All within the Intellij IDE, and in real-time.

For example, you could create a rule that matches on JUnit `@Disabled` annotations which do not have a reason, Sensei would then tell you about the issue by highlighting the code in the IDE.

Additionally, when you `alt+enter`, you can have the option to `Add a todo comment parameter`.

When selected, this would amend the code to add a boilerplate reason, which you can then amend, and if you don’t, it will show up in your TODO panel.

e.g. `@Disabled` would become ` @Disabled("TODO: add a description here")`

Sensei combines the functionality of a Static Analysis code scanner with a code rewriting engine.

## IntelliJ Intention Actions

Based on the above description, the obvious alternative (if you were not using Sensei) is to use the IntelliJ Intention Actions functionality.

Sensei differs from IntelliJ Intention Actions because the aim with Sensei is to provide a way to create matchers and rewriters which are project specific, or even local to an individual developer.

We have tried to put together a GUI that makes both the matching and rewrite rules easy to write and experiment with.

## How can it help me personally improve?

When I'm learning a new library, it takes me time to build up muscle memory around the methods and formatting. So I might choose to create personal recipes which

-   link off to the official documentation or tutorial pages
-   have boilerplate templates which are most effective
-   fix poor coding practices
-   add boilerplate code to help use a library
    

I can use Sensei to build temporary recipes which prompt me for the current practices I've chosen to use and help me build up effective habits. And the recipes are temporary because I can remove them when I outgrow them.

## How can it help my team improve?

In the same way we can help teams build up muscle memory around agreed coding standards.

Creating cookbooks of recipes that we apply when we find the same comments in pull request reviews. Since the cookbooks are stored in version control with the project, they are available to everyone on the project. And we can switch them off when we no longer need prompting.

## Sensei helps provide feedback early

What we've tried to build with Sensei is a way of pulling the feedback that helps us improve, and reminders of corrective action, as early into the coding process as we can.

Rather than wait for

-   the results of a static analyser
-   the comments from a code review
    

We can instead see the feedback, for custom standards we want to enforce, as we code.

And we have either reminders, or actual rewrite rules, to help us write code that complies with the standards.

## Sensei is flexible

In that way, Sensei is a bit of a mix, since it’s:

-   part Static Analyser   
-   part coding tutor
-   part rewrite engine
    

Sensei is flexible enough to make the job of saying "What is Sensei?" that little bit harder.

## Sensei fills a gap in the programming workflow

We've tried to make Sensei the missing piece of the programmer workflow that helps you improve specific elements in your coding style, or library use, that you and your team are currently working with.

This flexibility means that it takes a little more time to get to grips with Sensei than a static analysis tool or the built-in IntelliJ Intensions. Still, by spending the time to experiment, you will gain a new way to speed up your learning in your personal development process.

## How to experiment?

Once you have downloaded and installed Sensei from the [Intellij Marketplace](https://plugins.jetbrains.com/plugin/14015).

The easiest way to make Sensei work for you is to look at your coding process and consider:

-   What documentation do you keep looking up?    
    -   You could add some Sensei recipes that link back to that documentation.   
-   What simple mistakes do you keep making?
    -   You could temporarily codify that poor coding pattern as a matcher, and write a Quick Fix rewrite that amends the code to be what you really want to write.
-   What boilerplate code do you write to use a library?
    -   You could create a Quick Fix rule to write the code for you.
    

Since Sensei is designed to work alongside whatever static analysis tool you're using, if you find that the same violations are being reported from static analysis, then you could replicate the condition in a Sensei recipe. You can then add a Quick Fix to help train you, not just to identify the mistake but also to move quickly to writing the correct code.