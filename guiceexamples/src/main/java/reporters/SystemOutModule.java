package reporters;

import com.google.inject.AbstractModule;

public class SystemOutModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(ILineReporter.class).to(SystemOutReporter.class);
        binder().requestStaticInjection(CountReporter.class);
    }
}
