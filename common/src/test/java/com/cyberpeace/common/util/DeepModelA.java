package com.cyberpeace.common.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DeepModelA implements Serializable {

    private String name;

    private Integer num;

    private Date time;

    private String a;

    private List<ModelA> list;

    private Map<String, ModelA> map;
}
