package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

@Data
public class RtksRoleResource implements Serializable {

	private String name;

	private String description;

	private String level;

	private List<String> taskId = Lists.newArrayList();

	private List<IdAndWeight> knowledgeIdsWeights = Lists.newArrayList();

	private Integer knowledgeWeight;

	private List<IdAndWeight> skillIdsWeights = Lists.newArrayList();
}
