package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LessonFileBo implements Serializable {

    private Integer fileType;

    private String storeUrl;

    private String originUrl;

}
