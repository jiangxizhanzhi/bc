package com.cyberpeace.situation.model.outer;

import lombok.Data;

import java.io.Serializable;

@Data
public class VisualzationModel implements Serializable {

    private String uuid;

    private String name;

    private String thumbnail;
}
