package com.cyberpeace.cs.constant;

import lombok.Data;

@Data
public final class FileChunkUploadStatus {

	public static final Integer NOT_UPLOADED = 0;

	public static final Integer SUCCESS = 1;

	public static final Integer FAIL = 2;

	public static final Integer ONGOING = 3;

}
