package com.cyberpeace.oj.ojbase.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteListQuery extends PatternBase {

	private List<String> userIds;

	private Integer noteType;

	private Integer businessType;

	private String businessId;

}
