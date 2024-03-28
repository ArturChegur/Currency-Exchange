package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public final class ConnectionManager {
    private static HikariDataSource connectionPool;

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    static void closeConnectionPool() {
        connectionPool.close();
    }

    static void initPool() throws URISyntaxException {
        String path = Paths.get(Objects.requireNonNull(ConnectionManager.class
                        .getClassLoader()
                        .getResource("database.sqlite")).toURI())
                .toAbsolutePath().toString();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + path);
        config.setMaximumPoolSize(10);
        connectionPool = new HikariDataSource(config);
    }
}