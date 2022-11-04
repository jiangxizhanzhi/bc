package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 3/11/2022 下午5:26
 */
@Data
public class DataSourceRecordDelReq implements Serializable {

    private  List<String> dataResourceIdList;
}
