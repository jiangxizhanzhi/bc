package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午10:21
 */
@Data
public class ColorThemeDelReq implements Serializable {

    private List<String> idList;
}
