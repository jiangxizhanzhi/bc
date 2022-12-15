package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:20
 */
@Data
public class SituationIconDelReq implements Serializable {
    private List<String> idList;
}
