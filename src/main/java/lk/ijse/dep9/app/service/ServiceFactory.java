package lk.ijse.dep9.app.service;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    public ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return (serviceFactory==null)?(serviceFactory=new ServiceFactory()): serviceFactory;
    }
}
