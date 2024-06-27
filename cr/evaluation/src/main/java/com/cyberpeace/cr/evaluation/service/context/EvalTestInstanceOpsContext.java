package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cs.dao.entity.LocalScene;
import lombok.Data;

@Data
public class EvalTestInstanceOpsContext {

	private LocalScene localScene;

	private EvalTestInstance evalTestInstance;

}
