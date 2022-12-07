package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 5/12/2022 下午5:24
 */

@Data
public class DataSourceReq implements Serializable {

    @ApiModelProperty("查询sql")
    private String sql;

    @ApiModelProperty(value = "数据源id", required = true)
    private String resourceId;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("数据库名称")
    private String dbName;

    @ApiModelProperty("自定义参数列表")
    private String customParams;

    public DataSourceReq(String sql, String resourceId, String dbName, String tableName, String customParams) {
        this.sql = sql;
        this.dbName = dbName;
        this.resourceId = resourceId;
        this.tableName = tableName;
        this.customParams = customParams;
    }
}
