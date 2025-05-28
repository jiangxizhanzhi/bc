/**
 * 这段代码定义了一个名为 `EvalLog` 的类，用于记录评估过程中的日志信息。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**：`EvalLog` 
 * - **功能**：记录评估过程中不同级别的日志信息（info、debug、error） 
 * ### 主要成员 
 * - **静态日志对象**： 
 * - `private static final Logger LOG = LoggerFactory.getLogger("eval");` 
 * - 用于记录日志信息 
 * ### 方法 
 * #### Info 级别日志方法 
 * 1. `static void info(EvalRecord evalRecord, String msg)` 
 * - 记录包含 `EvalRecord` 的基本信息和消息的 Info 级别日志。 
 * - 示例输出格式：`ID <ResourceId>: <msg>` 
 * 2. `static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalVariableRef` 的详细信息和消息的 Info 级别日志。 
 * - 示例输出格式：`variable <ResourceId> [<Name>] <msg>` 
 * 3. `static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalIndexRef` 的详细信息和消息的 Info 级别日志。 
 * - 示例输出格式：`index <ResourceId> [<Name>] <msg>` 
 * #### Error 级别日志方法 
 * 1. `static void error(EvalRecord evalRecord, String msg)` 
 * - 记录包含 `EvalRecord` 的基本信息和消息的 Error 级别日志。 
 * - 示例输出格式：`ID <ResourceId>: <msg>` 
 * 2. `static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalVariableRef` 的详细信息和消息的 Error 级别日志。 
 * - 示例输出格式：`variable <ResourceId> [<Name>] <msg>` 
 * 3. `static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalIndexRef` 的详细信息和消息的 Error 级别日志。 
 * - 示例输出格式：`index <ResourceId> [<Name>] <msg>` 
 * #### Debug 级别日志方法 
 * 1. `static void debug(EvalRecord evalRecord, String msg)` 
 * - 记录包含 `EvalRecord` 的基本信息和消息的 Debug 级别日志。 
 * - 示例输出格式：`ID <ResourceId>: <msg>` 
 * 2. `static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalVariableRef` 的详细信息和消息的 Debug 级别日志。 
 * - 示例输出格式：`variable <ResourceId> [<Name>] <msg>` 
 * 3. `static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg)` 
 * - 记录包含 `EvalRecord` 和 `EvalIndexRef` 的详细信息和消息的 Debug 级别日志。 
 * - 示例输出格式：`index <ResourceId> [<Name>] <msg>` 
 * ### 总结 
 * - `EvalLog` 类提供了一种结构化的方式来记录与评估相关的日志信息，包括 `EvalRecord`, `EvalVariableRef`, 和 `EvalIndexRef` 的相关信息。 
 * - 每个方法都根据不同的日志级别（Info, Debug, Error）来记录信息，并且能够处理不同类型的引用对象（如 `EvalVariableRef` 和 `EvalIndexRef`），从而提供了详细的上下文信息。 
 * 这些方法使得开发者可以方便地记录评估过程中发生的事件，并且可以根据需要调整日志级别以控制日志的详细程度和输出量。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 评估日志
 *
 * @author SN
 * @date 2024/11/08
 */
public class EvalLog {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    /**
     * 信息
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void info(EvalRecord evalRecord, String msg) {
        LOG.info(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 信息
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 信息
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 错误
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void debug(EvalRecord evalRecord, String msg) {
        LOG.debug(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 调试
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        debug(evalRecord, newMsg);
    }
}
