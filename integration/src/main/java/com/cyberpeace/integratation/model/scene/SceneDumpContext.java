package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneDumpContext implements Serializable {

    private String id;

    private String env_path;

    private String image_path;

    private String single_image_path;

    private String scene_number;

    private Boolean disable_image_export;
}
