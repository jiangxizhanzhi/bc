package com.cyberpeace.oj.person.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonCollection  implements Serializable {

    private String resourceId;

    private String name;

    private String businessType;

}
