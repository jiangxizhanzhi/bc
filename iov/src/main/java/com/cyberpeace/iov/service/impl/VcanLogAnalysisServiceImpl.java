package com.cyberpeace.iov.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class VcanLogAnalysisServiceImpl {

    private RestHighLevelClient client;

    private static final String ES_HOST = "10.100.27.1";
    private static final Integer ES_PORT = 9205;
    private static final String ES_SCHEME = "http";

    @PostConstruct
    private void init() {
        HttpHost http = new HttpHost(ES_HOST, ES_PORT, ES_SCHEME);
        RestClientBuilder restClientBuilder = RestClient.builder(http);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        client = restHighLevelClient;
    }

    // es的client获取
    public RestHighLevelClient getESClient() {
        return client;
    }

    // 记录上次读取的文件的大小,用于略过上次读取的数据
    private Map<String, Long> recentReadTimeStamps = new HashMap<>();

    public void importVcanLog(MultipartFile multipartFile, Long size, String tableName, String sceneId, String elementId) {
        if (multipartFile == null || multipartFile.getName() == null || multipartFile.getName().equals("")) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
        } else if (!multipartFile.getOriginalFilename().endsWith("log")) {
            throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
        }
        // 数据收集
        List<Map> resultList = new ArrayList<>();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            long fileSize = multipartFile.getSize();
            // 如果文件大小超过需要截取的字节大小，则跳过该截取大小
            if (size != null && fileSize > size) {
                inputStream.skip(size);
            } else {
                throw new BusinessException(ErrorCode.RESTRICTED_FILE);
            }
            // 不保留重复数据
            if (recentReadTimeStamps != null && recentReadTimeStamps.get(tableName) != null) {
                Long lastTimeFileSize = recentReadTimeStamps.get(tableName);
                if (fileSize > lastTimeFileSize) {
                    if (size != null && fileSize > size) {
                        // 减去截取的字节大小
                        inputStream.skip(lastTimeFileSize - size);
                    } else {
                        inputStream.skip(lastTimeFileSize);
                    }
                }
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                String localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                while ((line = reader.readLine()) != null) {
                    if (line != null && !line.isEmpty()) {
                        JSONObject jsonObject = parseLogLine(line, sceneId, elementId, localTime);
                        if (jsonObject != null) {
                            resultList.add(jsonObject);
                        }
                    }
                }
            }
            // 记录当前文件的字节大小
            recentReadTimeStamps.put(tableName, fileSize);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_READ_FAIL, e);
        }

        // 写入resultList数据到 Elasticsearch 中
        if (!resultList.isEmpty()) {
            try {
                // 创建 BulkRequest 对象
                BulkRequest bulkRequest = new BulkRequest();
                // 遍历 resultList，构建 IndexRequest 并加入 BulkRequest
                for (Map map : resultList) {
                    IndexRequest indexRequest = new IndexRequest(tableName)
                            .source(map);
                    bulkRequest.add(indexRequest);
                }
                // 执行 BulkRequest
                client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                    @Override
                    public void onResponse(BulkResponse bulkResponse) {
                        if (bulkResponse.hasFailures()) {
                            log.info("Data insertion failure, cause:" + bulkResponse.buildFailureMessage());
//                            System.out.println("Data insertion failure, cause:" + bulkResponse.buildFailureMessage());
                        } else {
                            log.info("Bulk operation completed successfully.");
//                            System.out.println("Bulk operation completed successfully.");
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        log.info("Bulk operation failed:", e);
//                        System.out.println("Bulk operation failed:" + e);
                    }
                });
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.WRITE_ES_DATA_FAULT, e);
            }
        }
    }

    private JSONObject parseLogLine(String line, String sceneId, String elementId, String localTime) {
        JSONObject jsonObject = new JSONObject();
        try {
            // 解析日志行格式
            line = line.trim();
            int firstSpace = line.indexOf(" ");
            int secondSpace = line.indexOf(" ", firstSpace + 1);
            int hashIndex = line.indexOf("#");

            if (firstSpace > 0 && secondSpace > firstSpace && hashIndex > secondSpace) {
                String timestamp = line.substring(1, firstSpace - 1);
                String interfaceName = line.substring(firstSpace + 1, secondSpace);
                String id = line.substring(secondSpace + 1, hashIndex);
                String data = line.substring(hashIndex + 1);

                jsonObject.put("timestamp", convertTimestamp(Double.parseDouble(timestamp)));
                jsonObject.put("canInterfaceName", interfaceName);
                jsonObject.put("canId", id);
                jsonObject.put("data", data);
                jsonObject.put("DLC", data.length() / 2);
                jsonObject.put("sceneId", sceneId);
                jsonObject.put("elementId", elementId);
                jsonObject.put("readTime", localTime);
            }
        } catch (Exception e) {
            // 如果解析失败，可以返回 null 或者记录错误
            return null;
        }
        return jsonObject;
    }

    public String convertTimestamp(double timestamp) {
        // 将秒和纳秒分开
        long seconds = (long) timestamp;
        int nanos = (int) ((timestamp - seconds) * 1_000_000_000);
        // 转换为 Instant
        Instant instant = Instant.ofEpochSecond(seconds, nanos);
        // 转换为 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 格式化日期时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    public JSONObject getVcanLog(String tableName, String sceneId, String elementId,
                                 Integer pageNum, Integer pageSize) {
        // 创建查询请求
        SearchRequest searchRequest = new SearchRequest(tableName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置分页
        searchSourceBuilder.from((pageNum - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        // 设置查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (sceneId != null && !sceneId.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.termQuery("sceneId", sceneId));
        }
        if (elementId != null && !elementId.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.termQuery("elementId", elementId));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        // 执行查询
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.READ_ES_DATA_FAULT, e);
        }

        // 处理查询结果
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> result = new ArrayList<>();
        hits.forEach(hit -> {
            result.add(hit.getSourceAsMap());
        });

        // 获取总记录数
        long total = hits.getTotalHits().value;

        // 构建结果
        JSONObject finalResult = new JSONObject();
        finalResult.put("data", result);
        finalResult.put("total", total);

        return finalResult;
    }

    /**
     * 初始查询和滚动查询的分支判断：
     * 如果 scrollId 为空或为 null，则执行初始查询。
     * 如果 scrollId 不为空，则使用 scrollId 继续滚动查询。
     * scroll 参数：
     * scroll 是滚动查询的超时时间，比如 "1m" 表示 1 分钟。
     */
    public JSONObject getVcanLogWithScroll(String tableName, String sceneId, String elementId, String scroll, String scrollId, Integer pageSize) {
        JSONObject finalResult = new JSONObject();
        List<Map<String, Object>> result = new ArrayList<>();
        SearchResponse searchResponse;

        try {
            if (scrollId == null || scrollId.isEmpty()) {
                // 初始查询
                SearchRequest searchRequest = new SearchRequest(tableName);
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.size(pageSize);

                // 设置查询条件
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                if (sceneId != null && !sceneId.isEmpty()) {
                    boolQueryBuilder.must(QueryBuilders.termQuery("sceneId", sceneId));
                }
                if (elementId != null && !elementId.isEmpty()) {
                    boolQueryBuilder.must(QueryBuilders.termQuery("elementId", elementId));
                }
                searchSourceBuilder.query(boolQueryBuilder);
                searchRequest.source(searchSourceBuilder);

                // 设置 Scroll 超时时间
                searchRequest.scroll(TimeValue.parseTimeValue(scroll, "scroll"));

                // 执行初始查询
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            } else {
                // 使用 scrollId 执行后续查询
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(TimeValue.parseTimeValue(scroll, "scroll"));

                // 执行 Scroll 请求获取下一批数据
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            }

            // 获取 Scroll ID
            scrollId = searchResponse.getScrollId();
            SearchHits hits = searchResponse.getHits();

            // 处理查询结果
            for (SearchHit hit : hits) {
                result.add(hit.getSourceAsMap());
            }

            // 构建最终结果
            finalResult.put("data", result);
            finalResult.put("total", hits.getTotalHits().value);
            finalResult.put("scrollId", scrollId);

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.READ_ES_DATA_FAULT, e);
        }

        return finalResult;
    }


}
