package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import lombok.Data;

@Data
public class EvalTemplateListQuery extends PageReqBase implements Serializable {

	private Integer categoryCode;

	private String userId;

}
