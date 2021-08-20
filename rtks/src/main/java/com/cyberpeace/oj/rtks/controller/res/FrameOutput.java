package com.cyberpeace.oj.rtks.controller.res;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import lombok.Data;

@Data
public class FrameOutput {

	private String resourceId;

	private String name;

	private String description;

	public FrameOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
	}

	public static FrameOutput fromLevel(Level level) {
		return new FrameOutput(level);
	}

	public static List<FrameOutput> listFromLevels(List<Level> levels) {
		List<FrameOutput> frameOutputs = new ArrayList<>();
		for (Level level : levels) {
			frameOutputs.add(fromLevel(level));
		}
		return frameOutputs;
	}
}
