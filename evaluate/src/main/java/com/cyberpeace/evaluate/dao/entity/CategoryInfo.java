package com.cyberpeace.evaluate.dao.entity;

import lombok.Data;

@Data
public class CategoryInfo {

	private Integer id;

	/**分类编号 */
	private String categoryId;

	/**分类类型 */
	private Integer type;

	/**分类描述 */
	private String desc;

	/**子分类型 */
	private String subType;

	/**子分类描述 */
	private String subDesc;
}
