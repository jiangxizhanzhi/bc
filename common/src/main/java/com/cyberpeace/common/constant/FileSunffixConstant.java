package com.cyberpeace.common.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 文件 sunffix 常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个枚举类 `FileSunffixConstant`，用于表示文件后缀类型的常量集合。以下是代码的主要分析和总结： 
 * ### **主要功能** 
 * 该枚举类实现了接口 `GenericEnum`，并定义了一系列文件后缀类型的常量，包括对应的代码（`code`）和描述（`desc`）。这些常量可以用于表示不同类型的文件后缀。 
 * --- 
 * ### **关键点分析** 
 * 1. **实现接口**： 
 * - 枚举类实现了 `GenericEnum` 接口，并使用了 Lombok 的 `@Getter` 注解，自动生成了 `code` 和 `desc` 属性的 getter 方法。 
 * 2. **字段定义**： 
 * - `code`：表示文件类型的唯一标识符，类型为 `Integer`。 
 * - `desc`：表示文件类型的描述信息，类型为 `String`。 
 * 3. **构造方法**： 
 * - 枚举类提供了私有构造方法，用于初始化每个枚举实例的 `code` 和 `desc`。 
 * 4. **枚举实例**： 
 * - 定义了多个文件后缀类型的枚举值，例如 `MD`、`PDF`、`ZIP` 等，每个枚举值都包含唯一的 `code` 和对应的文件后缀描述 `desc`。 
 * 5. **注释**： 
 * - 每个枚举值都有详细的注释说明其用途，例如 `MD` 表示 Markdown 文件，`PDF` 表示 PDF 文件等。 
 * - 类级别的注释清晰地描述了该枚举类的作用以及继承的接口。 
 * 6. **特殊处理**： 
 * - `M4V` 和 `MP4` 共享相同的 `code` 值 `13`，这可能是为了兼容性或特定业务需求设计的。 
 * --- 
 * ### **优点** 
 * - **可读性强**：通过注释清楚地描述了每个枚举值的用途。 
 * - **扩展性好**：如果需要新增文件类型，只需添加新的枚举值即可。 
 * - **代码简洁**：借助 Lombok 的 `@Getter` 注解减少了冗余代码。 
 * --- 
 * ### **潜在问题** 
 * - **重复的 `code` 值**：`MP4` 和 `M4V` 的 `code` 值相同，可能会导致业务逻辑上的混淆或冲突，需谨慎使用。 
 * - **未定义的文件类型**：如果需要支持更多文件类型，可能需要进一步扩展。 
 * --- 
 * ### **适用场景** 
 * 该枚举类适用于需要统一管理文件后缀类型的场景，例如文件上传、下载、分类等功能中，可以作为文件类型的一种标准化表示。 
 * --- 
 * ### **总结** 
 * 这是一个典型的枚举类设计，结构清晰、功能明确，适合用作文件后缀类型的常量集合。如果需要扩展或优化，可以根据具体业务需求调整枚举值或增加更多的功能逻辑。 
**/
public enum FileSunffixConstant implements GenericEnum {

	/**
	 * 文件后缀类型枚举
	 *
	 * @see FileSunffixConstant
	 */
	MD(0, "md"),
	/**
	 * PDF格式
	 *
	 * @see FileSunffixConstant
	 */
	PDF(1, "pdf"),
	/**
	 * HTML格式
	 *
	 * @see FileSunffixConstant
	 */
	HTML(2, "html"),
	/**
	 * 邮编
	 *
	 * @see FileSunffixConstant
	 */
	ZIP(3, "zip"),
	/**
	 * XLS
	 *
	 * @see FileSunffixConstant
	 */
	XLS(4, "xls"),
	/**
	 * XLSX
	 *
	 * @see FileSunffixConstant
	 */
	XLSX(5, "xlsx"),
	/**
	 * PPTX
	 *
	 * @see FileSunffixConstant
	 */
	PPTX(6, "pptx"),
	/**
	 * PPT
	 *
	 * @see FileSunffixConstant
	 */
	PPT(7, "ppt"),
	/**
	 * docx
	 *
	 * @see FileSunffixConstant
	 */
	DOCX(8, "docx"),
	/**
	 * 医生
	 *
	 * @see FileSunffixConstant
	 */
	DOC(9,"doc"),
	/**
	 * PNG 格式
	 *
	 * @see FileSunffixConstant
	 */
	PNG(10, "png"),
	/**
	 * jpeg 格式
	 *
	 * @see FileSunffixConstant
	 */
	JPEG(11, "jpeg"),
	/**
	 * jpg 格式
	 *
	 * @see FileSunffixConstant
	 */
	JPG(12, "jpg"),
	/**
	 * MP4
	 *
	 * @see FileSunffixConstant
	 */
	MP4(13, "mp4"),
	/**
	 * M4V
	 *
	 * @see FileSunffixConstant
	 */
	M4V(13, "m4v"),
	/**
	 * RAR
	 *
	 * @see FileSunffixConstant
	 */
	RAR(15,"rar");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	FileSunffixConstant(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}

