/**
 * 该代码定义了一个常量接口 `MpSceneStatusConstant`，用于存储与中台场景状态相关的整型常量值。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - 使用 `interface` 定义常量接口，符合 Java 的常量规范（接口中默认的成员变量是 `public static final`）。 
 * - 接口名称 `MpSceneStatusConstant` 表明其用途是存储与中台场景状态相关的常量。 
 * 2. **常量说明**： 
 * - 提供了多个与场景状态相关的整型常量： 
 * - `MP_SCENE_PAUSE_EVENT_CODE`：值为 `6`，表示暂停事件的代号。 
 * - `MP_SCENE_UNPAUSE_EVENT_CODE`：值为 `7`，表示恢复事件的代号。 
 * - `MP_SCENE_DELETE_EVENT_CODE`：值为 `9`，表示场景删除事件的代号。 
 * - `MP_SCENE_PROGRESS_FINISH_CODE`：值为 `4`，表示场景事件完成的代号。 
 * 3. **注释说明**： 
 * - 每个常量都有详细的注释，说明其含义和用途。 
 * - 注解 `@see Integer` 表示这些常量的类型是 `Integer`。 
 * 4. **设计目的**： 
 * - 通过将这些常量集中在一个接口中，便于统一管理，避免在代码中硬编码，提高可维护性和可读性。 
 * 总结：这是一个典型的常量接口设计，用于集中管理与中台场景状态相关的整型常量值，方便后续代码引用和维护。 
**/
package com.cyberpeace.common.constant;

/**
 * 中台场景状态不断
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface MpSceneStatusConstant {


	/**
	 * 暂停
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_PAUSE_EVENT_CODE = 6;

	/**
	 * 恢复
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_UNPAUSE_EVENT_CODE = 7;
	/**
	 * 场景删除事件代号
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_DELETE_EVENT_CODE = 9;

	/**
	 * 场景事件完成代号
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_PROGRESS_FINISH_CODE = 4;
}
