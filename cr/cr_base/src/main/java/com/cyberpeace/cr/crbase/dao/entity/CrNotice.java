/**
 * ### 代码分析 
 * #### 1. **类的基本信息** 
 * - 类名为 `CrNotice`，位于包 `com.cyberpeace.cr.crbase.dao.entity` 中。 
 * - 继承了 `BaseTable` 类，并使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * #### 2. **注解说明** 
 * - 使用了 `@TableName` 注解，指定数据库表名为 `cr_notice`。 
 * - 使用了 `@TableId` 注解，定义主键字段为 `resource_id`，类型为 UUID。 
 * - 使用了 `javax.validation` 的注解如 `@NotBlank`、`@NotNull` 和 `@Size` 来进行字段校验。 
 * #### 3. **字段说明** 
 * - `resourceId`: 唯一标识符，类型为字符串。 
 * - `createUserId`: 创建公告的用户 ID，类型为字符串。 
 * - `context`: 公告内容，类型为字符串。 
 * - `role`: 公告对象的角色，类型为整数。 
 * - `roomId`: 房间 ID，类型为字符串。 
 * - `isTop`: 是否置顶，类型为整数（0 或 1）。 
 * - `noticeUsers`: 通知用户的 JSON 字符串，类型为字符串。 
 * #### 4. **方法说明** 
 * - `reoveUserId(String userId)`: 从 `noticeUsers` 中移除指定的用户 ID。使用 `JsonUtil` 将 `noticeUsers` 转换为 `List<String>`，执行移除操作后，再将列表转换回 JSON 字符串。 
 * #### 5. **枚举类说明** 
 * - 枚举类 `RoleGroup` 实现了多个接口：`GenericEnum`、`CrRole.RoleGroup` 和 `BaseI18n`。 
 * - 定义了四个角色：`TEACHER`、`STUDENT`、`RED` 和 `BLUE`，每个角色有对应的代码、中文描述和英文描述。 
 * - 提供了 `equals` 方法来比较角色代码，以及获取中文、英文和描述的方法。 
 * #### 6. **设计特点** 
 * - 使用了 MyBatis Plus 的注解来简化数据库操作。 
 * - 使用了 Lombok 和 Jackson 的工具类来处理 JSON 数据。 
 * - 枚举类的设计使得角色管理更加规范化和国际化。 
 * #### 总结 
 * 该类主要用于表示靶场中的通知实体，包含了基本的字段和操作方法。通过枚举类和注解的使用，提高了代码的可读性和可维护性。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.constant.BaseI18n;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;

/**
 * 靶场通知
 *
 * @author SN
 * @date 2024/11/08
 */
@TableName(value = "cr_notice")
@Getter
@Setter
public class CrNotice extends BaseTable {

    /**
     * uuid
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 公告内容
     */
    private String context;
    /**
     * 公告对象
     */
    private Integer role;
    /**
     * 房间id
     */
    private String roomId;

    /**
     * 是否置顶0否1是
     */
    private Integer isTop;

    /**
     * 通知用户
     */
    private String noticeUsers;

    public boolean reoveUserId(String userId) {
        List<String> userIds = JsonUtil.toJsonObjectArray(noticeUsers, String.class);
        boolean remove = userIds.remove(userId);
        if (remove) {
            noticeUsers = JsonUtil.toJsonString(userIds);
            return true;
        }
        return false;
    }

    public enum RoleGroup implements GenericEnum, CrRole.RoleGroup, BaseI18n {

        /***/
        TEACHER(1, "全部", "all"),
        STUDENT(2, "学员", "Student"),
        RED(3, "红方", "Red"),
        BLUE(4, "蓝方", "Blue");

        @Getter
        private final Integer code;

        @Getter
        private final String chinese;

        @Getter
        private final String english;

        RoleGroup(Integer code, String chinese, String english) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
        }

        public boolean equals(Integer roleGroupCode) {
            return getCode().equals(roleGroupCode);
        }

        @Override
        public String getChinese() {
            return this.chinese;
        }

        @Override
        public String getEnglish() {
            return this.english;
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

}
