package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.view.DrawNumberConsoleView;
import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    @SuppressWarnings("unchecked")
    public static void main(final String... args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);

        Class<DrawNumberConsoleView> console = DrawNumberConsoleView.class;
        Constructor<DrawNumberConsoleView> consoleConstructor = null;
        for(var constructor : console.getConstructors()){
            if(constructor.getParameterCount() == 0){
                consoleConstructor = (Constructor<DrawNumberConsoleView>) constructor;
            }
        }
        Class<DrawNumberSwingView> swing = DrawNumberSwingView.class;
        Constructor<DrawNumberSwingView> swingConstructor = null;
        for(var constructor : swing.getConstructors()){
            if(constructor.getParameterCount() == 0){
                swingConstructor = (Constructor<DrawNumberSwingView>) constructor;
            }
        }

        List<DrawNumberView> instancesList = new ArrayList<>();
        instancesList = Arrays.asList(consoleConstructor.newInstance(),
                            consoleConstructor.newInstance(),
                            consoleConstructor.newInstance(),
                            swingConstructor.newInstance(),
                            swingConstructor.newInstance(),
                            swingConstructor.newInstance());
        
        for(var instance : instancesList){
            app.addView(instance);
        }
    }
}
