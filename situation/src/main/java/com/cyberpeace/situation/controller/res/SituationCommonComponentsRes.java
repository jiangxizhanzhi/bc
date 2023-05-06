package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.SituationCommonComponents;

import lombok.Data;

/**
 * 态势通用组件返回
 *
 * @Author xuxz
 * @Date 29/12/2022 上午11:19
 */
@Data
public class SituationCommonComponentsRes extends SituationCommonComponents implements Serializable {

	/**
	 * 类型名称
	 */
	private String typeName;
}
