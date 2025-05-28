/**
 * 这段代码定义了一个名为 `IndicatorService` 的接口，该接口位于 `com.cyberpeace.oj.ojbase.service` 包中。这个接口包含三个方法： 
 * 1. `IndicatorBo getIndicators(Integer type);` 
 * - 这个方法接收一个整数类型的参数 `type`，并返回一个 `IndicatorBo` 类型的对象。`IndicatorBo` 可能是一个业务对象（BO），用于封装与指示器相关的数据。 
 * 2. `List<Indicator> selectIndicators(List<Integer> ids);` 
 * - 这个方法接收一个整数列表 `ids` 作为参数，并返回一个 `Indicator` 对象的列表。`Indicator` 是从 `com.cyberpeace.oj.ojbase.dao.entity` 包中导入的实体类，通常表示数据库中的一个表或数据记录。 
 * 3. `JSONArray parseIds(String attackIdJson);` 
 * - 这个方法接收一个字符串 `attackIdJson`，该字符串可能是包含攻击 ID 的 JSON 格式的数据。此方法返回一个 `JSONArray` 对象，`JSONArray` 是来自 `com.alibaba.fastjson` 包的一个类，用于处理 JSON 数据结构。 
 * 总体来说，这个接口提供了一组方法来获取和操作与指标（可能是指安全或性能方面的度量）相关的数据。这些方法可以用于从数据库中检索指标信息、解析特定格式的 JSON 数据，以及将这些数据转换为便于使用的对象。 
**/
package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.oj.ojbase.dao.entity.Indicator;
import com.cyberpeace.oj.ojbase.service.bo.IndicatorBo;

import java.util.List;

public interface IndicatorService {
    IndicatorBo getIndicators(Integer type);

    List<Indicator> selectIndicators(List<Integer> ids);

    JSONArray parseIds(String attackIdJson);
}
