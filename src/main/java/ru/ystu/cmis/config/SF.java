package ru.ystu.cmis.config;

import com.sun.xml.bind.api.impl.NameConverter;
import ognl.Token;
import org.h2.engine.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.ystu.cmis.domain.Event;
import sun.security.krb5.internal.Ticket;
import sun.security.provider.ConfigFile;

import javax.security.auth.login.AppConfigurationEntry;

public class SF {
    private SF(){}
    private static final SessionFactory sf;
    static {
        Config conf = Config.getConfig("db");
        try{
            Class.forName(conf.get(Config.DRIVER));

        } catch (Exception x){
            System.err.println("No def"+conf.get(Config.DRIVER)+"in classpath");
        }
        Configuration cfg = new Configuration()
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(Token.class);
        cfg.setProperty("hibernate.connection.url",conf.get(Config.URL));
        cfg.setProperty("hibernate.connection.username",conf.get(Config.USER));
        cfg.setProperty("hibernate.connection.password",conf.get(Config.PASSWORD));
        cfg.setProperty("hibernate.hbm2ddl.auto","update");
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(cfg.getProperties());
        sf = cfg.buildSessionFactory(builder.build());


    }
    public static SessionFactory getInstance(){
        return sf;
    }
}
