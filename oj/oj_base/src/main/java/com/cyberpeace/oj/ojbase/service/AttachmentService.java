package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;

import java.util.List;

public interface AttachmentService extends IService<Attachment> {

    /**
     * 根据附件编号查询附件信息 以,分割
     * @param attachmentIds
     * @param isWeb
     * @return
     */
    List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb);

    /**
     * 删除修改后
     * @return
     */
    Integer deleteDifferenceSet(String oldAttachmentIds, String attachmentIds);

}
