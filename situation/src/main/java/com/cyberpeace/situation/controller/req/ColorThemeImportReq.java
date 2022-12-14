package com.cyberpeace.situation.controller.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 13/12/2022 下午5:32
 */
@Data
public class ColorThemeImportReq implements Serializable {

    private String name;

    private MultipartFile file;
}
