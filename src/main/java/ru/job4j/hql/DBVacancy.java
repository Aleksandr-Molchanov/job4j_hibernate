package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dbvacancy")
public class DBVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    public static DBVacancy of(String name) {
        DBVacancy db = new DBVacancy();
        db.name = name;
        return db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DBVacancy dbVacancy = (DBVacancy) o;
        return id == dbVacancy.id
                && Objects.equals(name, dbVacancy.name)
                && Objects.equals(vacancies, dbVacancy.vacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vacancies);
    }

    @Override
    public String toString() {
        return "DBVacancy{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", vacancies=" + vacancies
                + '}';
    }
}
