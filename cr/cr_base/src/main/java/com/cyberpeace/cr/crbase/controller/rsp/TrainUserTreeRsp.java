package com.cyberpeace.cr.crbase.controller.rsp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainUserTreeRsp implements Serializable {
    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("类型 1-组织;2-用户")
    private Integer type;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("下一级组织数据")
    private List<TrainUserTreeRsp> children;

    public enum Type {
        ORGANIZATION(1, "组织"), USER(2, "用户");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
