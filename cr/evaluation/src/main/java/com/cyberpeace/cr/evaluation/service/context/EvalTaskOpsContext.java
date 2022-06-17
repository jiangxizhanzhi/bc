package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.Data;

@Data
public class EvalTaskOpsContext {

	private LocalScene localScene;

	private EvalTask evalTask;

}
