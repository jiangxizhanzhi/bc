package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oj/admin/attachment")
public class AdminAttachmentController extends PublicController<AttachmentService, Attachment> {


}
