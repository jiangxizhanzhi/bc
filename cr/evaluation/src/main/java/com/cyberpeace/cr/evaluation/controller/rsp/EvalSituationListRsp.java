package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalSituationListRsp implements Serializable {

	private String name;

	private String hash;
}
