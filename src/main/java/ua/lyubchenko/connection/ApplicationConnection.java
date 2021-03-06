package ua.lyubchenko.connection;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.lyubchenko.domains.*;

public class ApplicationConnection {
    private static ApplicationConnection instance;
    private final Session session;

    @SneakyThrows
    private ApplicationConnection() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Skill.class)
                .buildSessionFactory();
            this.session = sessionFactory.openSession();


    }

    public static Session getInstance() {
        if (instance == null)
            instance = new ApplicationConnection();
        return instance.session;
    }
}