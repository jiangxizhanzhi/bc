package com.cyberpeace.scene.service.bo.req;

import java.util.List;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

@Data
public class SnapshotReq {

	private String snapshot_name = "name1";

	private List<String> instances = Lists.newArrayList();
}
