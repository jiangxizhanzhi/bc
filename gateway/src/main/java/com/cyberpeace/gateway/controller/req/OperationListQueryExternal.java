package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OperationListQueryExternal extends OperationListQuery {

	private String knowledge;

}
