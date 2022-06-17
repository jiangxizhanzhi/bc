package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

@Data
public class CompletionStatus implements Serializable {

	private String resourceId;

	private Integer status;
}
