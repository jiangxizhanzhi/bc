package com.cyberpeace.common.util;

import com.cyberpeace.common.model.AbstractBaseTree;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataTransferUtils {

	/**
	 * 判断整数对象是否为空 并进行赋值
	 * @param num
	 * @return
	 */
	public static Integer nullToZero(Integer num) {
		if (null == num) {
			num = 0;
		}
		return num;
	}

	/**
	 * 判断 浮点数对象是否为空 并进行赋值
	 * @param num
	 * @return
	 */
	public static Double nullToZero(Double num) {
		if (null == num) {
			num = new Double(0);
		}
		return num;
	}

	/**
	 * list转为树形结构
	 * @param treeNodes
	 * @return
	 */
	public static AbstractBaseTree listToTree(List<? extends AbstractBaseTree> treeNodes) {

		Map<String, ? extends List<? extends AbstractBaseTree>> columnMap = StreamApiUtils
				.groupByColumn(treeNodes, AbstractBaseTree::getParentId);
		AbstractBaseTree firstNode = getFirstNode(treeNodes);
		if (null == firstNode) {
			return null;
		}
		packageData(firstNode, columnMap);
		return firstNode;
	}

	/**
	 * 无根节点树形转换
	 * @param treeNodes
	 * @return
	 */
	public static List<? extends AbstractBaseTree>
			listToListTree(List<? extends AbstractBaseTree> treeNodes) {

		List<? extends AbstractBaseTree> firstTree = treeNodes.stream()
				.filter(abstractBaseTree -> StringUtils.isBlank(abstractBaseTree.getParentId()))
				.collect(Collectors.toList());

		List<? extends AbstractBaseTree> cildrens = firstTree.stream()
				.filter(abstractBaseTree -> StringUtils.isNotBlank(abstractBaseTree.getParentId()))
				.collect(Collectors.toList());

		Map<String, ? extends List<? extends AbstractBaseTree>> columnMap = StreamApiUtils
				.groupByColumn(cildrens, AbstractBaseTree::getParentId);

		for (AbstractBaseTree abstractBaseTree : firstTree) {
			packageData(abstractBaseTree, columnMap);
		}

		return firstTree;
	}

	/**
	 * 获取最顶级节点
	 * @param treeNodes
	 * @return
	 */
	private static AbstractBaseTree getFirstNode(List<? extends AbstractBaseTree> treeNodes) {

		List<? extends AbstractBaseTree> baseTrees = treeNodes.stream()
				.filter(abstractBaseTree -> StringUtils.isBlank(abstractBaseTree.getParentId()))
				.collect(Collectors.toList());
		if (baseTrees.size() == 1) {
			return baseTrees.get(0);
		}

		return null;
	}

	/**
	 * 
	 * @param baseTree
	 * @param columnMap
	 */
	private static void packageData(AbstractBaseTree baseTree,
			Map<String, ? extends List<? extends AbstractBaseTree>> columnMap) {

		List<? extends AbstractBaseTree> children = columnMap.get(baseTree.getResourceId());
		if (CollectionUtils.isNotEmpty(children)) {

			for (AbstractBaseTree child : children) {
				packageData(child, columnMap);
			}
			baseTree.setChildren(children);
		}
	}

}
