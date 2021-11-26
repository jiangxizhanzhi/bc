package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Attachment extends BaseTable {

	public static String parentPath;

	@Value("${oj.file.root.path}")
	public void setParentPath(String parentPath) {
		Attachment.parentPath = parentPath;
	}

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String fileName;

	private String filePath;

	@TableField("public")
	private Boolean isPublic;

	public enum AttachmentType implements GenericEnum {

		HANDOUT(1, Attachment.parentPath + "/course/handout/"), VIDEO(2,
				Attachment.parentPath + "/course/video/"), ATTACHMENT(3,
						Attachment.parentPath + "/course/attachment/"),
		OTHER(4, Attachment.parentPath + "/other/");

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
