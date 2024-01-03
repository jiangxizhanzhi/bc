package com.cyberpeace.scene.service.bo.req;

import com.cyberpeace.scene.service.bo.Net;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/29 9:23
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstanceReq implements Serializable {

    /**
     * 虚拟机名称
     */
    private String name;

    /**
     * 镜像id
     */
    private String image;

    /**
     * 网络信息
     */
    private List<Net> nets;
}
