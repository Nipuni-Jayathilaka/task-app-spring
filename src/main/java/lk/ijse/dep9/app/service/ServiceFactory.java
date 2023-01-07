package lk.ijse.dep9.app.service;

import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.service.custom.impl.ProjectTaskImpl;
import lk.ijse.dep9.app.service.custom.impl.UserServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    public ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return (serviceFactory==null)?(serviceFactory=new ServiceFactory()): serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes, Class<T> tClass){
        switch (serviceTypes){
            case USER:
                return tClass.cast(new UserServiceImpl());
            case PROJECT_TASK:
                return tClass.cast(new ProjectTaskImpl());
            default:
                return null;
        }
    }
}
