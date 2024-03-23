package utils.enums;

public enum DatabaseType {
    ORACLE,
    MYSQL,
    POSTGRESQL,
    SQLITE,
    MSSQL,
    IBM_DB2,
    MONGODB;
    
    public static DatabaseType parseDatabaseType(String connectionUrl) {
        validateConnectionUrl(connectionUrl);
        return identifyDatabaseType(connectionUrl);
    }
    
    private static void validateConnectionUrl(String connectionUrl) {
        if (connectionUrl == null || connectionUrl.isEmpty()) {
            throw new IllegalArgumentException("Connection URL is empty or null");
        }
    }
    
    private static DatabaseType identifyDatabaseType(String connectionUrl) {
        if (connectionUrl.startsWith("jdbc:mysql")) {
            return DatabaseType.MYSQL;
        } else if (connectionUrl.startsWith("jdbc:oracle")) {
            return DatabaseType.ORACLE;
        } else if (connectionUrl.startsWith("jdbc:postgresql")) {
            return DatabaseType.POSTGRESQL;
        } else if (connectionUrl.startsWith("jdbc:sqlite")) {
            return DatabaseType.SQLITE;
        } else if (connectionUrl.startsWith("jdbc:sqlserver")) {
            return DatabaseType.MSSQL;
        } else if (connectionUrl.startsWith("jdbc:db2")) {
            return DatabaseType.IBM_DB2;
        } else if (connectionUrl.startsWith("mongodb://")) {
            return DatabaseType.MONGODB;
        } else {
            throw new IllegalArgumentException("Unsupported database type");
        }
    }
}
