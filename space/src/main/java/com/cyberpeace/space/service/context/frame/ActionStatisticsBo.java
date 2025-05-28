package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 行动统计学Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ActionStatisticsBo implements Serializable {

	private Integer type;

	private String date;

}