package com.cyberpeace.assess.constant;

/**
 * 统一错误吗
 */
public interface AssessErrorCode {

	/**
	 * 名称缺失
	 */
	String NAME_MISSING = "105001";

	/**
	 * 封面缺失
	 */
	String COVER_MISSING = "105002";

	/**
	 * 标签缺失
	 */
	String TAG_MISSING = "105003";

	/**
	 * ids为空
	 */
	String RESOURCE_IDS_MISSING = "105004";

	/**
	 * id为空
	 */
	String RESOURCE_ID_MISSING = "105005";

	/**
	 * 材料类型缺失
	 */
	String MATERIAL_TYPE_MISSING = "105006";

	/**
	 * 材料附件缺失
	 */
	String MATERIAL_ATTACHMENT_MISSING = "105007";

	/**
	 * 材料附件文件路径错误
	 */
	String MATERIAL_ATTACHMENT_FILE_PATH_ERROR = "105008";

	/**
	 * 评估案例id缺失
	 */
	String CASE_ID_EMPTY = "105009";

	/**
	 * 材料类型错误
	 */
	String MATERIAL_TYPE_ERROR = "105010";

	/**
	 * 评估案例id错误 找不到对应资源
	 */
	String CASE_ID_ERROR = "105011";

	/**
	 * tagid错误 找不到对应资源
	 */
	String TAG_ID_ERROR = "105012";

	/**
	 * 材料id数组错误
	 */
	String MATERIAL_IDS_ERROR = "105013";

	/**
	 * 材料id数组错误
	 */
	String ATTACHMENT_NAME_MISSING = "105014";
}
