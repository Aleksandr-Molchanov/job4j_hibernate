package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model avendator = Model.of("Avendator");
            session.save(avendator);
            Model huracan = Model.of("Huracan");
            session.save(huracan);
            Model centenario = Model.of("Centenario");
            session.save(centenario);
            Model gallardo = Model.of("Gallardo");
            session.save(gallardo);
            Model murcielago = Model.of("Murcielago");
            session.save(murcielago);

            Brand brand = Brand.of("Lamborghini");
            brand.addModel(session.load(Model.class, 1));

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}