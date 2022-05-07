package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

@Data
public class AttackContext implements Serializable {

	private String categoryId;

	private String categoryNameZh;

	private String categoryNameEn;

	private String attackId;

	private String attackNameZh;

	private String attackNameEn;
}
