import { ADD_BLACKLIST_REQUEST, ADD_BLACKLIST_REQUEST_FAIL, ADD_BLACKLIST_REQUEST_RESET, 
    ADD_BLACKLIST_REQUEST_SUCCESS, ADD_JOB_REQUEST, ADD_JOB_REQUEST_FAIL, 
    ADD_JOB_REQUEST_RESET, ADD_JOB_REQUEST_SUCCESS, CHANGE_JOB_STATUS_FAIL,
    CHANGE_JOB_STATUS_REQUEST, CHANGE_JOB_STATUS_RESET, CHANGE_JOB_STATUS_SUCCESS, 
    CLOSE_JOB_FAIL, CLOSE_JOB_REQUEST, CLOSE_JOB_RESET, CLOSE_JOB_SUCCESS, 
    EVALUATE_APPLICATION_REQUEST, EVALUATE_APPLICATION_REQUEST_FAIL, EVALUATE_APPLICATION_REQUEST_RESET, 
    EVALUATE_APPLICATION_REQUEST_SUCCESS,
    SEARCH_APPLICANT_FAIL, SEARCH_APPLICANT_REQUEST, SEARCH_APPLICANT_RESET, SEARCH_APPLICANT_SUCCESS 
    } from "../constants/adminConstant"

export const evaluateApplicationReducer = (state = {}, action) => {
    switch (action.type) {
        case EVALUATE_APPLICATION_REQUEST:
            return { loading: true }
        case EVALUATE_APPLICATION_REQUEST_SUCCESS:
            return {

                loading: false

            }
        case EVALUATE_APPLICATION_REQUEST_FAIL:
            return { loading: false
            }
        case EVALUATE_APPLICATION_REQUEST_RESET:
            return {}
        default:
            return state;
    }

}

export const addToBlacklistReducer = (state = {}, action) => {
    switch (action.type) {
        case ADD_BLACKLIST_REQUEST:
            return { loading: true }
        case ADD_BLACKLIST_REQUEST_SUCCESS:
            return {
                loading: false
            }
        case ADD_BLACKLIST_REQUEST_FAIL:
            return { 
                loading: false
            }
        case ADD_BLACKLIST_REQUEST_RESET:
            return {}
        default:
            return state;
    }

}

export const addJobReducer = (state = {}, action) => {
    switch (action.type) {
        case ADD_JOB_REQUEST:
            return { loading: true }
        case ADD_JOB_REQUEST_SUCCESS:
            return {
                loading: false,
            }
        case ADD_JOB_REQUEST_FAIL:
            return { 
                loading: false
            }
        case ADD_JOB_REQUEST_RESET:
            return {}
        default:
            return state;
    }

}

export const searchApplicantReducer = (state = { applicants:[]}, action) => {
    switch (action.type) {
        case SEARCH_APPLICANT_REQUEST:
            return { loading: true }
        case SEARCH_APPLICANT_SUCCESS:
            return {
                loading: false,
                applicants: action.payload
            }
        case SEARCH_APPLICANT_FAIL:
            return { 
                loading: false
            }
        case SEARCH_APPLICANT_RESET:
            return {}
        default:
            return state;
    }

}

export const closeJobPostingReducer = (state = {}, action) => {
    switch (action.type) {
        case CLOSE_JOB_REQUEST:
            return { loading: true }
        case CLOSE_JOB_SUCCESS:
            return {
                loading: false,
                applicants: action.payload
            }
        case CLOSE_JOB_FAIL:
            return { 
                loading: false
            }
        case CLOSE_JOB_RESET:
            return {}
        default:
            return state;
    }

}

export const changeJobStatusReducer = (state = {}, action) => {
    switch (action.type) {
        case CHANGE_JOB_STATUS_REQUEST:
            return { loading: true }
        case CHANGE_JOB_STATUS_SUCCESS:
            return {
                loading: false,
                applicants: action.payload
            }
        case CHANGE_JOB_STATUS_FAIL:
            return { 
                loading: false
            }
        case CHANGE_JOB_STATUS_RESET:
            return {}
        default:
            return state;
    }

}

export const searchApplicantOnApplicationReducer = (state = { applicants:[]}, action) => {
    switch (action.type) {
        case SEARCH_APPLICANT_REQUEST:
            return { loading: true }
        case SEARCH_APPLICANT_SUCCESS:
            return {
                loading: false,
                applicants: action.payload
            }
        case SEARCH_APPLICANT_FAIL:
            return { 
                loading: false
            }
        case SEARCH_APPLICANT_RESET:
            return {}
        default:
            return state;
    }

}
