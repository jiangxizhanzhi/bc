package com.cyberpeace.scene.service.bo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cyberpeace.scene.service.bo.Net;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/21 19:56
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContainerReq implements Serializable {

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器镜像id
     */
    private String image;

    /**
     * 容器网络配置
     */
    private List<Net> nets;
}
