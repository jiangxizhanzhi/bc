package com.cyberpeace.oj.practice.dao.entity;

import com.cyberpeace.oj.practice.dao.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PracticeCategory  implements Serializable {

    /**
     * 资源ID
     */
    private String resourceId = DigestUtils.md5Hex(UUID.randomUUID().toString());

    /**
     * 名称
     */
    private String name;

    @JsonIgnore
    private Integer id;

    /**
     * 状态
     */
    @JsonIgnore
    private Integer status = Status.NORMAL.ordinal();

    /**
     * 练习类型
     */
    @JsonIgnore
    private Integer practiceType;

}
