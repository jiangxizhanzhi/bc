package com.cyberpeace.situation.controller.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:04
 */
@Data
public class GisPageRes extends PageReqBase implements Serializable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private Integer type;

    private String img;

    private String url;
}
