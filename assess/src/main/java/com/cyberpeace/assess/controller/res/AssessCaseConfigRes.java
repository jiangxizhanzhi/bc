package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("材料编排信息")
public class AssessCaseConfigRes implements Serializable {

	@ApiModelProperty("案例id")
	private String caseId;

	@ApiModelProperty("材料id")
	private String materialId;

	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("索引")
	private Integer index;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("父节点编号")
	private String parentId;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("子节点")
	private List<AssessCaseConfigRes> children;

	/**
	 * 获取排序好子类课时信息
	 * @param includeChapter true 包含章节 false
	 * @param self true 是否包含自身 true包含
	 * @return
	 */
	public List<AssessCaseConfigRes> sortListByChildren(boolean includeChapter, boolean self) {

		List<AssessCaseConfigRes> bos = sortListByChildren(this, includeChapter);
		if (self) {
			bos.add(0, this);
		}
		return bos;

	}

	/**
	 * 获取排序数据
	 * @param assessCaseConfigRes
	 * @param includeChapter
	 * @return
	 */
	private List<AssessCaseConfigRes> sortListByChildren(AssessCaseConfigRes assessCaseConfigRes,
			boolean includeChapter) {

		if (CollectionUtils.isEmpty(assessCaseConfigRes.getChildren())) {
			return Arrays.asList(assessCaseConfigRes);
		}

		// 递归获取数据
		List<AssessCaseConfigRes> bos = assessCaseConfigRes.getChildren().stream().map(bo -> {
			bo.setParentId(assessCaseConfigRes.getResourceId());
			return sortListByChildren(bo, includeChapter);
		}).flatMap(Collection::stream).collect(Collectors.toList());

		// 将自身数据加入
		bos.add(0, assessCaseConfigRes);

		// 转换数据
//		List<AssessCaseConfigRes> configBos = bos.stream()
//				.filter(bo -> includeChapter ? includeChapter
//						: bo.getNodeType().intValue() == AssessCaseConfig.NodeType.NODE_LESSON.getCode()
//								.intValue())
//				.collect(Collectors.toList());

		return bos;

	}

}
