package reporters;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import staticinjection.SystemOutModule;

import java.util.Arrays;

public class StaticInjectionIssueTest {

    /*

    Scenario: We are learning Guice, and we keep making the simple mistake of
    forgetting to wire things up.

    We could...

    - write unit tests, and catch the null pointer exceptions there
        - but some of our code is using a GUI and its legacy code and ... more excuses


    e.g. this code fails because we missed out a `requestStaticInjection` in our `SystemOutModule` and
    we may have missed that in our code review, we didn't have unit tests, and it slipped out into
    production.

    But in this case there is a warning sign the `CountReporter` has a static field annotated with `@Inject`
    but... the `CountReporter` is `package private`.

    In a complicated code base this could be a warning sign that it isn't used because the Module configuring
    the bindings needs to be in the same package for this to work.

    Another error that we made, which we might have picked up in a code review, is that we forgot
    to actually bind the fields in the `SystemOutModule` `configure` method.

    ~~~~~~~~
    binder().requestStaticInjection(CountReporter.class);
    ~~~~~~~~

    Had we written the `requestStaticInjection` code then the Syntax Error generated when trying
    to use the `CountReporter` would have alerted us to the simple error. But... we forgot,
    and there were no warning signs in the code.

    ## How could Sensei help?

    We probably wouldn't use Sensei to pick up the missing `requestStaticInjection` since all
    our Guice configuration wiring would need to use that method, and we can't guarantee that
    all wiring is going to be as simple as that.

    But we could write a Sensei rule to pick up some warning signs that our code is not up to scratch.
    In this case, create a Sensei recipe to find any classes with `@Inject' annotated fields,
    where the classes are not public. Because then they are unlikely to have been wired up.

    By creating a recipe, then we will have a warning sign early, during the coding, and reduce the
    reliance on our pull requests or resolving our tech debt to allow us to add Unit Tests.

    Task:

    - Create a recipe which matches fields annotated with `@Inject` which are in `protected private` classes

    That should hopefully give us enough warning to find Modules using it and add the missing wiring code.

     */
    @Test
    void reporterWithStaticObserver(){

        Injector injector = Guice.createInjector(new SystemOutModule());
        CountReporter reporter = injector.getInstance(CountReporter.class);

        String [] lines5 = {"1: line", "2: line", "3: line", "4: line", "5: line"};

        try {
            reporter.reportThisMany(Arrays.asList(lines5));
            Assertions.assertEquals(5, reporter.getCount());

        }catch(NullPointerException e){
            // when the Dependency Injection fails then we'll drop in here
            System.out.println(
                    "TODO: fix the dependency injection issue -" +
                    " private protected class, and missing static injection");
        }
    }
}
