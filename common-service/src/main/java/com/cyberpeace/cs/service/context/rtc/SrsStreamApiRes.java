package com.cyberpeace.cs.service.context.rtc;

import java.util.List;

import lombok.Data;

@Data
public class SrsStreamApiRes extends SrsApiRes {

	private List<SrsStream> streams;
}
