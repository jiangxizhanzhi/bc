package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelCount implements Serializable {

	private String resourceId;

	private Integer count;
}
