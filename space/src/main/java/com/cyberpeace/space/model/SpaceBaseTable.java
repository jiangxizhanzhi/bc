package com.cyberpeace.space.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
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
	@ApiModelProperty(value = "内置")
	private Boolean builtin;
	public abstract SpaceBoBase entity2Bo(Map<String, IdAndName> relatedMap);

	public List<IdAndName> getListByJson(Map<String, IdAndName> relatedMap, String jsonStr) {
		List<IdAndName> res = Lists.newArrayList();
		if (jsonStr != null && !jsonStr.isEmpty()) {
			List<String> ids = JsonUtil.toJsonObjectArray(jsonStr, String.class);
			for (String id : ids) {
				if (relatedMap.containsKey(id)) {
					res.add(relatedMap.get(id));
				}
			}
		}
		return res;
	}
}
