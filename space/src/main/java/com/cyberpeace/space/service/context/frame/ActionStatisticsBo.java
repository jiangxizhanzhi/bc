package com.cyberpeace.space.service.context.frame;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActionStatisticsBo implements Serializable {

	private Integer type;

	private String date;

}