package com.cyberpeace.cr.redvsblue.service.context.evaluation;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.cs.service.context.IdNameType;

import lombok.Data;

@Data
public class RbRoleIdNameType extends IdNameType {

    /**
     * 设置前缀
     * 红方-角色名
     */
    public void setPrefix() {
		String prefix = EnumUtils.getEnumDescByCode(this.getType(), RbRoleGroup.class);
		this.setName(prefix + "-" + this.getName());
	}
}
