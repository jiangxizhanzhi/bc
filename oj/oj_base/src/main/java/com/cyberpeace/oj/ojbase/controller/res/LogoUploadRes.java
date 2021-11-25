package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
public class LogoUploadRes implements Serializable {

    private String url;

}
