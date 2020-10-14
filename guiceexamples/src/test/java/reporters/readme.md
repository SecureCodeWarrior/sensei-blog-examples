# How to catch and fix a dependency injection issue using Sensei

- Author Credits: Charlie Eriksen, Alan Richardson

Scenario: We are learning Guice, and we keep making the simple mistake of
forgetting to wire things up.

We could...

- write unit tests, and catch the null pointer exceptions there
    - but some of our code is using a GUI and its legacy code and ... more excuses


## RunTime Exceptions from Incorrect Dependency Injection Wiring

The code below fails at runtime with a `NullPointerException`.

~~~~~~~~
Injector injector = Guice.createInjector(new SystemOutModule());
CountReporter reporter = injector.getInstance(CountReporter.class);

String [] lines5 = {"1: line", "2: line", "3: line", "4: line", "5: line"};

reporter.reportThisMany(Arrays.asList(lines5));
Assertions.assertEquals(5, reporter.getCount());
~~~~~~~~

The code is syntactically correct but fails because we missed out a `requestStaticInjection` in our `SystemOutModule` configuration.

~~~~~~~~
public class SystemOutModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(ILineReporter.class).to(SystemOutReporter.class);
    }
}
~~~~~~~~

When we try to use the `reporter`, created using the `Injector`, it is not fully instantiated and we receive a `NullPointerException` when we call `reportThisMany`.

We may well have missed that in our code review, or we didn't have unit tests which triggered the dependency injection, and it slipped out into production

## Warning Signs

In this case there is a warning sign, the `CountReporter` has a static field annotated with `@Inject` but... the `CountReporter` class itself is `package private`. In a complicated code base this could be a warning sign that it isn't used because the Module class configuring the bindings needs to be in the same package for this to work.

~~~~~~~~
class CountReporter {

    @Inject
    private static ILineReporter reporter;
~~~~~~~~

Another error that we made, which we might have picked up in a code review, is that we forgot to actually bind the fields in the `SystemOutModule` `configure` method.

~~~~~~~~
binder().requestStaticInjection(CountReporter.class);
~~~~~~~~

Had we written the `requestStaticInjection` code then the Syntax Error generated when trying to use the `CountReporter` would have alerted us to the simple error.

But... we forgot, and there were no syntactical warning signs in the code.

## How could Sensei help?

We probably wouldn't use Sensei to pick up the missing `requestStaticInjection` since all
our Guice configuration wiring would need to use that method, and we can't guarantee that
all wiring is going to be as simple as this use-case.

But we could write a Sensei rule to pick up some warning signs that our code is not up to scratch.

In this case, create a Sensei recipe to find any classes with `@Inject` annotated fields, where the classes are not public. Because then they are unlikely to have been wired up.

By creating a recipe, then we will have a warning sign early, during the coding, and reduce the reliance on our pull requests or resolving our tech debt to allow us to add Unit Tests.

## How to create a recipe?

The task I want to complete is:

- Create a recipe which matches fields annotated with `@Inject` which are in `protected private` classes

That should hopefully give us enough warning to find Modules using it and add the missing wiring code.

In my `CountReporter` class I will use `Alt+Enter` to `Create a new Recipe` and I will `start from scratch`

I will name this and add a description:

~~~~~~~~
Name: Guice Injected Field Not Public
Description: If the Injected field is not public then the code might not be wired up
Level: Warning
~~~~~~~~

~~~~~~~~
search:
  field:
    with:
      annotation:
        type: "com.google.inject.Inject"
    in:
      class:
        without:
          modifier: "public"
~~~~~~~~

~~~~~~~~
availableFixes:
- name: "Change class to public. Remember to also request injection on this class"
  actions:
  - changeModifiers:
      visibility: "public"
      target: "parentClass"
~~~~~~~~

## Fix

When the recipe is triggered, then I still have a manual step to perform in my code, adding the line containing `requestStaticInjection` to fully instantiate the object.

~~~~~~~~
public class SystemOutModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(ILineReporter.class).to(SystemOutReporter.class);
        // instantiate via dependency injection
        binder().requestStaticInjection(CountReporter.class);
    }
}
~~~~~~~~

## Summary

If we ever find ourselves making a mistake with a common root pattern, then Sensei can help codify the knowledge around detecting and fixing the issue, and then hopefully, it won't slip through code reviews and into production.