package org.example.config;

import com.google.inject.Inject;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.example.security.RolesFilter;

import java.io.IOException;
import java.util.Objects;

public class LoginConfig {

    private final Server server;

    @Inject
    public LoginConfig(Server server) throws IOException {
        this.server = server;
        final String jdbcConfig = Objects.requireNonNull(LoginConfig.class.getResource("/jdbc_config")).toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService("login", jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new RolesFilter().build(jdbcLoginService);

        server.addBean(jdbcLoginService);
//        securityHandler.setHandler(context);
        server.setHandler(securityHandler);
    }
}
