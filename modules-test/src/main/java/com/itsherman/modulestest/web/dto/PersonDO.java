package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Person;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@DtoModel(from = Person.class)
public class PersonDO {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private LocalDateTime birthday;

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

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }


    public Integer getAge() {
        if (getBirthday() != null) {
            return LocalDateTime.now().getYear() - getBirthday().getYear();
        }
        return -1;
    }
}
