import { 
    APPLICATION_LOAD_FAIL, 
    APPLICATION_LOAD_REQUEST, 
    APPLICATION_LOAD_RESET, 
    APPLICATION_LOAD_SUCCESS, 
    LOAD_APPLICANT_APPLICATION_FAIL, 
    LOAD_APPLICANT_APPLICATION_REQUEST,
    LOAD_APPLICANT_APPLICATION_RESET,
    LOAD_APPLICANT_APPLICATION_SUCCESS
} from "../constants/applicationConstant"

export const loadApplicationReducer = (state={applications:[]}, action) => {
    switch (action.type) {
        case APPLICATION_LOAD_REQUEST:
            return {loading:true}
        case APPLICATION_LOAD_SUCCESS:
            return {
                loading: false,
                totalItems: action.totalItems,
                applications: action.payload
            }
        case APPLICATION_LOAD_FAIL:
                return {
                    loading: false,
                    error: action.payload
                }    
        case APPLICATION_LOAD_RESET:
            return {}
        default:
            return state;
    }
}


export const loadApplicantApplicationsReducer = (state = { applications:[]}, action) => {
    switch (action.type) {
        case LOAD_APPLICANT_APPLICATION_REQUEST:
            return { loading: true }
        case LOAD_APPLICANT_APPLICATION_SUCCESS:
            return {
                loading: false,
                applications: action.payload
            }
        case LOAD_APPLICANT_APPLICATION_FAIL:
            return { loading: false
            }
        case LOAD_APPLICANT_APPLICATION_RESET:
            return {}
        default:
            return state;
    }

}