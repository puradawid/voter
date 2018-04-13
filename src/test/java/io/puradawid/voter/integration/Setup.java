package io.puradawid.voter.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.function.Consumer;

import io.puradawid.voter.io.VoterApplication;

public class Setup {

    ConfigurableApplicationContext context;

    private void openApplication(String args) {
        context = SpringApplication.run(VoterApplication.class, args);
    }

    private void closeApplication() {
        context.close();
    }

    public void withRunningApplication(Consumer<ConfigurableApplicationContext> consumerContext, int port) {
        openApplication("--server.port=" + port);
        try {
            consumerContext.accept(context);
        } finally {
            closeApplication();
        }
    }

}