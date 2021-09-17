package com.cyberpeace.evaluate.dao.entity;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.FormalVariableItem;
import lombok.Data;

@Data
public class EstimateInstanceFormalVariables implements Serializable {

	String name;

	List<FormalVariableItem> variableList;
}
