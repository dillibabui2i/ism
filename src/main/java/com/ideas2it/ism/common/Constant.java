package com.ideas2it.ism.common;



/**
 * The members of this class remains the same all over the project. 
 */ 
public class Constant {
	
	// General Constants.
    public static final String STATUS = "status";
	public static final String RESULT = "result";
	public static final String SCHEDULE = "schedule";
	public static final String SELECTED = "selected";
	public static final String FEED_BACK = "feedback";
    public static final  String CREATED = "created";
	public static final String UPDATED = "updated";	
	public static final String ACTION = "action";	
	public static final String UPDATE = "update";	
    public static final  String ID = "id";
    public static final  String NAME = "name";
	public static final String RESUME = "resume";
	public static final String PAGE_NO = "pageNo";
	public static final int RETRIEVE_LIMIT = 5;
	public static final String APPLICATION_JSON = "application/json";
    
	// Candidate constants.
    public static final  String CANDIDATE = "candidate";
	public static final String REDIRECT = "redirect:/";
	public static final String CANDIDATE_NAME = "candidateName";
	public static final String POSITION = "position";
	public static final String DEPARTMENT = "department";
	public static final String EXPERIENCE = "experience";
	public static final String CANDIDATE_FORM_INFO = "candidateFormInfo";
	public static final String PAGENATION_INFO = "pagenationInfo";
	public static final String CANDIDATE_ID = "candidateId";
	public static final String CANDIDATES = "candidate";
	public static final String RESUME_FILE_PATH = "resumeFilePath";
	public static final String CANDIDATE_NOT_FOUND = "Candidate not found for the given id";
	public static final String RESUME_REQUIRED = "Resume file is required";
    
    // Candidate mapping constants.
    public final static String ADD_CANDIDATE = "addCandidate";
    public final static String SAVE_CANDIDATE = "saveCandidate";
    public final static String UPLOAD_RESUME = "uploadResume";
	public static final String VIEW_CANDIDATES = "viewCandidates";
	public static final String SEARCH_BY_NAME = "searchByName";
    public static final String CREATE_CANDIDATE_JSP = "createCandidate";
	public static final String INDEX_JSP = "index";
	public static final String VIEW_CANDIDATES_JSP = "viewCandidates";
	public static final String VIEW_CANDIDATE_JSP = "viewCandidate";
	public static final String VIEW_NEW_SCHEDULES_JSP = "viewNewSchedules";
	public static final String VIEW_PENDING_SCHEDULES_JSP = "viewPendingSchedules";
	public static final String GET_CANDIDATES_BY_STATUS = "getCandidatesByStatus";

	// Employee constants.	
	public static final String EMPLOYEE = "employee";
	
	// Schedule constants.
	public static final String SCHEDULES = "schedules";
	public static final String SCHEDULED_DATE = "shdate";
	public static final String SCHEDULED_TIME = "shtime";
	public static final String SECONDS = ":00";
	public static final String MESSAGE = "message";
	public static final String SCHEDULE_ID = "scheduleId";
	public static final String TYPES = "types";
	public static final String LEVELS = "levels";
	public static final String SCHEDULE_STATUS = "scheduleStatus";
	public static final String CANDIDATE_STATUS = "candidateStatus";
	public static final String CANDIDATE_STATUSES = "candidateStatuses";
	public static final String RESCHEDULE_COMMENT = "rescheduleComment";
	public static final String CANCEL_COMMENT = "cancellationComment";
	public static final String COMMENT = "comment";
	public static final String NEW_SCHEDULE = "newSchedule";
	public static final String INTERVIEWERS = "interviewers";
	public static final String ASSIGN_INTERVIEWER = "assignInterviewer";
	public static final String INTERVIEWER_ID = "interviewerId";
	public static final String REJECT_SCHEDULE = "rejectSchedule";

    // Schedule JSP pages.
	public static final String VIEW_SCHEDULES_JSP = "viewSchedules";
	public static final String VIEW_SCHEDULE_JSP = "viewSchedule";
	public static final String CREATE_SCHEDULE_JSP = "createSchedule";
	public static final String CREATE_RESCHEDULE_JSP = "createReschedule";
	public static final String RECRUITER_JSP = "recruiter";
	public static final String ASSIGN_INTERVIEWER_JSP = "assignInterviewer";


    // Schedule mapping constants.
	public static final String SCHEDULE_FORM = "scheduleForm";
	public static final String CREATE_SCHEDULE = "createSchedule";
	public static final String SCHEDULE_BY_STATUS = "schedulesByStatus";
	public static final String VIEW_PROGRESS = "viewProgress";
	public static final String GET_SCHEDULE = "getSchedule";
	public static final String NEW_SCHEDULES = "newSchedules";
	public static final String PENDING_SCHEDULES = "pendingSchedules";
	public static final String ACCEPT_SCHEDULE = "acceptSchedule";
	public static final String UPDATE_CANDIDATE = "updateCandidate";
	public static final String VIEW_CANDIDATE_FOR_UPDATE = "viewCandidateForUpdate";
	public static final String INTERVIEW_RESULT = "interviewResult";
	public static final String RESCHEDULE = "reschedule";
	public static final String CANCEL_SCHEDULE = "cancelSchedule";
	public static final String GET_RECRUITER_OPERATIONS = "getRecruiterOperations";
	public static final String GET_SCHEDULE_WITH_INTERVIEWERS = "getScheduleWithInterviewers";
	public static final String NO_OF_RECORDS = "noOfRecords";
	public static final String SEARCH_BY_FIELDS = "searchByFields";
	public static final String SEARCH_BY_STATUS = "searchByStatus";
	public static final String VIEW_ALL_CANDIDATES = "viewAllCandidates";
	public static final String VIEW_SCHEDULES_BY_STATUS = "viewSchedulesByStatus";
	public static final String VIEW_SCHEDULES = "viewSchedules";
	
	// Error Constants.
	public static final String ERROR_RETRIEVING_CANDIDATES = "Error while fetching the Candidiate details";
	public static final String INDEX = "index";

	// Admin login constants.
	public static final String LOGIN = "login";
	public static final String LOGIN_JSP = "login";

	// User registration constants.
	public static final String USER = "user";
	public static final String REGISTER = "register";
	public static final String REGISTER_JSP = "register";
	public static final String REGISTRATION_SUCCESS = "registrationSuccess";
	public static final String ERROR = "error";
	public static final String USERNAME_ALREADY_REGISTERED = "This username/email is already registered.";
}

   
