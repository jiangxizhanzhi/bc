package com.cyberpeace.situation.service.impl;


import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

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

    private DataSourceRecord selectDataSourceRecordById(String resourceId, Boolean throwNull) {
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        if (dataSourceRecord == null && throwNull) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return dataSourceRecord;
    }

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

    /**
     * 获取数据库表列表
     *
     * @param resourceId 数据源ID
     * @param dbName     数据库名称
     * @return {@code List<String>}
     */
    @Override
	public List<String> getDBTables(String resourceId, String dbName) {

		// 1、根据Id获取数据源信息
		DataSourceRecord dataSourceRecord = selectDataSourceRecordById(resourceId, true);

		// 2、获取数据库连接
		Connection connection = getConnection(
				dataSourceRecordToDataSourceRecordReq(dataSourceRecord), dbName);

		// 3、获取数据库表名列表
		ResultSet resultSet = null;
		List<String> tableList = new ArrayList<>();
		if (connection != null) {
			try {
				DatabaseMetaData metaData = connection.getMetaData();

				// 提取数据库下表名列表
				if (dataSourceRecord.getType().equals("IMPALA")) {
					// IMPALA数据库处理
					resultSet = metaData.getTables(null, dbName, null, new String[] { "TABLE" });
				} else {
					// 其他数据库处理
					resultSet = metaData.getTables(dbName, null, null, new String[] { "TABLE" });
				}

				// 填充表名列表
				while (resultSet.next()) {
					tableList.add(resultSet.getString("TABLE_NAME"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// 关闭数据库连接
				closeDB(null, resultSet);
			}
		}
		return tableList;
	}

    /**
     * 获取数据库库列表
     *
     * @param resourceId 数据源Id
     * @return {@code List<String>}
     */
    @Override
    public List<String> getDBName(String resourceId) {

        // 1、根据Id获取数据源信息
        DataSourceRecord dataSourceRecord = selectDataSourceRecordById(resourceId, true);

        // 2、获取数据库连接
        Connection connection = getConnection(dataSourceRecordToDataSourceRecordReq(dataSourceRecord), null);

        // 3、获取数据库名称列表
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> list = new ArrayList<>();

        try {

            // 3.1、获取sql执行结果
            String sql = "SHOW DATABASES";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            String type = dataSourceRecord.getType();

            // 3.2、根据不同的数据库类型，提取数据库名称
            while (resultSet.next()) {
                String dbName = null;
                if (type.equals("HIVE")) {
                    dbName = "database_name";
                } else if (type.equals("MYSQL")) {
                    dbName = "Database";
                } else if (type.equals("IMPALA")) {
                    dbName = "name";
                }
                String database = resultSet.getString(dbName);

                if (StringUtils.isNotEmpty(database)) {
                    list.add(database);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 3.3、关闭数据库连接
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
                    if (name.contains(".")) {
                        name = name.substring(name.indexOf(".") + 1);
                    }
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
        String resourceId = dataSourceRecordReq.getResourceId();
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
                } else {
                    connectionCache.put(url, conn);
                }
            }
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage());
            dataSourceRecordReq.setConnectStatus(Boolean.FALSE);
            throw new BusinessException(ErrorCode.CONNECT_DATABASE_FAILED);
        } finally {
            if (StringUtils.isNotBlank(resourceId)) {
                updateDataSourceRecord(dataSourceRecordReq);
            }
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

    private String parseSQL(String customParams, String sql) {
        HashMap<String, String> map = new HashMap<>();
        //映射自定义参数列表
        if (StringUtils.isNotBlank(customParams)) {
            try {
                JSONArray jsonArray = JsonUtil.stringToArray(customParams);
                for (Object o : jsonArray) {
                    JSONObject datumObject = JsonUtil.java2JsonObject(o);
                    String value = datumObject.getString("value");
                    String name = datumObject.getString("name");
                    if (StringUtils.isNotBlank(value)) {
                        map.put(name, value);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
            }
        }
        if (!map.isEmpty()) {
            for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                String key = "${"+ stringStringEntry.getKey() + "}";
                if (sql.contains(key)){
                    sql = sql.replace(key, stringStringEntry.getValue());
                }
            }
        }
        return sql;
    }
}
