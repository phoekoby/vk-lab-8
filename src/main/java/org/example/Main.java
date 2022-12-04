package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.SneakyThrows;
import org.example.config.ProductModule;
import org.example.server.ServerCreator;
import org.example.service.MigrationService;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ProductModule());
        injector.getInstance(MigrationService.class).createMigrations();
        injector.getInstance(ServerCreator.class).initServer();
    }
}