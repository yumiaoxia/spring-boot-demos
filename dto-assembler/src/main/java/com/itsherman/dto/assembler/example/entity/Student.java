package com.itsherman.dto.assembler.example.entity;

import com.itsherman.dto.assembler.example.domain.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Gender gender;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Teacher> teachers;

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
