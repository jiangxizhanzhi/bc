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
