package com.itsherman.dto.assembler.example.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.entity.Student;

import java.io.Serializable;
import java.util.Set;

@DtoModel(from = Student.class)
public interface StudentDto extends Serializable {

    String getName();

    Set<TeacherDto> getTeachers();
}
