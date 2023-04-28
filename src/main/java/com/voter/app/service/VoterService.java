package com.voter.app.service;

import com.voter.app.model.AppUsers;
import com.voter.app.model.Parties;
import com.voter.app.model.Voter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VoterService {

    Parties createParties(Parties parties);

    Map<String, Object> getAllParties();

    Map<String, String>  createUser(AppUsers users);


    Map<String, Object> login(String userId, String password);

    Map<String, String>  createVoter(Voter voter);

    Map<String, String> updateVoterByVoterId(Voter voterDetails);

    Map<String, Object> getVoterByBoothName(String userId);

    Map<String, Object> findByVoterId(String voterId);

    Map<String, Object> getAllVoters();

    Parties saveParty(MultipartFile file, String partyName, String pabv) throws IOException;


    Map<String, Object> getByBoothName(String boothName);

    Map<String, Object> getBoothNamesByConstituency(String constituencyName);

    Map<String, Object> getVoted();

    Map<String, Object> getUnVoted();

    void importData(MultipartFile file) throws IOException;

    Map<String, Object> getAllConstituencies();

    Map<String, Object> getAllVotersByFilter(String constituency, String boothName,String statusOfVoter);
}
