package com.voter.app.service.impl;


import com.voter.app.model.AppUsers;
import com.voter.app.model.Parties;
import com.voter.app.model.Voter;
import com.voter.app.repo.AppUserRepository;
import com.voter.app.repo.PartiesRepository;
import com.voter.app.repo.VoterRepository;
import com.voter.app.service.VoterService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class VoterServiceImpl implements VoterService {
    @Autowired
    private PartiesRepository partiesRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Override
    public Parties createParties(Parties parties) {
        return partiesRepository.save(parties);
    }

    @Override
    public Map<String, Object> getAllParties() {
        HashMap<String, Object> response = new HashMap<>();
        List<Parties> listOfParties = partiesRepository.findAll();
        if (listOfParties != null) {
            response.put("status", true);
            response.put("message", "Parties Availabe");
            response.put("result", listOfParties);
            return response;
        }
        response.put("status", false);
        response.put("message", "Parties are Not Available");
        response.put("result", Collections.emptyList());

        return response;
    }

    @Override
    public Map<String, String> createUser(AppUsers users) {

        HashMap<String, String> hmap = new HashMap<>();
        String salt = BCrypt.gensalt();

        String hashedPassword = BCrypt.hashpw(users.getUserPassword(), salt);

        AppUsers appUsers = new AppUsers();
        appUsers.setUserName(users.getUserName());
        appUsers.setUserRole(users.getUserRole());
        appUsers.setEmail(users.getEmail());
        appUsers.setUserBoothNumber(users.getUserBoothNumber());
        appUsers.setUserBoothName(users.getUserBoothName());
        appUsers.setUserPhoneNumber(users.getUserPhoneNumber());
        appUsers.setUserPassword(hashedPassword);
        appUsers.setUserAffiliatedParty(users.getUserAffiliatedParty());


        AppUsers finalUser = appUserRepository.save(appUsers);

        hmap.put("result", String.format("%s Registration completed successfully.", finalUser.getUserName()));
        return hmap;
    }

    @Override
    public Map<String, Object> login(String userId, String password) {


        HashMap<String, Object> hmap = new HashMap<>();
        AppUsers existingDetails = appUserRepository.findByEmail(userId);

        if (existingDetails != null) {
            if (BCrypt.checkpw(password, existingDetails.getUserPassword())) {
                List<Voter> voters = getVoters(userId);

                hmap.put("status", true);
                hmap.put("message", "User Availabe");
                hmap.put("result", existingDetails);
                //  hmap.put("userRole",existingDetails.getUserRole());
                return hmap;
            } else {
                hmap.put("status", false);
                hmap.put("message", "Incorrect Password");
                hmap.put("result", null);
                hmap.put("userRole", null);
                return hmap;
            }

        }
        hmap.put("status", false);
        hmap.put("message", "User Not Found");
        hmap.put("result", null);
        hmap.put("userRole", null);
        return hmap;
    }

    public List<Voter> getVoters(String userId) {
        AppUsers userDetails = appUserRepository.findByEmail(userId);
        if (userDetails != null) {
            List<Voter> filteredData = voterRepository.findByVoterBoothName(userDetails.getUserBoothName());
            return filteredData;
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, String> createVoter(Voter voter) {

        HashMap<String, String> hmap = new HashMap<>();

        Voter voterFromDb = voterRepository.save(voter);
        if (voterFromDb != null) {
            hmap.put("result", String.format("%s Registration completed successfully.", voterFromDb.getVoterName()));
            return hmap;
        }
        hmap.put("result", String.format("%s Registration not completed successfully.", voterFromDb.getVoterName()));
        return hmap;
    }

    @Override
    public Map<String, String> updateVoterByVoterId(Voter voterDetails) {

        HashMap<String, String> hmap = new HashMap<>();
        Voter existingVoterDetails = voterRepository.findByVoterIdEpicNumber(voterDetails.getVoterIdEpicNumber());
        if (existingVoterDetails != null) {
            existingVoterDetails.setVoterIdEpicNumber(voterDetails.getVoterIdEpicNumber());
            existingVoterDetails.setActual(voterDetails.getActual());

            Voter finalSave = voterRepository.save(existingVoterDetails);

            hmap.put("result", "Updated Successfully.");
            return hmap;
        }
        hmap.put("result", "User Not Found");
        return hmap;
    }

    @Override
    public Map<String, Object> getVoterByBoothName(String userId) {
        HashMap<String, Object> hmap = new HashMap<>();
        AppUsers userDetails = appUserRepository.findByEmail(userId);
        if (userDetails != null) {
            List<Voter> filteredData = voterRepository.findByVoterBoothName(userDetails.getUserBoothName());
            hmap.put("status", true);
            hmap.put("message", "BoothName Available");
            hmap.put("result", filteredData);
            return hmap;
        }
        hmap.put("status", false);
        hmap.put("message", "BoothName Not Available");
        hmap.put("result", Collections.emptyList());
        return hmap;
    }

    @Override
    public Map<String, Object> findByVoterId(String voterId) {
        HashMap<String, Object> hmap = new HashMap<>();
        Voter voterInformation = voterRepository.findByVoterIdEpicNumber(voterId);
        if (voterInformation != null) {

            hmap.put("status", true);
            hmap.put("message", "Voter Available");
            hmap.put("result", voterInformation);
            return hmap;
        }
        hmap.put("status", false);
        hmap.put("message", "Voter Not Available");
        hmap.put("result", voterInformation);
        return hmap;
    }

    @Override
    public Map<String, Object> getAllVoters() {

        HashMap<String, Object> response = new HashMap<>();

        List<Voter> listOfVoters = voterRepository.findAll();
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "Voters are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "Voters are Not Available");
        response.put("result", Collections.emptyList());
        return response;
    }

    @Override
    public Parties saveParty(MultipartFile file, String partyName, String pabv) throws IOException {

        String base64 = Base64.getEncoder().encodeToString(file.getBytes());
        Parties party = new Parties();
        party.setPname(partyName.toUpperCase());
        party.setPabv(pabv);
        party.setPlogo(base64);
        Parties parties = partiesRepository.save(party);
        return parties;

    }

    @Override
    public Map<String, Object> getByBoothName(String boothName) {

        HashMap<String, Object> response = new HashMap<>();
        List<Voter> listOfVoters = voterRepository.findByVoterBoothName(boothName);
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "Voters are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "Voters are Not Available");
        response.put("result", Collections.emptyList());
        return response;

    }

    @Override
    public Map<String, Object> getBoothNamesByConstituency(String constituencyName) {
        List<Map<String, Object>> listOfVoters = voterRepository.getBoothNamesByConstituency(constituencyName);
        HashMap<String, Object> response = new HashMap<>();
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "BoothNames are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "BoothNames are Not Available");
        response.put("result", Collections.emptyList());
        return response;

    }

    @Override
    public Map<String, Object> getVoted() {
        List<Map<String, Object>> listOfVoters = voterRepository.getVoted();
        HashMap<String, Object> response = new HashMap<>();
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "Voters are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "Voters are Not Available");
        response.put("result", Collections.emptyList());
        return response;

    }

    @Override
    public Map<String, Object> getUnVoted() {
        List<Map<String, Object>> listOfVoters = voterRepository.getUnVoted();
        HashMap<String, Object> response = new HashMap<>();
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "Voters are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "Voters are Not Available");
        response.put("result", Collections.emptyList());
        return response;
    }

    @Override
    public void importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Voter data = new Voter();
            //data.setVid();
            String voterId = String.valueOf(row.getCell(0).getNumericCellValue()).substring(0, String.valueOf(row.getCell(0).getNumericCellValue()).length() - 2);
            data.setVoterIdEpicNumber(voterId);
            data.setVoterName(row.getCell(1).getStringCellValue());
            data.setVoterAddress(row.getCell(2).getStringCellValue());
            data.setConstituency(row.getCell(3).getStringCellValue());
            String voterBoothNumber = String.valueOf(row.getCell(4).getNumericCellValue()).substring(0, String.valueOf(row.getCell(4).getNumericCellValue()).length() - 2);
            data.setVoterBoothNumber(voterBoothNumber);
            data.setVoterBoothName(row.getCell(5).getStringCellValue());
            data.setVoterGender(row.getCell(6).getStringCellValue());
            data.setExpected(row.getCell(7).getStringCellValue());
            data.setActual("");

            voterRepository.save(data);
        }
        workbook.close();
        inputStream.close();
    }

    @Override
    public Map<String, Object> getAllConstituencies() {
        List<Map<String, Object>> listOfVoters = voterRepository.getAllConstituencies();
        HashMap<String, Object> response = new HashMap<>();
        if (!listOfVoters.isEmpty()) {
            response.put("status", true);
            response.put("message", "Constituencies are Available");
            response.put("result", listOfVoters);
            return response;
        }
        response.put("status", false);
        response.put("message", "Constituencies are Not Available");
        response.put("result", Collections.emptyList());
        return response;
    }

    @Override
    public Map<String, Object> getAllVotersByFilter(String constituency, String boothName, String statusOfVoter) {

            if (constituency != null && constituency != "" && !constituency.isEmpty() && boothName != "" && boothName != null && !boothName.isEmpty() && statusOfVoter != "" &&statusOfVoter != null && !statusOfVoter.isEmpty()) {
                if (statusOfVoter.equalsIgnoreCase("UNVOTED")) {
                    List<Map<String, Object>> listOfVoters = voterRepository.findByConstituencyAndBoothNameAndUnVoted(constituency, boothName);
                    HashMap<String, Object> response = new HashMap<>();
                    if (!listOfVoters.isEmpty()) {
                        response.put("status", true);
                        response.put("message", "Voters are Available");
                        response.put("result", listOfVoters);
                        return response;
                    }
                    response.put("status", false);
                    response.put("message", "Voters are Not Available");
                    response.put("result", Collections.emptyList());
                    return response;

                } else {
                    List<Map<String, Object>> listOfVoters = voterRepository.findByConstituencyAndBoothNameAndVoted(constituency, boothName);
                    HashMap<String, Object> response = new HashMap<>();
                    if (!listOfVoters.isEmpty()) {
                        response.put("status", true);
                        response.put("message", "Voters are Available");
                        response.put("result", listOfVoters);
                        return response;
                    }
                    response.put("status", false);
                    response.put("message", "Voters are Not Available");
                    response.put("result", Collections.emptyList());
                    return response;
                }


            } else if(statusOfVoter!="" && statusOfVoter!=null && !statusOfVoter.isEmpty()){
                    List<Map<String, Object>> listOfVoters = null;
                    if(statusOfVoter.equalsIgnoreCase("VOTED")) {
                        listOfVoters = voterRepository.getVoted();
                    } else {
                        listOfVoters = voterRepository.getUnVoted();
                    }
                    HashMap<String, Object> response = new HashMap<>();
                    if (!listOfVoters.isEmpty()) {
                        response.put("status", true);
                        response.put("message", "Voters are Available");
                        response.put("result", listOfVoters);
                        return response;
                    }
                    response.put("status", false);
                    response.put("message", "Voters are Not Available");
                    response.put("result", Collections.emptyList());
                    return response;

            }
            List<Map<String, Object>> listOfVoters = voterRepository.findAllFromVoter();
            HashMap<String, Object> response = new HashMap<>();
            if (!listOfVoters.isEmpty()) {
                response.put("status", true);
                response.put("message", "Voters are Available");
                response.put("result", listOfVoters);
                return response;
            }
            response.put("status", false);
            response.put("message", "Voters are Not Available");
            response.put("result", Collections.emptyList());
            return response;
        }
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void importAgentsData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            AppUsers data = new AppUsers();
            data.setUserName(row.getCell(0).getStringCellValue());
            data.setUserRole(row.getCell(1).getStringCellValue());
           // System.out.println("Email" +row.getCell(1).getStringCellValue() );
            data.setEmail(row.getCell(2).getStringCellValue());
            data.setConstituency(row.getCell(3).getStringCellValue());
            String voterBoothNumber = String.valueOf(row.getCell(4).getNumericCellValue()).substring(0, String.valueOf(row.getCell(4).getNumericCellValue()).length() - 2);
            data.setUserBoothNumber(voterBoothNumber);
            data.setUserBoothName(row.getCell(5).getStringCellValue());

            String phoneNumber = String.valueOf(row.getCell(6).getNumericCellValue()).substring(0, String.valueOf(row.getCell(6).getNumericCellValue()).length() - 2);
            data.setUserPhoneNumber(phoneNumber);

            int getRandomNumber = getRandom();
            String generatedPassword = row.getCell(0).getStringCellValue().charAt(0)+String.valueOf(getRandomNumber);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("adapaganeshofficial@gmail.com");
            simpleMailMessage.setTo(row.getCell(2).getStringCellValue());
            simpleMailMessage.setText("Agent please find your password for login inside Voter app : \n"+"UserName : "+row.getCell(2).getStringCellValue()+"\n"+"Password : "+generatedPassword);
            simpleMailMessage.setSubject("Agent Created - Credentials");
            javaMailSender.send(simpleMailMessage);

            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(generatedPassword, salt);

            data.setUserPassword(hashedPassword);
            data.setUserAffiliatedParty(row.getCell(7).getStringCellValue());


             appUserRepository.save(data);
        }
        workbook.close();
        inputStream.close();
    }

public int getRandom(){
    Random rand = new Random();
    int randomNum =0;
    for (int i = 0; i < 10; i++) {
        randomNum  = rand.nextInt(900) + 100; // generates a random integer between 100 and 999
        // System.out.println(randomNum);
    }
    return randomNum;
}
}
