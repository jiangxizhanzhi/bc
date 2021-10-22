package com.cyberpeace.oj.ojbase.controller;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.controller.req.ResourceAuthReq;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.ojbase.controller.req.ResourceShareReq;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class AuthAndPublicController<T extends IService<E>, E> {

    @Autowired
    private T baseService;

    private Class<E> entityClass = currentModelCls();

    @SuppressWarnings("unchecked")
    private Class<E> currentModelCls() {
        return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), 0);
    }

    @PatchMapping("/batch-public")
    @ApiOperation("资源公开/隐藏")
    public BaseRsp<Boolean> batchPublic(@RequestBody @Valid ResourcePublicReq requestData) {

        Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
            AuthAndPublicData data = new AuthAndPublicData();
            data.setIsPublic(requestData.getIsPublic());
            data.setModifyUserId(UserInfoContext.getUserContext().getUserId());
            data.setResourceId(resourceId);
            return data;
        });

        return new BaseRsp<>(result);
    }

    @PatchMapping("/batch-auth")
    @ApiOperation("资源授权访问")
    public BaseRsp<Boolean> batchAuth(@RequestBody @Valid ResourceAuthReq requestData) {

        Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
            AuthAndPublicData data = new AuthAndPublicData();
            data.setResourceId(resourceId);
            data.setReadAuthType(requestData.getReadAuthType());
            data.setReadAuthIds(JsonUtil.arrayToString(requestData.getReadAuthIds()));
            return data;
        });

        return new BaseRsp<>(result);
    }

    @PatchMapping("/batch-share")
    @ApiOperation("资源授权操作")
    public BaseRsp<Boolean> batchShare(@RequestBody ResourceShareReq requestData) {

        Boolean result = updateData(requestData.getResourceIds(), resourceId -> {
            AuthAndPublicData data = new AuthAndPublicData();
            data.setResourceId(resourceId);
            data.setOperationAuthType(requestData.getOperationAuthType());
            data.setReadAuthIds(JsonUtil.arrayToString(requestData.getOperationAuthIds()));
            return data;
        });
        return new BaseRsp<>(result);
    }

    /**
     * 数据更新
     * @param collection
     * @param mapperValue
     * @param <U>
     * @param <V>
     * @return
     */
    private <U, V> Boolean updateData(Collection<U> collection, Function<U, V> mapperValue) {
        List<V> publicDatas = StreamApiUtils.simpleMap(collection, mapperValue);
        List<E> entityList = BeanUtils.deepCopyList(publicDatas, entityClass);

        boolean result = baseService.updateBatchById(entityList);
        return result;
    }

    /**
     * 数据中间存储
     */
    @Data
    class AuthAndPublicData {

        private String resourceId;

        private Boolean isPublic;

        private Integer readAuthType;

        private String readAuthIds;

        private Integer operationAuthType;

        private String operationAuthIds;

        private String modifyUserId;
    }

}
