package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeUnderCourseBo implements Serializable {

    private String relatedId;

    private String resourceId;

    private String type;

    private Integer weight;

}
