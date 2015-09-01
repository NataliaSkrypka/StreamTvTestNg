package ua.net.streamtv.guiceConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by nskrypka on 9/1/15.
 */
public class GuicePropertiesModule implements Module {
    @Override
    public void configure(Binder binder) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("TestProperties.properties"));
            Names.bindProperties(binder, props);
        } catch (IOException e) {
            System.out.println("Could not load config: " + e.getMessage());
            System.exit(1);
        }
    }
}
