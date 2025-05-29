package com.cyberpeace.space.constant;

/**
 * 空间常用不断
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 * common constant
 * @author zhangxiaofeng
 * @version 1.0
 * @date 2024/11/06
 *
 */
/**
 * ### 代码分析 
 * #### 1. **代码结构** 
 * - 该代码定义了一个名为 `SpaceCommonConstant` 的公共常量类，用于存储与某个空间系统相关的通用常量。 
 * - 类中的内容被组织成多个内部静态类，每个内部类专注于一个特定的功能或领域。 
 * #### 2. **主要功能模块** 
 * - **默认策略框架 (`DEFAULT_STRATEGY_FRAME`)** 
 * - 定义了一个默认的策略框架名称字符串 `"default"`，可能用于指定默认的策略配置。 
 * - **战术阶段 (`TacticStage`)** 
 * - 内部类 `TacticStage` 定义了三个战术阶段的整数值： 
 * - `PRE`（值为 1）：表示“前”。 
 * - `IN`（值为 2）：表示“在”。 
 * - `POST`（值为 3）：表示“后”。 
 * - 注释表明此部分由作者 `SN` 编写，日期为 `2024/11/06`。 
 * - **战术类型 (`TacticType`)** 
 * - 内部类 `TacticType` 定义了两种战术类型的整数值： 
 * - `ATTACK`（值为 1）：表示攻击。 
 * - `DEFENSE`（值为 2）：表示防御。 
 * - **次序状态 (`OrderStatus`)** 
 * - 内部类 `OrderStatus` 定义了任务或订单的状态： 
 * - `UNRELEASED`（值为 0）：未发布。 
 * - `FINISHED`（值为 1）：已完成。 
 * - `UNFINISHED`（值为 2）：未完成。 
 * - 注释表明此部分由作者 `SN` 编写，日期为 `2025/05/28`。 
 * - **RVB用户组 (`RvbUserGroup`)** 
 * - 内部类 `RvbUserGroup` 定义了不同类型的用户组： 
 * - `COMMANDER`（值为 1）：指挥官。 
 * - `MIDDLE`（值为 2）：中间角色。 
 * - `WORKER`（值为 3）：工人。 
 * - `STAFF`（值为 4）：员工。 
 * - 注释表明此部分由作者 `SN` 编写，日期为 `2025/05/28`。 
 * #### 3. **注释与文档** 
 * - 代码中包含详细的注释，说明了每个字段的用途及其所属的作者和编写时间。 
 * - 但存在一些拼写错误，例如注释中的“不断”应为“常量”。 
 * #### 4. **潜在改进** 
 * - **拼写修正**：将注释中的“不断”改为“常量”。 
 * - **统一格式**：可以进一步规范注释格式，确保一致性。 
 * - **命名规范**：某些注释（如 `@see Integer`）显得冗余，可以直接省略。 
 * #### 总结 
 * 该代码是一个典型的常量类，通过分模块的方式组织了多个领域的通用常量。整体结构清晰，注释详细，适合用作配置类或枚举替代方案。 
**/
public class SpaceCommonConstant {
    
	/**
	 * 默认策略框架
	 */
    public static final String DEFAULT_STRATEGY_FRAME = "default";

    /**
     * 战术阶段
     *
     * @author SN
     * @date 2024/11/06
     */
    public static final class TacticStage {

        /**
         * 前
         */
        public static final Integer PRE = 1;

        /**
         * 在
         */
        public static final Integer IN = 2;

        /**
         * 后
         */
        public static final Integer POST = 3;
	}

	public static final class TacticType {

		/**
		 * ATTACK
		 */
		public static final Integer ATTACK = 1;

		/**
		 * DEFENSE
		 */
		public static final Integer DEFENSE = 2;
	}

	/**
	 * 次序状态
	 *
	 * @author SN
	 * @version 1.0.0
	 * @date 2025/05/28
	 * @since 1.0.0
	 */
	public static class OrderStatus {
		public static final Integer UNRELEASED = 0;
		public static final Integer FINISHED = 1;
		public static final Integer UNFINISHED = 2;
	}

	/**
	 * RVB用户组
	 *
	 * @author SN
	 * @version 1.0.0
	 * @date 2025/05/28
	 * @since 1.0.0
	 */
	public static class RvbUserGroup {

		/**
		 * 指挥官
		 *
		 * @see Integer
		 */
		public static final Integer COMMANDER = 1;

		/**
		 * 中间
		 *
		 * @see Integer
		 */
		public static final Integer MIDDLE = 2;

		/**
		 * 工人
		 *
		 * @see Integer
		 */
		public static final Integer WORKER = 3;

		/**
		 * 员工
		 *
		 * @see Integer
		 */
		public static final Integer STAFF = 4;
	}

}

