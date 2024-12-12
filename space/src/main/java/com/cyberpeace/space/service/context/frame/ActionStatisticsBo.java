package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionStatisticsBo implements Serializable {

	private Integer type;

	private String date;

}