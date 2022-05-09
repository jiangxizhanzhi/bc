package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

@Data
public class EngageContext implements Serializable {

	private String categoryId;

	private String categoryNameZh;

	private String categoryNameEn;

	private String subCategoryId;

	private String subCategoryNameZh;

	private String subCategoryNameEn;

	private String engageId;

	private String engageNameZh;

	private String engageNameEn;
}
