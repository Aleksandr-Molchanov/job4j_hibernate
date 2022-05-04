package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        List<Brand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand lamborghini = Brand.of("Lamborghini");
            session.save(lamborghini);
            Model avendator = Model.of("Avendator", lamborghini);
            session.save(avendator);
            Model huracan = Model.of("Huracan", lamborghini);
            session.save(huracan);
            Model centenario = Model.of("Centenario", lamborghini);
            session.save(centenario);
            Model gallardo = Model.of("Gallardo", lamborghini);
            session.save(gallardo);
            Model murcielago = Model.of("Murcielago", lamborghini);
            session.save(murcielago);
            list = session.createQuery(
                    "select distinct b from Brand b join fetch b.models"
            ).list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Model model : list.get(0).getModels()) {
            System.out.println(model);
        }
    }
}