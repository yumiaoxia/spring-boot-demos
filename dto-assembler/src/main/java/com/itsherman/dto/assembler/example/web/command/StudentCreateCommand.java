package com.itsherman.dto.assembler.example.web.command;

import com.itsherman.dto.assembler.example.domain.Gender;

import java.io.Serializable;
import java.util.Set;

public class StudentCreateCommand implements Serializable {

    private String name;

    private Gender gender;

    private Set<TeacherCreateCommand> teachers;

    public Set<TeacherCreateCommand> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherCreateCommand> teachers) {
        this.teachers = teachers;
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
