# About This Project

TLDR; [Sensei](https://plugins.jetbrains.com/plugin/14015-sensei-by-secure-code-warrior-) is a free IntelliJ plugin from [Secure Code Warrior](https://securecodewarrior.com/) that helps you codify quality coding practices as real-time warnings in your IDE, and create Quick Fix solutions to educate you or rewrite your code. This project has example recipes and code to experiment with.

**How to Use:**

- [Install Sensei Plugin](https://plugins.jetbrains.com/plugin/14015-sensei-by-secure-code-warrior-/getstarted)
- clone or download project
- open in IDE
- Example Sensei recipes in `.sensei` will be loaded
- read through Use Cases to see how recipes identify issues and fix code

_This is a Multi module maven project, when you clone this project, or download it as a zip, remember to open the top level `pom.xml` as the project. The `.sensei` folder contains example recipes which support the examples and embedded `readme.md` files._ 

## Docs and Links

- [Instructions to install the plugin from the IntelliJ Marketplace](https://plugins.jetbrains.com/plugin/14015-sensei-by-secure-code-warrior-/getstarted)
- [Official Documentation](https://sensei-docs-beta.securecodewarrior.com/)
- [Blog](https://insights.securecodewarrior.com/)
- [YouTube](https://www.youtube.com/channel/UCGXOkj26t4wItqf_BAt3W0Q)

## Example Video

If you want to see Sensei in Action then we have a short video:

[![](https://i.ytimg.com/vi/mjXGliXJ7M8/hqdefault.jpg)](https://www.youtube.com/watch?v=mjXGliXJ7M8)

## Example Use Cases for Sensei

This is a mixed media project, it contains `readme.md` files within the code structure. This helps make the project easier to review and understand when viewed on Github, and when using the markdown preview pane inside IntelliJ itself.

The following links are `readme.md` use case descriptions in the code base.

### POJO

- [Converting System.out.println to using a Logger](pojoexamples/src/test/java/logging)
- [Adding a Private Constructor to a Utility Class](pojoexamples/src/main/java/privateconstructors)
- [Basic Immutability](pojoexamples/src/main/java/immutability)
- [Java Gotchas - Bitwise vs Boolean Operators](pojoexamples/src/test/java/gotchas/bitwiseoperator)

### JUnit 5

- [Adding and Removing Annotations](junitexamples/src/test/java/annotations/avoidingreason)
- [Adding Parameters to Annotations](junitexamples/src/test/java/annotations/disabledreason)
- [Creating Library Documentation Links to Tutorials and Examples](junitexamples/src/test/java/annotations/documentationlinks) 
- [Amending Visibility Modifiers of Methods and Classes](junitexamples/src/test/java/modifier/visibility)

### Guice

- [Detecting Forgotten Guice Dependency Injection Wiring](guiceexamples/src/test/java/reporters)

### SQL Injection Fixes

- [Fix SQL Injection Vulnerability](sqlexamples/src/test/java/sqlinjection)


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

Once you have downloaded and installed Sensei from the [Intellij Marketplace](https://plugins.jetbrains.com/plugin/14015), we recommend installing via the plugins manager in IntelliJ preferences (just search for 'Sensei secure code' and you'll find it).

The easiest way to make Sensei work for you is to look at your coding process and consider:

-   What documentation do you keep looking up?    
    -   You could add some Sensei recipes that link back to that documentation.   
-   What simple mistakes do you keep making?
    -   You could temporarily codify that poor coding pattern as a matcher, and write a Quick Fix rewrite that amends the code to be what you really want to write.
-   What boilerplate code do you write to use a library?
    -   You could create a Quick Fix rule to write the code for you.
    

Since Sensei is designed to work alongside whatever static analysis tool you're using, if you find that the same violations are being reported from static analysis, then you could replicate the condition in a Sensei recipe. You can then add a Quick Fix to help train you, not just to identify the mistake but also to move quickly to writing the correct code.

---

## Sensei Team Support

When one person creates a recipe to improve their code quality or productivity, everyone on the team can benefit when the cookbooks are shared.

Sensei provides a number of mechanisms for sharing cookbooks:

- Store Cookbooks in the Project Under Version Control
- Storing Cookbooks in a shared folder
- Store Cookbooks in Github
- Zipped files over HTTP(s)

By sharing the cookbooks, Sensei helps teams collaborate on knowledge sharing. The collaboration helps improve communication and embed the agreed code quality approaches.

For example, sharing a cookbook allows:

team members to share useful recipes with each other.
team leads to codify agreed coding practices for junior staff. To identify common violations with a quick fix for the agreed version.
increased Inter-team co-operation. An AppSec team might create recipes to highlight a problem in the code, and the development team could write the quick fix.

The next few sections explain how to implement each of the sharing mechanisms.

## Store Cookbooks in the Project Under Version Control

The project `.sensei` folder is the default option when creating a cookbook file.

- `project://.sensei’

All cookbooks and recipes would be stored in a `.sensei` folder in your project. 

The easiest approach to sharing is to add the project `.sensei` folder to version control.

Then the `.sensei` folder can be managed like any other shared code artifact associated with the project. The cookbooks are stored as YAML configuration, making them easy to merge during any commit and review process.

This is the approach taken for the public `sensei-blog-examples` project.

https://github.com/SecureCodeWarrior/sensei-blog-examples

The `.sensei` folder contains the cookbook with all the recipes, and they are available to anyone that clones the repository.

## Store Cookbooks in Any Folder

Teams can also use cookbooks stored in central locations.

Saving the cookbook to any folder with shared write access permissions will allow the whole team to update the recipes, and import them into any project that they happen to be working on.

The location would be set to the directory path.

## Store Recipes in Github

Sensei can also access recipes that are stored in a Github repo. Both private and public repositories are supported.

### Github over SSH

SSH Repository access is configured using the following syntax for the `Location`

`git@github.com:SecureCodeWarrior/acookbook.git`

For this to work, the repository would contain the contents of a cookbook folder.

It is also possible to configure the branch and the subfolder for the cookbook e.g. in the `master` branch in the `cookbook` subfolder

e.g.

- `git@github.com:SecureCodeWarrior/sensei-blog-examples.git|master|.sensei`

An SSH key needs to be configured for private repositories.

- https://github.com/settings/keys

And the key should not have a passphrase.

### Github over HTTPS

It is also possible to access public repositories over HTTPS, and the same `repo.git|branch|folder` syntax is used e.g.

`https://github.com/SecureCodeWarrior/sensei-blog-examples.git|master|.sensei`

### Zipped over HTTP(s)

Sensei can also access cookbooks which are zipped, over HTTP or HTTPS.

e.g.

`http://localhost:8000/rules.sensei.zip`

The zip cookbook file should contain the contents of a cookbook folder e.g the `rules.sensei` file.

## Sharing Summary

Sensei supports using multiple cookbooks so that an individual programmer can have recipes that support their own learning and productivity.

More importantly, we know that teams work most effectively when knowledge is shared. Having shared team repositories, e.g. for a specific project, or a specific library, or for a shared set of migration patterns, can help boost team productivity and codify the team’s experience.

When a cookbook is shared, multiple teams can use the same cookbook which can also improve inter-team collaboration from different disciplines e.g. AppSec to development.

With four core sharing mechanisms available, Sensei hopefully has at least one approach you can use to increase collaboration on knowledge sharing.
