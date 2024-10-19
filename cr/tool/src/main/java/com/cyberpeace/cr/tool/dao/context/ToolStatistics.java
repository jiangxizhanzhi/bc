package com.cyberpeace.cr.tool.dao.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class ToolStatistics implements Serializable {

	private String type;

	private String date;
}
