package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * 包含时间信息的obj
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ObjWithTime implements Comparable<ObjWithTime> {

	/**
	 * 时间
	 */
	private Date time;

	/**
	 * 值
	 */
	private Integer value = 1;

	/**
	 * 起源
	 */
	private Map origin;

	@Override
	public int compareTo(ObjWithTime o) {
		return (int) (this.time.getTime() - o.time.getTime());
	}
}
