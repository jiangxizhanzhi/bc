package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment extends BaseTable {

	public static String PARENT_PATH = "/Users/chenzhuo/home";

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String fileName;

	private String filePath;

	@TableField("public")
	private Boolean isPublic;

	public enum AttachmentType implements GenericEnum {

		HANDOUT(1, Attachment.PARENT_PATH + "/course/handout/"),
		VIDEO(2, Attachment.PARENT_PATH + "/course/video/"),
		ATTACHMENT(3, Attachment.PARENT_PATH + "/course/attachment/");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		AttachmentType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
