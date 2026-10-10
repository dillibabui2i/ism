package com.ideas2it.ism.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.ism.common.Constant;
import com.ideas2it.ism.common.Department;
import com.ideas2it.ism.common.Result;
import com.ideas2it.ism.common.Technology;
import com.ideas2it.ism.dao.CandidateDAO;
import com.ideas2it.ism.common.CandidateStatus;
import com.ideas2it.ism.common.Department;
import com.ideas2it.ism.common.Result;
import com.ideas2it.ism.common.Technology;
import com.ideas2it.ism.dao.CandidateRepository;
import com.ideas2it.ism.entity.Candidate;
import com.ideas2it.ism.exception.IsmException;
import com.ideas2it.ism.info.CandidateFormInfo;
import com.ideas2it.ism.info.CandidatePagenationInfo;
import com.ideas2it.ism.service.CandidateService;
import com.ideas2it.ism.service.ScheduleService;
import com.ideas2it.ism.util.CalculatePage;



/**
 * All the request passed from jsp are received at controller,
 * the action between controller and DAO layer is controlled here
 * any modifications , logics required are performed here.
 * 
 * @author M.Mani Bharathi.
 *
 */
@Service
public class CandidateServiceImpl implements CandidateService {
	
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateDAO candidateDAO;
    @Autowired
    private ScheduleService scheduleService;
    private final String UPLOAD_DIRECTORY = "/home/ubuntu/resume/";
    private final String PROFILE_PIC_PATH = "http://localhost:8080/resume/";
    
	public CandidateFormInfo getCandidateFormInfo() {
		CandidateFormInfo candidateFormInfo = new CandidateFormInfo();
		List<Department> departments = new ArrayList<Department>(Arrays.asList(Department.values()));
		List<Technology> technologies = new ArrayList<Technology>(Arrays.asList(Technology.values()));
		Candidate candidate = new Candidate();
		candidateFormInfo.setCandidate(candidate);
		candidateFormInfo.setDepartments(departments);
		candidateFormInfo.setTechnologies(technologies);
		return candidateFormInfo;
	}
   
	public Candidate saveCandidate(Candidate candidate, MultipartFile resume) 
			throws IOException {
		System.out.println(candidate);
		candidate = saveCandidateResume(candidate, resume);
		return candidateRepository.save(candidate);
	}
	
    public Candidate fetchCandidateById(long candidateId) {   
    	return candidateRepository.getOne(candidateId);
    }
    
    public Candidate getCandidateProgress(long candidateId) {
    	Candidate candidate = candidateRepository.getOne(candidateId);
    	candidate.setSchedules(scheduleService.fetchSchedulesByCandidateId(candidateId));
		return candidate;   	
    }
    
	public CandidatePagenationInfo getPagenationInfo() throws IsmException {
		CandidatePagenationInfo pagenationInfo = new CandidatePagenationInfo();
		int count = (int) candidateRepository.count();
		pagenationInfo.setCandidates(candidateDAO.fetchCandidatesByLimit(0));
		pagenationInfo.setTotalCount(count);
		List<Result> results = new ArrayList<Result>(Arrays.asList(Result.values()));
		pagenationInfo.setResults(results);
		if (0 != count) {
		    List<Integer> pages = CalculatePage.calculatePages(count, Constant.RETRIEVE_LIMIT); 
		    pagenationInfo.setPages(pages);
		    int lastPage = pages.get(pages.size() - 1); 
		    pagenationInfo.setLastPageNo(lastPage);
		}
		pagenationInfo.setStatus(Result.New);
		return pagenationInfo;
	}
	
	public CandidatePagenationInfo searchByName(String name) {
		CandidatePagenationInfo pagenationInfo = new CandidatePagenationInfo();
		int count = (int) candidateRepository.count();
		pagenationInfo.setCandidates(candidateRepository.findCandidateByName(name));
		List<Result> results = new ArrayList<Result>(Arrays.asList(Result.values()));
		pagenationInfo.setTotalCount(count);
		pagenationInfo.setResults(results);
		return pagenationInfo;	
	}
	
	public CandidatePagenationInfo searchByStatus(Result status) throws IsmException {
		CandidatePagenationInfo pagenationInfo = new CandidatePagenationInfo();
		int count = this.totalCount(status);
		pagenationInfo.setCandidates(fetchCandidatesByStatus(0, status));
		List<Result> results = new ArrayList<Result>(Arrays.asList(Result.values()));
		if (0 != count) {
		    List<Integer> pages = CalculatePage.calculatePages(count, Constant.RETRIEVE_LIMIT); 
		    pagenationInfo.setPages(pages);
		    int lastPage = pages.get(pages.size() - 1); 
		    pagenationInfo.setLastPageNo(lastPage);
		    pagenationInfo.setTotalCount(count);
		}
		pagenationInfo.setResults(results);
		pagenationInfo.setStatus(status);
		return pagenationInfo;	
	}

	@Override
	public void updateCandidateStatus(long candidateId, Result status) {
		Candidate candidate = candidateRepository.getOne(candidateId);
		candidate.setStatus(status);
		candidateRepository.save(candidate);
	}

	@Override
	public Candidate updateCandidate(Candidate candidate, MultipartFile resume) throws IOException {
		candidate = saveCandidateResume(candidate, resume);
		Candidate candidateToBeUpdated = candidateRepository.getOne(candidate.getId());
		candidate.setSchedules(candidateToBeUpdated.getSchedules());
		candidate.setStatus(candidateToBeUpdated.getStatus());
		return candidateRepository.save(candidate);
	}

	@Override
	public JSONArray retrieveAllCandidates(int pageNo, Result candidateResult) throws IsmException {
        List<Candidate> candidates  = this.fetchCandidatesByStatus(pageNo, candidateResult);
        JSONArray candidatesInfo = new JSONArray();
        for (Candidate candidate : candidates) { 
            JSONObject candidateInfo = new JSONObject();
            candidateInfo.put(Constant.CANDIDATE_ID, candidate.getId());
            candidateInfo.put(Constant.CANDIDATE_NAME, candidate.getName());
            candidateInfo.put(Constant.POSITION, candidate.getPosition());
            candidateInfo.put(Constant.DEPARTMENT, candidate.getDepartment());
            candidateInfo.put(Constant.EXPERIENCE, candidate.getExperience());
            candidateInfo.put(Constant.STATUS, candidate.getStatus());
            candidatesInfo.put(candidateInfo);
        }
        return candidatesInfo;
	}

	@Override
	public List<Candidate> getCandidatesByStatus(CandidateStatus status) {
		return candidateRepository.fetchCandidatesByStatus(status);
	}

	@Override
	public Candidate uploadResume(long candidateId, MultipartFile resume) throws IOException, IsmException {
		Optional<Candidate> existingCandidate = candidateRepository.findById(candidateId);
		if (!existingCandidate.isPresent()) {
			throw new IsmException(Constant.CANDIDATE_NOT_FOUND);
		}
		Candidate candidate = saveCandidateResume(existingCandidate.get(), resume);
		return candidateRepository.save(candidate);
	}
    
    /**
     * List of all candidate object is fetched from DB.
     * 
     * @param noOfRecords - Limit of records to be fetched.
     * @return candidates - List of entities fetched from DB.
     * @throws IsmException - Thrown when a hibernate exception occurs while retrieving
     * candidates details from DB. 
     */
    private List<Candidate> fetchCandidatesByStatus(int pageNo, Result candidateResult)
    		throws IsmException {   
        pageNo = pageNo - 1;
        pageNo = pageNo * Constant.RETRIEVE_LIMIT; 
        return candidateDAO.fetchCandidatesByStatus(pageNo, candidateResult);
    }
    
    /**
	 * get Total count of records for the particular status within DB.
	 * 
	 * @param status - Candidates count corresponding status is required.
	 * @return totalCount - Count of candidates present.
     * @throws IsmException - Thrown when a hibernate exception occurs while counting
     * candidates details from DB. 
     */
    private int totalCount(Result status) throws IsmException {   
    	return candidateDAO.getTotalCount(status);
    }
    
	
    /**
     * Resume uploaded by the recruiter is passed as multipart file then it is converted as
     * bytes and stored in the local directory and the path is stored in DB. To save the
     * name unique for each candidate the emailId is concatenated with resume name.
     * 
     * @param candidate - Object in which the path to be saved.
	 * @param resume - Resume uploaded by the recruiter is saved to the local 
	 * directory and the path is saved in the DB. 
     * @return candidate - Path updated object to be stored.
     * @throws IOException
     */
    private Candidate saveCandidateResume(Candidate candidate, MultipartFile resume)
            throws IOException {   
        String filename = resume.getOriginalFilename();      
        byte[] bytes = resume.getBytes();  
        BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(  
                new File(UPLOAD_DIRECTORY + File.separator + candidate.getEmailId() + filename)));  
        stream.write(bytes);  
        stream.flush();  
        stream.close();  
        if (!(resume.getOriginalFilename().isEmpty())) {
            String path = PROFILE_PIC_PATH + candidate.getEmailId() + resume.getOriginalFilename();
            candidate.setResumeFilePath(path);
        }
        return candidate;
    }
}
