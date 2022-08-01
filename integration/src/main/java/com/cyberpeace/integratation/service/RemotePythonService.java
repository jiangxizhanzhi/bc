package com.cyberpeace.integratation.service;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;

public interface RemotePythonService {

	String getPyHost();

	List<String> formatPerform(String algorithmId, String algorithmCode, FormatData formatData,
			List<Serializable> otherArguments);

	List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData,
			List<Serializable> otherArguments);

	Integer markPerform(String algorithmId, String algorithmCode, MarkData markData,
			List<Serializable> otherArguments);

	Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments);
}
