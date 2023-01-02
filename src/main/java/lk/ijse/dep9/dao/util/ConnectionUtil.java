package lk.ijse.dep9.dao.util;

import java.sql.Connection;

public class ConnectionUtil {
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();

    public static void setConnection(Connection connection){
        threadLocal.set(connection);
    }

    public static Connection getThreadLocal() {
        return threadLocal.get();
    }
}
