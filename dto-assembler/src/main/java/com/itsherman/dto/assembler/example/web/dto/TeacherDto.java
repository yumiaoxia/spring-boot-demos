package com.itsherman.dto.assembler.example.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.entity.Teacher;

import java.io.Serializable;

@DtoModel(from = Teacher.class)
public interface TeacherDto extends Serializable {
    String getName();
}
