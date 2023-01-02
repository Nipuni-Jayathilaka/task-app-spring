package lk.ijse.dep9.dao;

import lk.ijse.dep9.dao.custom.impl.ProjectDAOImpl;
import lk.ijse.dep9.dao.custom.impl.QueryDAOImpl;
import lk.ijse.dep9.dao.custom.impl.TaskDAOImpl;
import lk.ijse.dep9.dao.custom.impl.UserDAOImpl;

import java.sql.Connection;

public class DAOFactory {

    private static DAOFactory daoFactory;

    public DAOFactory() {
    }
    public static DAOFactory getInstance(){
        return (daoFactory==null) ? (daoFactory=new DAOFactory()): daoFactory;

    }
    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes daoTypes, Class<T> tClass){
        switch (daoTypes) {
            case TASK:
                return tClass.cast(new UserDAOImpl(connection));
            case PROJECT:
                return tClass.cast(new ProjectDAOImpl(connection));
            case USER:
                return tClass.cast(new TaskDAOImpl(connection));
            case QUERY:
                return tClass.cast(new QueryDAOImpl(connection));
            default:
                return null;
        }
    }
}
