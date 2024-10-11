package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景模板和计数的关联
 * attack， engage
 *
 * @author SN
 */
@Data
public class TechRelation implements Serializable {

	private String businessTemplateId;

	private String techId;
}
