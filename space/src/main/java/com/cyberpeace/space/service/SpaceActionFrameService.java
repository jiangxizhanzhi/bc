package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.controller.req.SpaceFramePBListReq;
import com.cyberpeace.space.controller.res.FrameListRes;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameRecBo;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;

import java.util.List;
import java.util.Map;

/**
 * 空间行动框架Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/05
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameService` 的接口，它扩展了 `IService<SpaceActionFrame>` 接口。这个接口用于管理与 `SpaceActionFrame` 相关的操作和服务。以下是代码的主要组成部分和功能分析：
 * 1. **继承关系**：`SpaceActionFrameService` 继承自 `IService<SpaceActionFrame>`，这意味着它包含了 MyBatis-Plus 提供的基本 CRUD 操作方法。
 * 2. **方法定义**：
 * - `countByType(Integer code)`：根据指定的类型代码返回记录的数量。
 * - `getFrameLevels(String resourceId)`：获取某个资源ID对应的框架级别信息。
 * - `saveFrame(SpaceFrameLevel req)` 和 `saveFrame(ActionFrameArrangeBo req)`：两种不同的保存框架的方法，分别接收不同类型的请求参数。
 * - `recordUsage(String type)`：记录某种类型的使用情况。
 * - `getFrame(String resourceId)`：根据资源ID获取行动框架的安排信息。
 * - `searchFrame(String req)`：搜索框架，返回符合条件的空间行动框架列表。
 * - `getTacticNameMap(ActionFrameArrangeBo bo)`：获取战法名称映射，返回一个字符串到字符串的映射表。
 * - `getTuiyanRecFrame(String categoryId, String keyWord)`：根据类别ID和关键词获取推荐的框架列表。
 * - `add(SpaceActionFrame entity)`：新增一条空间行动框架记录。
 * 3. **注释说明**：每个方法都有详细的注释，描述了其功能、输入参数以及可能的输出类型，这有助于其他开发者理解接口的具体用途。
 * 4. **泛型使用**：接口中使用了多个泛型类（如 `ActionFrameArrangeBo`, `SpaceActionFrameBo`, `SpaceActionFrameRecBo`），这些类可能是为了封装特定业务逻辑的数据结构。
 * 总体来说，这个接口提供了一套完整的关于 `SpaceActionFrame` 对象的操作方法，涵盖了从数据检索到存储再到业务处理等多个方面，适用于复杂的业务场景。
**/
public interface SpaceActionFrameService extends IService<SpaceActionFrame> {

    /**
     * 计数由类型
     *
     * @param code 代号
     * @return {@code Integer }
     */
    Integer countByType(Integer code);

    /**
     * 获取框架水平
     *
     * @param resourceId 资源id
     * @return {@code SpaceActionFrameLevel }
     */
    SpaceFrameLevel getFrameLevels(String resourceId);

    /**
     * 保存框架
     *
     * @param req 请求体
     */
    boolean saveFrame(SpaceFrameLevel req);

    /**
     * 保存框架
     *
     * @param req 请求体
     * @throws
     * @see ActionFrameArrangeBo
     */
    void saveFrame(ActionFrameArrangeBo req);
    /**
     * 记录使用情况
     *
     * @param type 类型
     * @throws
     * @see String
     */
    void recordUsage(String type, int n);

    void recordUsage(Integer type, int n);

    void recordUsage(List<SpaceActionFrame> frameList);
    /**
     * 获取框架
     *
     * @param resourceId 资源id
     * @return 行动框架安排Bo
     * @throws
     * @see String
     * @see ActionFrameArrangeBo
     */
    ActionFrameArrangeBo getFrame(String resourceId);

    /**
     * 搜索框架
     *
     * @param req 请求体
     * @return 列表<空间行动框架博>
     * @throws
     * @see String
     * @see List<SpaceActionFrameBo>
     */
    List<SpaceActionFrameBo> searchFrame(String req);

    /**
     * 获取战法名称映射
     *
     * @param bo Bo
     * @return map<string 、 string>
     * @throws
     * @see ActionFrameArrangeBo
     * @see Map<String, String>
     */
    Map<String, String> getTacticNameMap(ActionFrameArrangeBo bo);

    /**
     * 获取推燕娱乐框架
     *
     * @param categoryId 类别id
     * @param keyWord    关键词
     * @return 列表<空间行动框架娱乐博>
     * @throws
     * @see String
     * @see String
     * @see List<SpaceActionFrameRecBo>
     */
    List<SpaceActionFrameRecBo> getTuiyanRecFrame(String categoryId, String keyWord);

    /**
     * 新增
     *
     * @param entity 实体
     * @throws
     * @see SpaceActionFrame
     */
    void add(SpaceActionFrame entity);

//    List<SpaceActionFrameRecBo> getNengliRecFrame(String categoryId, String keyWord);

    List<FrameListRes> defenseFrameList(SpaceFramePBListReq req);
}
