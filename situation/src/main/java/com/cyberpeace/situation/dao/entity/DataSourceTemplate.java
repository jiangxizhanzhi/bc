package com.cyberpeace.situation.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 3/11/2022 上午9:58
 */
@Data
public class DataSourceTemplate implements Serializable {

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 连接url
     */
    private String connectUrl;

}
