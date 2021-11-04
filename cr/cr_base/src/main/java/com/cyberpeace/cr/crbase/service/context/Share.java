package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class Share {

	private Integer shareMode;

	private List<ShareTeacher> teachers;
}
