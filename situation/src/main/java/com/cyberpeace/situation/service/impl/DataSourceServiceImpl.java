package com.cyberpeace.situation.service.impl;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.controller.req.DataSourceRecordDelReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordReq;
import com.cyberpeace.situation.controller.req.DataSourceReq;
import com.cyberpeace.situation.controller.res.DataResourceRecordInfoRes;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.dao.entity.DataSourceRecord;
import com.cyberpeace.situation.dao.entity.DataSourceTemplate;
import com.cyberpeace.situation.dao.mapper.DataSourceRecordMapper;
import com.cyberpeace.situation.dao.mapper.DataSourceTemplateMapper;
import com.cyberpeace.situation.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author xuxz
 * @Date 28/10/2022 下午2:07
 */
@Service
@Slf4j
public class DataSourceServiceImpl extends ServiceImpl<DataSourceRecordMapper, DataSourceRecord> implements DataSourceService {

    @Autowired
    private DataSourceTemplateMapper dataSourceTemplateMapper;

    @Autowired
    private DataSourceRecordMapper dataSourceRecordMapper;

    private volatile Map<String, Connection> connectionCache = new HashMap<>();

    private static final Pattern PATTERN = Pattern.compile("\b(exec|insert|drop|grant|alter|delete|update|chr|mid|master|truncate|char|declare)|\b(;)");

    @Override
    public String testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
        Connection connection = getConnection(dataSourceRecordReq, null);
        String conn = connection.toString();
        closeDB(null, null);
        return conn;
    }

    @Override
    public String testDBConnection(String resourceId) {
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        if (dataSourceRecord == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        DataSourceRecordReq dataSourceRecordReq = BeanUtils.copy(dataSourceRecord, DataSourceRecordReq.class);
        return testDBConnection(dataSourceRecordReq);
    }

    @Override
    public List<String> getDataSourceType() {
        return dataSourceTemplateMapper.getDataSourceType();
    }

    @Override
    public ListRsp<DataSourceRecordRes> getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq) {
        Page page = dataSourceRecordPageReq.toPageParam();
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                CommonConstant.FALSE);
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, authContext, dataSourceRecordPageReq));
    }

    @Override
    public DataResourceRecordInfoRes getDataSourceRecordInfo(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        return BeanUtils.copy(dataSourceRecord, DataResourceRecordInfoRes.class);
    }

    @Override
    public Integer deleteDataSourceRecord(DataSourceRecordDelReq dataSourceRecordDelReq) {
        return dataSourceRecordMapper.deleteBatchIds(dataSourceRecordDelReq.getDataResourceIdList());
    }

    @Override
    public String createDataSourceRecord(DataSourceRecordReq dataSourceRecordReq) {
        DataSourceRecord dataSourceRecord = dataSourceRecordReqToDataSourceRecord(dataSourceRecordReq);
        dataSourceRecord.setResourceId(IDGeneratorUtil.getID());
        dataSourceRecord.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        dataSourceRecord.setConnectStatus(Boolean.TRUE);
        dataSourceRecordMapper.insert(dataSourceRecord);
        return dataSourceRecord.getResourceId();
    }

    @Override
    public String updateDataSourceRecord(DataSourceRecordReq dataSourceRecordReq) {
        if (dataSourceRecordReq == null) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        String resourceId = dataSourceRecordReq.getResourceId();
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordReqToDataSourceRecord(dataSourceRecordReq);
        DataSourceRecord dataSource = dataSourceRecordMapper.selectById(resourceId);
        if (dataSource == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        dataSourceRecordMapper.updateById(dataSourceRecord);
        return resourceId;
    }

    @Override
    public List<String> getDBTables(String resourceId, String dbName) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        Connection connection = getConnection(dataSourceRecordToDataSourceRecordReq(dataSourceRecord), dbName);
        ResultSet resultSet = null;
        List<String> tableList = new ArrayList<>();
        if (connection != null) {
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                resultSet = metaData.getTables(dbName, null, null, new String[]{"TABLE"});
                while (resultSet.next()) {
                    tableList.add(resultSet.getString("TABLE_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeDB(null, resultSet);
            }
        }
        return tableList;
    }

    @Override
    public List<String> getDBName(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        Connection connection = getConnection(dataSourceRecordToDataSourceRecordReq(dataSourceRecord), null);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> list = new ArrayList<>();
        try {
            String sql = "SHOW DATABASES";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String database = resultSet.getString("Database");
                if (StringUtils.isNotEmpty(database)) {
                    list.add(database);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDB(preparedStatement, resultSet);
        }
        return list;
    }

    @Override
    public List<JSONObject> getDataSourceData(DataSourceReq dataSourceReq) {
        String sql = dataSourceReq.getSql();
        String dbName = dataSourceReq.getDbName();
        String resourceId = dataSourceReq.getResourceId();
        String customParams = dataSourceReq.getCustomParams();
        String tableName = dataSourceReq.getTableName();
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        if (dataSourceRecord == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        //选定数据库时，自动获取所有数据表字段
        if (StringUtils.isNotBlank(tableName) && StringUtils.isEmpty(sql)) {
            sql = "SELECT * FROM " + dbName + "." + tableName;
        }
        String parseSQL = parseSQL(customParams, sql);
        //过滤非法sql字段

        if (StringUtils.isEmpty(parseSQL) || transactSQLInjection(parseSQL)) {
            throw new BusinessException(ErrorCode.ILLEGAL_SQL);
        }

        List<JSONObject> dataList = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        DataSourceRecordReq dataSourceRecordReq = dataSourceRecordToDataSourceRecordReq(dataSourceRecord);
        Connection connection = getConnection(dataSourceRecordReq, dbName);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(parseSQL);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                columnList.add(columnName);
            }
            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                for (String name : columnList) {
                    Object object = resultSet.getObject(name);
                    jsonObject.put(name, object);
                }
                dataList.add(jsonObject);
            }
        } catch (SQLException e) {
            throw new BusinessException(ErrorCode.ILLEGAL_SQL);
        } finally {
            closeDB(preparedStatement, resultSet);
        }
        return dataList;
    }

    private Connection getConnection(DataSourceRecordReq dataSourceRecordReq, String dbName) {
        String type = dataSourceRecordReq.getType();
        if (StringUtils.isEmpty(type)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceTemplate dataSourceTemplate = dataSourceTemplateMapper.selectByType(type);
        if (dataSourceTemplate == null) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_DB_TYPE);
        }
        Connection conn = null;
        try {
            Class.forName(dataSourceTemplate.getDataSource());
            String url;
            if (StringUtils.isNotBlank(dbName)) {
                url = String.format(dataSourceTemplate.getConnectDBUrl(), dataSourceRecordReq.getConnectAddress(), dataSourceRecordReq.getPort(), dbName);
            } else {
                url = String.format(dataSourceTemplate.getConnectUrl(), dataSourceRecordReq.getConnectAddress(), dataSourceRecordReq.getPort());
            }
            if (connectionCache.containsKey(url)) {
                Connection connection = connectionCache.get(url);
                if (connection != null) {
                    return connection;
                }
            }
            String password = decryptedPwd(dataSourceRecordReq.getPassword());
            conn = DriverManager.getConnection(url, dataSourceRecordReq.getUserName(), password);
            if (conn != null) {
                dataSourceRecordReq.setConnectStatus(Boolean.TRUE);
                if (connectionCache.containsKey(url)) {
                    connectionCache.replace(url, conn);
                }else {
                    connectionCache.put(url, conn);
                }
            }
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage());
            dataSourceRecordReq.setConnectStatus(Boolean.FALSE);
            throw new BusinessException(ErrorCode.CONNECT_DATABASE_FAILED);
        } finally {
            updateDataSourceRecord(dataSourceRecordReq);
        }
    }

    private void closeDB(PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;

            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;
        } catch (Exception e) {
            log.error("数据源关闭失败", e);
        }
    }

    private DataSourceRecord dataSourceRecordReqToDataSourceRecord(DataSourceRecordReq dataSourceRecordReq) {
        return BeanUtils.copy(dataSourceRecordReq, DataSourceRecord.class);
    }

    private DataSourceRecordReq dataSourceRecordToDataSourceRecordReq(DataSourceRecord dataSourceRecord) {
        return BeanUtils.copy(dataSourceRecord, DataSourceRecordReq.class);
    }

    private String decryptedPwd(String password) {
        DES des = SecureUtil.des("B13H089Y".getBytes(StandardCharsets.UTF_8));
        return des.decryptStr(password);
    }

    private Boolean transactSQLInjection(String str) {
        Matcher matcher = PATTERN.matcher(str);
        return matcher.find();
    }

    private String parseSQL(String customParams, String sql){
        String id = IDGeneratorUtil.getID();
        String keyWord = " and ";
        HashMap<String, String> map = new HashMap<>();
        //映射自定义参数列表
        if (StringUtils.isNotBlank(customParams)) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                JSONArray jsonArray = JsonUtil.stringToArray(customParams);
                for (Object o : jsonArray) {
                    JSONObject datumObject = JsonUtil.java2JsonObject(o);
                    String type = datumObject.getString("type");
                        String value = datumObject.getString("value");
                        String name = datumObject.getString("name");
                        String key = datumObject.getString("symbol");
                        if (StringUtils.isNotBlank(value) && Objects.equals(type, "constant")) {
                            stringBuilder.append(key).append("'").append(value).append("'");
                            map.put(name, stringBuilder.toString());
                        }
                }
            }catch (Exception e) {
                throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
            }
        }
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("select * from (").append(sql).append(") as ").append(id);
       if (!map.isEmpty()) {
           sqlSB.append(" where ");
           int index = 0;
           for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
               sqlSB.append(stringStringEntry.getKey()).append(stringStringEntry.getValue());
               if (index != map.size() - 1 ) {
                   sqlSB.append(keyWord);
               }
               index ++;
           }
       }
        return sqlSB.toString();
    }
}
