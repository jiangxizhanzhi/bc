package com.cyberpeace.integratation.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CompetitionDeleteBo implements Serializable {

	/** 赛事编号*/
	private List<String>  resource_ids;

}
