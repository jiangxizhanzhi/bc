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
public abstract class SpaceFrameBaseTable {

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
	private String resourceId;

	private String name;

	public abstract SpaceFrameBoBase entity2Bo(Map<String, List<IdAndName>> relatedMap);

	public List<IdAndName> getIdAndNameList(Map<String, IdAndName> relatedMap, String idListJson){
		List<String> ids = JsonUtil.toJsonObjectArray(idListJson, String.class);
		return getIdAndNameList(relatedMap, ids);
	}

	public List<IdAndName> getIdAndNameList(Map<String, IdAndName> relatedMap, List<String> ids){
		List<IdAndName> res = Lists.newArrayList();
		for (String id : ids) {
			if(relatedMap.containsKey(id)){
				res.add(relatedMap.get(id));
			}
		}
		return res;
	}
	public IdAndName getIdAndName(Map<String, IdAndName> relatedMap, String id){
		return relatedMap.get(id);
	}
}
