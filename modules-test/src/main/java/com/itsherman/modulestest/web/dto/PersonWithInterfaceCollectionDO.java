package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Person;

import java.util.Set;

@DtoModel(from = Person.class)
public class PersonWithInterfaceCollectionDO extends PersonDO {

    private Set<HouseDto> houses;

    private Set<String> tabs;

    public Set<HouseDto> getHouses() {
        return houses;
    }

    public void setHouses(Set<HouseDto> houses) {
        this.houses = houses;
    }

    public Set<String> getTabs() {
        return tabs;
    }

    public void setTabs(Set<String> tabs) {
        this.tabs = tabs;
    }
}
