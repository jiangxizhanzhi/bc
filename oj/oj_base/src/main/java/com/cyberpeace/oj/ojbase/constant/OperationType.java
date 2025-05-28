/**
 * 这段代码定义了一个名为 `OperationType` 的枚举类，它实现了 `GenericEnum` 接口。这个枚举类用于表示不同类型的操作，例如攻击、防御和其他类型的操作。 
 * ### 主要特性： 
 * 1. **枚举常量**： 
 * - `ATTACK`：代码为1，描述为“攻击”。 
 * - `DEFENSE`：代码为2，描述为“防御”。 
 * - `OTHER`：代码为3，描述为“其他”。 
 * 2. **字段**： 
 * - `code`：操作的代码，使用 `@Getter` 注解表明可以通过 `getCode()` 方法访问该字段。 
 * - `desc`：操作的描述，同样使用 `@Getter` 注解表明可以通过 `getDesc()` 方法访问该字段。 
 * 3. **构造函数**： 
 * - 枚举类有一个私有的构造函数，接受两个参数：一个整数代码和一个字符串描述。这确保了枚举常量只能通过这些预定义的值来创建。 
 * 4. **实现接口**： 
 * - `OperationType` 实现了 `GenericEnum` 接口，这意味着它可能需要提供一些通用的方法来处理枚举实例（如获取代码或描述）。 
 * ### 总结： 
 * - 这个枚举类主要用于定义不同类型的操作，并且每个操作都有一个唯一的代码和描述。 
 * - 通过 `@Getter` 注解，可以方便地获取枚举实例的代码和描述。 
 * - 这种设计模式使得代码更加清晰和易于维护，同时也便于扩展新的操作类型。 
 * ### 可能的应用场景： 
 * - 在一个在线竞赛平台中，可以使用这个枚举类来区分不同类型的用户操作，如攻击、防御或其他行为。 
 * - 在游戏开发中，可以用来表示不同的游戏事件或动作类型。 
 * 总的来说，这是一个简洁而有效的枚举类设计，适用于需要明确区分几种固定类型的操作的场景。 
**/
package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum OperationType implements GenericEnum {

    /**
     * 主要
     *
     * @see Difficulty
     */
    ATTACK(1, "攻击"),
    /**
     * 中间
     *
     * @see Difficulty
     */
    DEFENSE(2, "防御"),
    /**
     * 高
     *
     * @see Difficulty
     */
    OTHER(3, "其他");

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

    OperationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
