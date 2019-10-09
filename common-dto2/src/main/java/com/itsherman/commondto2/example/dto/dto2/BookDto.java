package com.itsherman.commondto2.example.dto.dto2;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.example.model.Book;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
@DtoModel(from = Book.class)
public class BookDto {

    @DtoProperty
    private String name;

    @DtoProperty
    private Integer version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
