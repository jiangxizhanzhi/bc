package com.cyberpeace.cr.crbase.service.context.paraser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;
import com.google.common.collect.Lists;

/**
 * 角色解析
 */
@Service
public class RoleParser implements MdContentParser<List<RoleBo>, String> {


	@Override
	public List<RoleBo> parse(Object content) {
		return Lists.newArrayList();
	}

	@Override
	public String write(List<RoleBo> roleBos) {
		StringBuilder builder = new StringBuilder();
		for(RoleBo roleBo:roleBos){
			builder.append(PatternConstant.MD_LIST).append(roleBo.getTeamName())
					.append(System.lineSeparator());
			for (String roleName:roleBo.getRoleName()){
				builder.append("  ").append(PatternConstant.MD_LIST).append(roleName)
						.append(System.lineSeparator());
				if(roleBo.getTeamName().equals("学员组")){
					builder.append("    ").append(PatternConstant.MD_LIST).append("负责学习")
							.append(System.lineSeparator());
				}else {
					builder.append("    ").append(PatternConstant.MD_LIST)
							.append(roleBo.getTeamName()).append(System.lineSeparator());
				}
			}
		}
		return LocalStringUtil.frontBackLine(builder.toString());
	}

}
