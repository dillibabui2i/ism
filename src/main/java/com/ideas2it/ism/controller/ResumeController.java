package com.ideas2it.ism.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.ism.common.Constant;
import com.ideas2it.ism.entity.Candidate;
import com.ideas2it.ism.exception.IsmException;
import com.ideas2it.ism.service.CandidateService;

/**
 * Allows a resume to be uploaded or replaced for an existing candidate,
 * identified by id, independent of the create/update candidate form.
 *
 */
@Controller
public class ResumeController {

    @Autowired
    private CandidateService candidateService;

    /**
     * Shows the standalone resume upload/replace form for an existing
     * candidate.
     *
     * @return UPLOAD_RESUME_JSP - Page that shows the resume upload form.
     */
    @RequestMapping(value = Constant.UPLOAD_RESUME_FORM, method = RequestMethod.GET)
    private String uploadResumeForm() {
        return Constant.UPLOAD_RESUME_JSP;
    }

    /**
     * Stores the resume uploaded for the given candidate id and updates the
     * candidate's resumeFilePath. Writes a JSON response indicating success
     * or failure.
     *
     * @param candidateId - Id of the existing candidate whose resume is to be stored.
     * @param resume - Resume file uploaded to be stored against the candidate.
     * @param response - Response to server from servlet.
     * @throws IOException - general class of exceptions produced by failed or
     * interrupted I/O operations.
     */
    @RequestMapping(value = Constant.UPLOAD_RESUME, method = RequestMethod.POST)
    private void uploadResume(@RequestParam(name = Constant.CANDIDATE_ID) long candidateId,
            @RequestParam(name = Constant.RESUME) MultipartFile resume,
            HttpServletResponse response) throws IOException {
        JSONObject responseBody = new JSONObject();
        response.setContentType(Constant.APPLICATION_JSON);
        if (resume.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put(Constant.MESSAGE, Constant.RESUME_REQUIRED);
            response.getWriter().write(responseBody.toString());
            return;
        }
        try {
            Candidate candidate = candidateService.uploadResume(candidateId, resume);
            responseBody.put(Constant.STATUS, Constant.UPDATED);
            responseBody.put(Constant.RESUME_FILE_PATH, candidate.getResumeFilePath());
            response.getWriter().write(responseBody.toString());
        } catch (IsmException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBody.put(Constant.MESSAGE, e.getMessage());
            response.getWriter().write(responseBody.toString());
        }
    }
}
