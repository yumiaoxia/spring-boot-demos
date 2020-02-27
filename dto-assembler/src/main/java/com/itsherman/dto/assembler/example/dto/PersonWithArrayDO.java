package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

@DtoModel(from = Person.class)
public class PersonWithArrayDO extends PersonDO {

    private CarDO[] cars;

    public CarDO[] getCars() {
        return cars;
    }

    public void setCars(CarDO[] cars) {
        this.cars = cars;
    }
}
