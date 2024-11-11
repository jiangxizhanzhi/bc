package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程档案 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LessonFileBo implements Serializable {

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 商店 URL
     */
    private String storeUrl;

    /**
     * 源 URL
     */
    private String originUrl;

}
