package com.cyberpeace.common.model;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DataTransferUtils;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Data
public class AbstractBaseTree {

	public List<? extends AbstractBaseTree> children;

	public String parentId;

	private String resourceId;

	/**
	 * 树形结构转为有序集合
	 * @return
	 */
	public List<? extends AbstractBaseTree> treeToList() {
		return treeToList(this);
	}

	/**
	 * 树形结构转换list 并转换数据类型
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public <T> List<T> treeToList(Class<T> cls) {
		List<? extends AbstractBaseTree> trees = treeToList(this);
		return BeanUtils.deepCopyList(trees, cls);
	}

	private List<? extends AbstractBaseTree> treeToList(AbstractBaseTree abstractBaseTree) {

		// 获取子数据
		List<? extends AbstractBaseTree> children = abstractBaseTree.getChildren();

		// 有子类则递归遍历 无子类直接返回
		if (CollectionUtils.isNotEmpty(children)) {

			// 新建节点存储数据集合 放入当前节点
			List<AbstractBaseTree> resultList = Lists.newArrayList();
			abstractBaseTree.setChildren(null);
			resultList.add(abstractBaseTree);

			for (AbstractBaseTree child : children) {
				List<? extends AbstractBaseTree> childrens = treeToList(child);

				for (AbstractBaseTree baseTree : childrens) {
					// 子类置空
					baseTree.setChildren(null);
					resultList.add(baseTree);
				}
			}
			return resultList;
		} else {
			return Lists.newArrayList(abstractBaseTree);
		}
	}

	/**
	 * 转换数据类型 通常用于字段减少操作
	 * @param abstractBaseTree
	 * @return
	 */
	public AbstractBaseTree convertClass(AbstractBaseTree abstractBaseTree,
			Class<? extends AbstractBaseTree> cls) {
		Object o = new BaseRsp<>();
		BeanUtils.copy(o, BaseTable.class);
		List<? extends AbstractBaseTree> abstractBaseTrees = abstractBaseTree.treeToList();
		List<? extends AbstractBaseTree> baseTrees = BeanUtils.deepCopyList(abstractBaseTrees, cls);
		return DataTransferUtils.listToTree(baseTrees);
	}
}
