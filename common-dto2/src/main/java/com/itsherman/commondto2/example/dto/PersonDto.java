package com.itsherman.commondto2.example.dto;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.example.model.Person;

import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-26
 */
@DtoModel(from = Person.class)
public class PersonDto {

    @DtoProperty
    private String name;

    @DtoProperty
    private String nickName;

    @DtoProperty
    private Integer age;

    @DtoProperty
    private LocalDateTime birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
