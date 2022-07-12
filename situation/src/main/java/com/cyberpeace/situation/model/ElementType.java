package com.cyberpeace.situation.model;

import lombok.Data;

@Data
public class ElementType {

    private String resourceId;

    private String name;

    public ElementType(String resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }
}
