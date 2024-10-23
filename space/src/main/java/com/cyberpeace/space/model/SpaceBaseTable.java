package com.cyberpeace.space.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public abstract class SpaceBaseTable {

    @ApiModelProperty("自增ID")
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime;

	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "资源id")
	private String resourceId;

    @ApiModelProperty(value = "名称")
	private String name;

	public abstract SpaceBoBase entity2Bo();
}
