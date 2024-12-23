package com.cyberpeace.iov.index.service.bo.can;

import lombok.Data;

import java.util.List;

/**
 * 车联网态势闪光动效
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/23
 * @since 1.0.0
 */
@Data
public class IovSituationFlashEffect {

	private List<Integer> ecu;

	private String color = "#FF0000";

	private Integer duration = 3;
}
