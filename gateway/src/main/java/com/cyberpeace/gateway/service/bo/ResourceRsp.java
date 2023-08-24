package com.cyberpeace.gateway.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRsp implements Serializable {

	private Integer count;

	private String resourceName;
}
