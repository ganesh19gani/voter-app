package com.voter.app.repo;

import com.voter.app.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Voter findByVoterIdEpicNumber(String voterId);

    List<Voter> findByVoterBoothName(String userBoothName);

    @Query(value = "select distinct voter_booth_name from voter_list where constituency =?1",nativeQuery = true )
   List<Map<String, Object>> getBoothNamesByConstituency(String constituencyName);

    @Query(value = "select * from  voter_list where actual!=\"\";",nativeQuery = true )
    List<Map<String, Object>> getVoted();

    @Query(value = "select * from  voter_list where actual=\"\";",nativeQuery = true )
    List<Map<String, Object>> getUnVoted();

    @Query(value = "select distinct constituency from voter_list;",nativeQuery = true )
    List<Map<String, Object>> getAllConstituencies();


    @Query(value = "select * from voter_list where constituency =?1 and voter_booth_name=?2 and actual=\"\";",nativeQuery = true )
    List<Map<String, Object>> findByConstituencyAndBoothNameAndUnVoted(String constituency, String boothName);

    @Query(value = "select * from voter_list where constituency =?1 and voter_booth_name=?2 and actual!=\"\";",nativeQuery = true )
    List<Map<String, Object>> findByConstituencyAndBoothNameAndVoted(String constituency, String boothName);
}
