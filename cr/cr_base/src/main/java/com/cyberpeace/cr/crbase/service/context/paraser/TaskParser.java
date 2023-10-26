package com.cyberpeace.cr.crbase.service.context.paraser;


import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;

/**
 * 任务解析
 */
@Service
public class TaskParser implements MdContentParser<List<RoleBo>, String> {

	@Override
	public List<RoleBo> parse(Object content) {
		return Lists.newArrayList();
	}

	@Override
	public String write(List<RoleBo> roleBos) {
		StringBuilder builder = new StringBuilder();
		int i = 1;
		for (RoleBo stage : roleBos) {
			builder.append("## 阶段").append(i).append(System.lineSeparator());
			i++;
			builder.append(stage.getTeamName()).append(System.lineSeparator());
			builder.append("### 任务").append(System.lineSeparator());
			for(String task : stage.getRoleName()){
				builder.append("- ").append(task).append(System.lineSeparator());
			}
		}
		return LocalStringUtil.frontBackLine(builder.toString());
	}
}
