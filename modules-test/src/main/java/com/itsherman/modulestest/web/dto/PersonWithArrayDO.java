package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Person;

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
