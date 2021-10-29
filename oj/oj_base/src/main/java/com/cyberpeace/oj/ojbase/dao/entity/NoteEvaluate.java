package com.cyberpeace.oj.ojbase.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
public class NoteEvaluate extends BaseTable {

	private String resourceId;

	private Double score;

	private Boolean passed;

	private String evaluateComment;

	private String evaluatorId;

	private String noteId;

	private Date evaluateTime;

}
