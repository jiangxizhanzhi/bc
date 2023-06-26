package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 导入业务组件请求
 *
 * @Author xuxz
 * @Date 13/12/2022 下午5:32
 */
@Data
public class BusinessComponentImportReq implements Serializable {

    /**
     * 文件
     */
    private MultipartFile file;
}
