package com.voter.app.controller;

import com.voter.app.model.AppUsers;
import com.voter.app.model.LoginRequest;
import com.voter.app.model.Parties;
import com.voter.app.model.Voter;
import com.voter.app.service.VoterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voter")
@Api(value = "Voter Management System", description = "Operations pertaining to user in Voter Management System")
@Validated
public class VoterController {

    @Autowired
    private VoterService voterService;

    @PostMapping("/register")
    public Map<String, String> createUser( @RequestBody @Valid AppUsers users) {
        return voterService.createUser(users);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return voterService.login(loginRequest.getUserId(), loginRequest.getPassword());
    }

    @PostMapping("/createVoter")
    public Map<String, String> createVoter(@RequestBody Voter voter) {
        return voterService.createVoter(voter);
    }

    @PatchMapping("/updateVoter")
    public Map<String, String> updateVoterByVoterId(@RequestBody Voter voterDetails) {
        return voterService.updateVoterByVoterId(voterDetails);
    }


    @GetMapping("/findByVoterId")
    public Map<String, Object> findByVoterId(@RequestParam("voterId") String voterId) {
        return voterService.findByVoterId(voterId);
    }

    @GetMapping("/getVoterByBoothName")
    public Map<String, Object> getVoterByBoothName(@RequestParam("userId") String userId) {
        return voterService.getVoterByBoothName(userId);
    }

    @GetMapping("/getAllVoters")
    public Map<String, Object> getAllVoters() {
        return voterService.getAllVoters();
    }


    @PostMapping("/createParties")
    public Parties saveParty(@RequestParam("logo") MultipartFile file, @RequestParam("partyName") String partyName, @RequestParam("pabv") String pabv) throws IOException {
        Parties parties = voterService.saveParty(file, partyName, pabv);
        return parties;
    }

    @GetMapping("/getAllParties")
    public Map<String, Object> getAllParties() {
        return voterService.getAllParties();
    }

    @GetMapping("/getByBoothName")
    public Map<String, Object> getByBoothName(@RequestParam("boothName") String boothName) {
        return voterService.getByBoothName(boothName);
    }

    @GetMapping("/getBoothNamesByConstituency")
    public Map<String, Object> getBoothNamesByConstituency(@RequestParam("constituencyName") String constituencyName) {
        return voterService.getBoothNamesByConstituency(constituencyName);
    }

//    @GetMapping("/getVoted")
//    public Map<String, Object> getVoted() {
//        return voterService.getVoted();
//    }
//
//    @GetMapping("/getUnVoted")
//    public Map<String, Object> getUnVoted() {
//        return voterService.getUnVoted();
//    }


    @GetMapping("/getAllConstituencies")
    public Map<String, Object> getAllConstituencies() {
        return voterService.getAllConstituencies();
    }

    @GetMapping("/getAllVotersByFilter")
    public Map<String, Object> getAllVotersByFilter(@RequestParam("constituency") String constituency,
                                                    @RequestParam("boothName") String boothName,
                                                    @RequestParam("statusOfVoter") String statusOfVoter) {
        return voterService.getAllVotersByFilter(constituency, boothName, statusOfVoter);
    }
    @PostMapping("/importVoters")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) {
        try {
            voterService.importData(file);
            return new ResponseEntity<>("Data imported successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to import data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/importAgents")
    public ResponseEntity<String> importAgentsData(@RequestParam("file") MultipartFile file) {
        try {
            voterService.importAgentsData(file);
            return new ResponseEntity<>("Data imported successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to import data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
