package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Gender;
import com.itsherman.modulestest.entity.Student;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Set;

@DtoModel(from = Student.class)
public interface StudentDto extends Serializable {

    @ApiModelProperty
    Long getId();

    @ApiModelProperty
    Gender getGender();

    @ApiModelProperty
    String getName();

    @ApiModelProperty
    Set<TeacherDto> getTeachers();
}
