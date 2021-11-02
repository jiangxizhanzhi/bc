package com.cyberpeace.cr.crbase.model;

import java.util.List;

import lombok.Data;

@Data
public class Share {

	private Integer shareMode;

	private List<ShareTeacher> teachers;
}
