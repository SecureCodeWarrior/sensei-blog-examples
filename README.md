# Sensei Blog Examples

Example code to be used in Sensei blog posts.

A Multi module maven project where modules are for different technologies and examples.

---

## What is Sensei

What is Sensei?

The Sensei plugin provides an easy way to find specific code patterns, and then apply rewrite rules to amend the matching code.

For example:

- you could create a rule that matches on JUnit `@Disabled` annotations which do not have a reason
    - and in addition to 'telling you about the issue', when you `alt+enter` you can have the option to `add a todo comment` which adds a boiler plate reason, which you can amend.

Sensei combines the functionality of a Static Analysis code scanner with a code rewriting engine.

Based on that description,  the obvious alternative are the IntelliJ Intentions.

The aim with Sensei is to provide a way to create matchers and rewriters which are project specific, or even local to an individual developer.

And we've tried to put together a GUI that makes both the matching and rewrite rules easy to write and experiment with.

For example, when I'm learning a new library, it takes me time to build up muscle memory around the methods and formatting. So I might choose to create personal recipes which:

- link off to the official documentation or tutorial pages
- have boilerplate templates which are most effective

I can use Sensei to built temporary recipes which prompt me for the current practices I've chosen to use, to help me build up effective habits. And they are temporary because I can delete them when I outgrow them.

In the same way we can help teams build up muscle memory around agreed coding standards. Creating cookbooks of recipes that we apply when we find the same comments in pull request reviews. But switch them off when we no longer need prompting.

What we've tried to build with Sensei is a way of pulling the reminder and feedback that help us improve as early into the coding process as we can.

Rather than wait for:

- the results of a static analyser
- the comments from a code review

We can instead see the feedback, for custom standards we want to enforce, as we code. And we have either reminders, or actual rewrite rules, to help us write code that complies with the standards.

In that way, Sensei is a bit of a mix:

- It's a little bit of a Static Analyser
- a little bit of a tutor
- a little bit of a rewrite engine

Its flexible enough to make the job of saying "What is Sensei?" that little bit harder.

We've tried to make it that missing piece of the programmer workflow that helps you improve specific elements in your coding style or library use that you and your team are currently working with.

This flexibility comes at a price though, it means it takes a little more time to get to grips with than a static analysis tool or built in Intensions, but by spending the time, you have a way of speeding up your learning in your personal development process.

The easiest way to make Sensei work for you is to look at your coding process and look for:

- what documentation do you keep looking up?
    - you could add some Sensei recipes that link back to that documentation.
- what simple mistakes do you keep making?
    - you could temporarily codify that poor coding pattern as a matcher, and write a quick fix rewrite that adds the code you want to write.

Since Sensei is designed to work alongside whatever static analysis tool you're using. If you find that the same violations are being reported from static analysis then you could replicate the condition in a Sensei recipe. But also add a Quick Fix to help train you, not just to identify the mistake, but to move quickly to the correct code.