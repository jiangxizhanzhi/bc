package com.cyberpeace.oj.ojbase.dao.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteUniqueContext implements Serializable {

	private Integer noteType;

	private Integer businessType;

	private String userId;

	private String businessId;

}
