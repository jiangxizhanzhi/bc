package com.cyberpeace.cr.crbase.service.context.paraser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;

/**
 * 角色解析器
 * 角色解析
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class RoleParser implements MdContentParser<List<RoleBo>, String> {

	@Override
	public List<RoleBo> parse(Object content) {
		return null;
	}

	@Override
	public String write(List<RoleBo> roleBos) {
		String str = "";
		for (RoleBo roleBo : roleBos) {
			str = str + PatternConstant.MD_LIST + roleBo.getTeamName() + System.lineSeparator();
			for (String roleName : roleBo.getRoleName()) {
				str = str + "  " + PatternConstant.MD_LIST + roleName + System.lineSeparator();
				if (roleBo.getTeamName().equals("学员组")) {
					str = str + "    " + PatternConstant.MD_LIST + "负责学习" + System.lineSeparator();
				} else {
					str = str + "    " + PatternConstant.MD_LIST + roleBo.getTeamName()
							+ System.lineSeparator();
				}
			}
		}
		return LocalStringUtil.frontBackLine(str);
	}

}
