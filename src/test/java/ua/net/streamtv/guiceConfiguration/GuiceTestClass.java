package ua.net.streamtv.guiceConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import ua.net.streamtv.steps.ApiSteps;
import ua.net.streamtv.tests.SportsmanApiTest;

/**
 * Created by nskrypka on 8/29/15.
 */
public class GuiceTestClass implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ApiSteps.class).toInstance(new ApiSteps());
    }
}
