package com.cyberpeace.bs.service.bo.colletion;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.service.bo.behavior.Behavior;

import lombok.Data;

@Data
public class BehaviorCollectionDetail extends BehaviorCollectionBase implements Serializable {

	/**
	 * 行为列表
	 */
	private List<Behavior> behaviorList = Lists.newArrayList();
}
