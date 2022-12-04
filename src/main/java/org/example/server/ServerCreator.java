package org.example.server;

import com.google.inject.Inject;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlets.QoSFilter;
import org.example.config.LoginConfig;
import org.example.config.ProductListener;
import org.example.filter.PostFilter;
import org.example.security.RolesFilter;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Objects;

public class ServerCreator {

    private final DefaultServer defaultServer;


    private final PostFilter postFilter;

    @Inject
    public ServerCreator(DefaultServer defaultServer, PostFilter postFilter) {
        this.defaultServer = defaultServer;
        this.postFilter = postFilter;
    }

    public void initServer() throws Exception {
        final Server server = defaultServer.build();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        context.addServlet(HttpServletDispatcher.class,"/");
        context.addEventListener(new ProductListener());

        context.addFilter(new FilterHolder(postFilter), "/products", EnumSet.of(DispatcherType.REQUEST));

        final QoSFilter filter = new QoSFilter();
        final FilterHolder filterHolder = new FilterHolder(filter);
        filterHolder.setInitParameter("maxRequests", "1");
        context.addFilter(filterHolder, "/products", EnumSet.of(DispatcherType.REQUEST));


        final String jdbcConfig = Objects.requireNonNull(LoginConfig.class.getResource("/jdbc_config")).toExternalForm();
        final JDBCLoginService jdbcLoginService = new JDBCLoginService("login", jdbcConfig);
        final ConstraintSecurityHandler securityHandler = new RolesFilter().build(jdbcLoginService);

        server.addBean(jdbcLoginService);
        securityHandler.setHandler(context);
        server.setHandler(securityHandler);
        server.start();
    }
}
