package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY)
    private DBVacancy dbVacancy;

    public static Candidate of(String name, String experience, int salary) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public DBVacancy getDbVacancy() {
        return dbVacancy;
    }

    public void setDbVacancy(DBVacancy dbVacancy) {
        this.dbVacancy = dbVacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && salary == candidate.salary
                && Objects.equals(name, candidate.name)
                && Objects.equals(experience, candidate.experience)
                && Objects.equals(dbVacancy, candidate.dbVacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, experience, salary, dbVacancy);
    }

    @Override
    public String toString() {
        return String.format("Student: id=%s, name=%s, experience=%s, salary=%s, dbvacancy=%s", id, name, experience, salary, dbVacancy);
    }
}