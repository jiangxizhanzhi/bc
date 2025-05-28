/**
 * 该代码定义了一个名为 `DemonstrationUriEnum` 的枚举类，用于管理与“演示”相关的 URI 和服务逻辑。以下是代码的主要功能和结构分析： 
 * --- 
 * ### **主要功能** 
 * 1. **枚举定义**： 
 * - 定义了多个枚举常量（如 `SITUATION_RANKING`, `SITUATION_SITUATION` 等），每个常量代表一种“态势”相关的功能。 
 * - 每个常量包含以下属性： 
 * - `uri`: 请求路径。 
 * - `filePath`: 文件路径。 
 * - `systemPath`: 系统路径。 
 * - `demonstrationService`: 提供具体业务逻辑的服务接口实现。 
 * 2. **核心方法**： 
 * - `getDemonstrationService()`: 返回当前枚举实例对应的 `DemonstrationService` 实现。 
 * - `getUri()`: 获取 URI 路径。 
 * - `getFilePath()`: 获取文件路径。 
 * - `getSystemPath()`: 获取系统路径。 
 * - `getByUri(String uri)`: 根据传入的 URI 查询对应的枚举实例。 
 * 3. **服务接口及实现**： 
 * - 定义了 `DemonstrationService` 接口，要求实现类提供 `deal(String content)` 方法。 
 * - 提供了两个具体的实现类： 
 * - `SituationNewsServiceImpl`: 处理“态势新增功能”的逻辑。 
 * - `SituationTrendServiceImpl`: 处理“态势趋势”的逻辑。 
 * 4. **工具类支持**： 
 * - 使用了 `DateUtil` 工具类来格式化日期。 
 * - 使用了 `JSONObject` 进行 JSON 数据的解析和生成。 
 * --- 
 * ### **代码特点** 
 * - **模块化设计**：通过枚举类将不同的功能模块统一管理，并通过 `DemonstrationService` 接口实现解耦。 
 * - **可扩展性**：可以通过新增枚举常量和对应的服务实现类轻松扩展新功能。 
 * - **强类型安全**：使用枚举确保 URI 和服务逻辑的绑定关系不会出错。 
 * - **清晰的职责划分**：每个服务实现类专注于处理特定的功能逻辑。 
 * --- 
 * ### **潜在问题或改进建议** 
 * 1. **默认值处理**： 
 * - 当前代码中部分枚举常量的 `demonstrationService` 属性为 `null`，这可能导致运行时空指针异常。建议在构造函数中强制初始化服务实现，或者提供默认实现。 
 * 2. **国际化支持**： 
 * - 如果 URI 或其他字段需要国际化，可以考虑将这些值存储在资源文件中。 
 * 3. **异常处理**： 
 * - 当前服务实现类未进行输入数据校验或异常捕获，可能会导致运行时错误。建议在 `deal` 方法中加入必要的校验逻辑。 
 * 4. **注释完善**： 
 * - 部分注释内容重复或不够详细，建议进一步优化注释，使其更具参考价值。 
 * --- 
 * ### **总结** 
 * 该代码实现了基于枚举的 URI 管理和业务逻辑封装，具有良好的模块化和扩展性。通过引入服务接口和工具类，代码结构清晰且易于维护。但在实际应用中需要注意潜在的空指针风险、异常处理以及国际化支持等问题。 
**/
package com.cyberpeace.enums;


import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.DateUtil;

import java.util.*;

/**
 * 演示 URI 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Enum
 * @since 1.0.0
 */
public enum DemonstrationUriEnum {
    /**
     * 态势排名
     *
     * @see DemonstrationUriEnum
     */
    SITUATION_RANKING("/ojj/rtks/situation/ranking/","/cpms/demonstration/situationRanking.json","classpath:static/demonstration/situationRanking.json",null),
    /**
     * 态势态势
     *
     * @see DemonstrationUriEnum
     */
    SITUATION_SITUATION("/ojj/rtks/situation/situation/","/cpms/demonstration/situationSituation.json","classpath:static/demonstration/situationSituation.json",null),
    /**
     * 态势趋势
     *
     * @see DemonstrationUriEnum
     */
    SITUATION_TREND("/ojj/rtks/situation/trend/","/cpms/demonstration/situationTrend.json","classpath:static/demonstration/situationTrend.json",new SituationTrendServiceImpl()),
    /**
     * 态势新增功能
     *
     * @see DemonstrationUriEnum
     */
    SITUATION_NEW("/ojj/rtks/situation/news/","/cpms/demonstration/situationNew.json","classpath:static/demonstration/situationNew.json",new SituationNewsServiceImpl());


    /**
     * URI
     *
     * @see String
     */
    private String uri;

    /**
     * 文件路径
     *
     * @see String
     */
    private String filePath;

    /**
     * 系统路径
     *
     * @see String
     */
    private String systemPath;

    /**
     * 示范Service
     *
     * @see DemonstrationService
     */
    private DemonstrationService demonstrationService;

    /**
     * 演示 URI 枚举
     *
     * @param uri                  URI
     * @param filePath             文件路径
     * @param systemPath           系统路径
     * @param demonstrationService 示范Service
     * @throws
     * @see String
     * @see String
     * @see String
     * @see DemonstrationService
     */
    DemonstrationUriEnum(String uri, String filePath, String systemPath, DemonstrationService demonstrationService) {
        this.uri = uri;
        this.filePath = filePath;
        this.systemPath = systemPath;
        this.demonstrationService = demonstrationService;
    }

    /**
     * 获取示范Service
     *
     * @return 示范Service
     * @throws
     * @see DemonstrationService
     */
    public DemonstrationService getDemonstrationService() {
        return demonstrationService;
    }

    /**
     * 获取URI
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getUri() {
        return uri;
    }

    /**
     * 获取文件路径
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 获取系统路径
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getSystemPath() {
        return systemPath;
    }

    /**
     * 获取由URI
     *
     * @param uri URI
     * @return 演示URI枚举
     * @throws
     * @see String
     * @see DemonstrationUriEnum
     */
    public static DemonstrationUriEnum getByUri(String uri) {

        for (DemonstrationUriEnum demonstrationUriEnum : DemonstrationUriEnum.values()) {
            if (demonstrationUriEnum.getUri().equals(uri)) {
                return demonstrationUriEnum;
            }
        }
        return null;
    }

    /**
     * 示范Service
     *
     * @author SN
     * @version 1.0.0
     * @date 2025/05/28
     * @since 1.0.0
     */
    public interface DemonstrationService {
        String deal(String content);
    }

    /**
     * 态势新闻Service实现
     *
     * @author SN
     * @version 1.0.0
     * @date 2025/05/28
     * @see DemonstrationService
     * @since 1.0.0
     */
    public static class SituationNewsServiceImpl implements DemonstrationService{

        /**
         * 交易
         *
         * @param content 内容
         * @return 字符串
         * @throws
         * @see String
         * @see String
         */
        @Override
        public String deal(String content) {
            List<JSONObject> list = JSONObject.parseArray(content, JSONObject.class);
            String date = DateUtil.DateToString(new Date(),"yyyy-MM-dd");
            for (JSONObject jsonObject : list) {
                jsonObject.put("create_time",date);
            }
            return JSONObject.toJSONString(list);
        }
    }

    /**
     * 态势趋势Service实现
     *
     * @author SN
     * @version 1.0.0
     * @date 2025/05/28
     * @see DemonstrationService
     * @since 1.0.0
     */
    public static class SituationTrendServiceImpl implements DemonstrationService{

        @Override
        public String deal(String content) {
            JSONObject trend = JSONObject.parseObject(content);
            List<String> xDates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 10; i++) {
                xDates.add(DateUtil.DateToString(calendar.getTime(),"yyyy-MM-dd"));
                calendar.add(Calendar.MONTH,-1);
            }
            Collections.reverse(xDates);
            trend.put("xdates",xDates);
            return trend.toJSONString();
        }
    }
}
