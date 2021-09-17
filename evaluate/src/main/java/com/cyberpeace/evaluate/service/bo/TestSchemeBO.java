package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestSchemeBO implements Serializable {

    private String testSchemeId;

    private String name;

    private String desc;

    private String logo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String content;

}
