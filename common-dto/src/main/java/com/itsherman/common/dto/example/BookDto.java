package com.itsherman.common.dto.example;

import com.itsherman.common.dto.annotations.DtoMapping;
import com.itsherman.common.dto.annotations.DtoProperty;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
@DtoMapping(from = Book.class)
public class BookDto {

    @DtoProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
