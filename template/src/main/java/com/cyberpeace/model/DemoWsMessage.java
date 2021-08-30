package com.cyberpeace.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoWsMessage implements Serializable {

	private Date timestamp;

	private Integer code;

	private String message;
}
