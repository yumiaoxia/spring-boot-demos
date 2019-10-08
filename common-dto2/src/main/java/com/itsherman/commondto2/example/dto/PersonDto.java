package com.itsherman.commondto2.example.dto;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.example.dto.dto2.BookDto;
import com.itsherman.commondto2.example.model.Person;

import java.time.LocalDateTime;
import java.util.List;

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
    private long weight;

    @DtoProperty
    private LocalDateTime birthday;

    @DtoProperty
    private List<BookDto> books;

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

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

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", birthday=" + birthday +
                ", books=" + books +
                '}';
    }
}
