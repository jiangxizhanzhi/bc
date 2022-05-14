package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.evaluation.AlgorithmContext;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;

import java.io.Serializable;
import java.util.List;

public interface RemotePythonService {
    String getPyHost();

    List<String> formatPerform(AlgorithmContext context,
                               String algorithmId,
                               String algorithmCode,
                               FormatData formatData,
                               List<Serializable> otherArguments);

    List<Integer> matchPerform(AlgorithmContext context,
                               String algorithmId,
                               String algorithmCode,
                               MatchData matchData,
                               List<Serializable> otherArguments);

    Integer markPerform(AlgorithmContext context,
                        String algorithmId,
                        String algorithmCode,
                        MarkData markData,
                        List<Serializable> otherArguments);

    Integer indexPerform(AlgorithmContext context,
                         String algorithmId,
                         String algorithmCode,
                         List<Integer> data,
                         List<Serializable> otherArguments);
}
