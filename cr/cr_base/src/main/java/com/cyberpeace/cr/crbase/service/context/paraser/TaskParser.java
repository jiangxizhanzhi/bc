package com.cyberpeace.cr.crbase.service.context.paraser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cs.service.context.RoleBo;

/**
 * 任务解析器
 * 任务解析
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class TaskParser implements MdContentParser<List<RoleBo>, String> {

	@Override
	public List<RoleBo> parse(Object content) {
		return null;
	}

	@Override
	public String write(List<RoleBo> roleBos) {
		String str = "";
		int i = 1;
		for (RoleBo stage : roleBos) {
			str = str + "## 阶段" + i++ + System.lineSeparator();
			str = str + stage.getTeamName() + System.lineSeparator();
			str = str + "### 任务" + System.lineSeparator();
			for (String task : stage.getRoleName()) {
				str = str + "- " + task + System.lineSeparator();
			}
		}
		return LocalStringUtil.frontBackLine(str);
	}
}
