package com.cyberpeace.cr.evaluation.controller.bo;

import com.cyberpeace.cr.evaluation.dao.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class RecordDumpLoadBo extends EvalRecord {

	private List<EvalIndexRef> indexRefs;

	private List<EvalVariableRef> variableRefs;

	private String rbId;

	private String systemName;
}
