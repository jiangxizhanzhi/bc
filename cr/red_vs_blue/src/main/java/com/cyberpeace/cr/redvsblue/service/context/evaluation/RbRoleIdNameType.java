package com.cyberpeace.cr.redvsblue.service.context.evaluation;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cs.service.context.IdNameType;

import lombok.Data;

/**
 * RB角色id名称类型
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbRoleIdNameType extends IdNameType {

    /**
     * 设置前缀
     * 红方-角色名
     */
    public void setPrefix() {
		String prefix = EnumUtils.getEnumDescByCode(this.getType(), CrRole.RbRoleGroup.class);
		this.setName(prefix + "-" + this.getName());
	}
}
