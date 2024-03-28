package util;

import exceptions.DatabaseException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.net.URISyntaxException;

@WebListener
public class ApplicationInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionManager.initPool();
        } catch (URISyntaxException e) {
            throw new DatabaseException("Database is unavailable");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionManager.closeConnectionPool();
    }
}