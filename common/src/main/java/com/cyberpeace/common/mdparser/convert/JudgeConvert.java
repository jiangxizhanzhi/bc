package com.cyberpeace.common.mdparser.convert;


/**
 * 评判 CONVERT
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个枚举类 `JudgeConvert`，用于表示某种判断转换的逻辑。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `JudgeConvert` 是一个实现 `MdConvertEnum<String, String>` 接口的枚举类。 
 * - 它用于表示两种判断状态：`CORRECT`（正确）和 `ERROR`（错误），每个状态都有对应的答案 (`answer`) 和描述 (`desc`)。 
 * 2. **字段**： 
 * - `answer`：存储判断的答案（如 "A" 或 "B"）。 
 * - `desc`：存储判断的描述（如 "正确" 或 "错误"）。 
 * 3. **构造方法**： 
 * - 枚举类通过构造方法初始化 `answer` 和 `desc` 字段。 
 * 4. **接口方法实现**： 
 * - `to()` 方法返回判断的答案 (`answer`)。 
 * - `reTo()` 方法返回判断的描述 (`desc`)。 
 * 5. **用途**： 
 * - 这个枚举类可以用于需要表示和转换判断状态的场景，例如考试系统中的判断题评分。 
 * 总结：这是一个简单的枚举类，用于封装判断状态及其相关信息，并提供转换功能。 
**/
public enum JudgeConvert implements MdConvertEnum<String, String> {

	/**
	 * 正确
	 *
	 * @see JudgeConvert
	 */
	CORRECT("A", "正确"),
	/**
	 * 错误
	 *
	 * @see JudgeConvert
	 */
	ERROR("B", "错误");

	/**
	 * 答案
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String desc;

	/**
	 * 评判 CONVERT
	 *
	 * @param answer 答案
	 * @param desc   描述
	 */
	JudgeConvert(String answer, String desc) {
		this.answer = answer;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code String }
	 */
	@Override
	public String to() {
		return answer;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return desc;
	}
}

