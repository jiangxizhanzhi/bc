package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

import java.util.List;

@Data
public class DefenseListBo {
    private String resourceId;

    private String name;

    private String tacticId;

    private String categoryId;

    private String category;

    private String description;

    private String scene;

    private List<String> tool;
}
