package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;

import lombok.Data;

@Data
public class IdAndWeight implements Serializable {

	private String id;

	private Integer weight;
}
