package io.puradawid.voter.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;
import java.util.function.BiConsumer;

import io.puradawid.voter.io.VoterApplication;

class Setup {

    ConfigurableApplicationContext context;

    private void openApplication(String... args) {
        context = SpringApplication.run(VoterApplication.class, args);
    }

    private void closeApplication() {
        context.close();
    }

    void withRunningApplication(BiConsumer<ConfigurableApplicationContext, Integer> consumerContext) {
        int port = new Random().nextInt(8999) + 1000;
        openApplication("--spring.profiles.active=test", "--server.port=" + port);
        try {
            consumerContext.accept(context, port);
        } finally {
            closeApplication();
        }
    }

}