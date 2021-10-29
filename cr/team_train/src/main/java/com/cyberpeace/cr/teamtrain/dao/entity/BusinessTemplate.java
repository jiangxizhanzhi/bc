package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoInput;

import lombok.Data;

/**
 * 
 * @TableName cr_business_template
 */
@TableName(value = "cr_business_template")
@Data
public class BusinessTemplate implements Serializable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String topoId;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Integer businessType;

	/**
	 * 
	 */
	private Integer difficulty;

	/**
	 * 
	 */
	private String createUser;

	/**
	 * 
	 */
	private String operationAuthType;

	/**
	 * 
	 */
	private String operationAuthIds;

	/**
	 * 
	 */
	private String readAuthType;

	/**
	 * 
	 */
	private String readAuthIds;

	public BusinessTemplate() {

	}

	public BusinessTemplate(BaseInfoInput crEventAddInput) {
		this.name = crEventAddInput.getName();
		this.description = crEventAddInput.getDescription();
		this.difficulty = crEventAddInput.getDifficult();
		this.businessType = CrEvent.CeType.TRAINING_TEAM;
	}

}