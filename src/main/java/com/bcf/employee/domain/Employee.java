package com.bcf.employee.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;



@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name="supervisor_id")
    @JsonIgnoreProperties("subordinates")
    private Employee supervisor;
    @OneToMany(mappedBy="supervisor", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnoreProperties("supervisor")
    private Set<Employee> subordinates = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public void addSubordinate(Employee subordinate) {
        this.subordinates.add(subordinate);
        subordinate.setSupervisor(this);
    }

    public void removeSubordinate(Employee subordinate) {
        this.subordinates.remove(subordinate);
        subordinate.setSupervisor(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
