package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * 包含时间信息的obj
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Comparable
 * @since 1.0.0
 */
@Data
public class ObjWithTime implements Comparable<ObjWithTime> {

	/**
	 * 时间
	 *
	 * @see Date
	 */
	private Date time;

	/**
	 * 值
	 *
	 * @see Integer
	 */
	private Integer value = 1;

	/**
	 * 起源
	 *
	 * @see Map
	 */
	private Map origin;

	@Override
	public int compareTo(ObjWithTime o) {
		return (int) (this.time.getTime() - o.time.getTime());
	}
}
