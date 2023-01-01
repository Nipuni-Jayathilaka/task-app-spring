package lk.ijse.dep9.dao.util;

import lk.ijse.dep9.entity.Project;
import lk.ijse.dep9.entity.Task;
import lk.ijse.dep9.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.table.TableStringConverter;

public class HibernateUtil {

    private SessionFactory sessionFactory= buildSessionFactory();

    private static SessionFactory getSessionFactory(){
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("/application.properties")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Task.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }



    private SessionFactory buildSessionFactory() {
        return sessionFactory;
    }
}
