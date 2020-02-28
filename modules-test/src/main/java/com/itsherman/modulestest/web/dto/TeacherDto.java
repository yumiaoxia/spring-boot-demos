package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.entity.Teacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@DtoModel(from = Teacher.class)
@ApiModel
public interface TeacherDto extends Serializable {

    @ApiModelProperty
    String getName();
}
