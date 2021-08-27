package com.cyberpeace.cr.tool.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class GhdbQueryContext implements Serializable {

	private String title;

	private String author;
}
