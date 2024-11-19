package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课时文件Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class LessonFileBo implements Serializable {

    /**
     * 文件类型
     *
     * @see Integer
     */
    private Integer fileType;

    /**
     * 商店 URL
     *
     * @see String
     */
    private String storeUrl;

    /**
     * 源 URL
     *
     * @see String
     */
    private String originUrl;

}
