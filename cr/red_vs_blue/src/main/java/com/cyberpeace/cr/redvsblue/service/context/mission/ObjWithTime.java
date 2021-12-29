package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class ObjWithTime implements Comparable<ObjWithTime> {

	private Date time;

	private Integer value = 1;

	private Map origin;

	@Override
	public int compareTo(ObjWithTime o) {
		return (int) (this.time.getTime() - o.time.getTime());
	}
}
