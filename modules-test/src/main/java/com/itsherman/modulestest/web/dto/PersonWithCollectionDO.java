package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Person;

import java.util.Set;

@DtoModel(from = Person.class)
public class PersonWithCollectionDO extends PersonDO {

    private Set<HouseDO> houses;

    public Set<HouseDO> getHouses() {
        return houses;
    }

    public void setHouses(Set<HouseDO> houses) {
        this.houses = houses;
    }
}
