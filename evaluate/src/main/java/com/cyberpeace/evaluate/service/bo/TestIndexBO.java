package com.cyberpeace.evaluate.service.bo;

import com.cyberpeace.evaluate.dao.entity.TestIndex;

import lombok.Data;

@Data
public class TestIndexBO extends TestIndex {

	/**指标项结果 */
	private String value;
}
