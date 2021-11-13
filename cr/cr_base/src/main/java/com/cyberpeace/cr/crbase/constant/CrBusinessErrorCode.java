package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 *
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 */
public interface CrBusinessErrorCode {

	// 找不到有效目录
	String NOT_PATH = "101001";

	// 没有找到md文件
	String NOT_MD_PATH = "101002";

	// 文件解压失败
	String ZIP_PATH_NOT_EXTRA = "101003";

	// 实例不存在
	String CASE_DOES_NOT_EXIST = "101004";

	// 权限不存在
	String PERMISSION_NOT_EXIST = "101005";

	// 训练未开始
	String TRAIN_NOT_BEGIN = "101006";

	// 训练已结束
	String TRAIN_IS_OVER = "101007";

	// 训练角色不存在
	String TRAINING_TEAM_ROLE_NOT_EXIST_ERROR = "101008";

	// 评分范围出错
	String SCORE_RANGE_ERROR = "101009";
}
