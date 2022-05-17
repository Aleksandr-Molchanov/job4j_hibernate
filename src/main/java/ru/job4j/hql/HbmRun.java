package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", "1 year", 10000);
            Candidate two = Candidate.of("Nikolay", "3 years", 30000);
            Candidate three = Candidate.of("Nikita", "10 years", 100000);
            session.save(one);
            session.save(two);
            session.save(three);

            Vacancy vacancy1 = Vacancy.of("Junior", "Description1");
            Vacancy vacancy2 = Vacancy.of("Middle", "Description2");
            Vacancy vacancy3 = Vacancy.of("Senior", "Description3");
            session.save(vacancy1);
            session.save(vacancy2);
            session.save(vacancy3);

            DBVacancy db = DBVacancy.of("base1");
            db.setVacancies(List.of(vacancy1, vacancy2, vacancy3));
            session.save(db);

            one.setDbVacancy(db);

            /*
            Query queryAll = session.createQuery("from Candidate");
            for (Object c : queryAll.list()) {
                System.out.println(c);
            }

            Query queryId = session.createQuery("from Candidate c where c.id = 1");
            System.out.println(queryId.uniqueResult());

            Query query = session.createQuery("from Candidate c where c.name = :fName");
            query.setParameter("fName", "Nicolay");
            System.out.println(query.uniqueResult());

            session.createQuery("update Candidate c set c.experience = :newExperience, c.salary = :newSalary where c.id = :fId")
                    .setParameter("newExperience", "15 years")
                    .setParameter("newSalary", 150000)
                    .setParameter("fId", 1)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.createQuery("insert into Candidate (name, experience, salary) "
                            + "select concat(s.name, 'NEW'), s.experience, s.salary + 15000  "
                            + "from Candidate s where s.id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();
             */

            Candidate rsl = session.createQuery(
                    "select distinct c from Candidate c "
                            + "join fetch c.dbVacancy db "
                            + "join fetch db.vacancies v "
                            + "where c.id = :cId", Candidate.class
            ).setParameter("cId", 5).uniqueResult();
            System.out.println(rsl);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}