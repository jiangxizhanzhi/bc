package com.cyberpeace.cr.hld.service.context;

import lombok.Data;

import java.util.List;

@Data
public class HldRoleContext {

	private String name;

	private String id;

	private List<HldUserContext> userList;

}
