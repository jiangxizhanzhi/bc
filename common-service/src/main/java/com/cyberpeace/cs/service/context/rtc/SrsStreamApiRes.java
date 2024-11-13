package com.cyberpeace.cs.service.context.rtc;

import java.util.List;

import lombok.Data;

/**
 * SRS流Api结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SrsStreamApiRes extends SrsApiRes {

	/**
	 * 流
	 */
	private List<SrsStream> streams;
}
