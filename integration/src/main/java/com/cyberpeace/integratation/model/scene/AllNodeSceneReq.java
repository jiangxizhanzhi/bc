package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AllNodeSceneReq extends AllNodeReq implements Serializable {

	private Boolean realTimeHostState;

	@Override
	protected List<String> getQuery() {
		List<String> queries = super.getQuery();
		if (Boolean.TRUE.equals(this.realTimeHostState)) {
			queries.add("realTimeHostState=true");
		} else {
			queries.add("realTimeHostState=false");
		}
		return queries;
	}
}
