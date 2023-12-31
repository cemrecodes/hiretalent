import {
    LOAD_USER_APPLICATIONS_FAIL,
    LOAD_USER_APPLICATIONS_REQUEST,
    LOAD_USER_APPLICATIONS_RESET,
    LOAD_USER_APPLICATIONS_SUCCESS,
    USER_ADD_LINK_FAIL,
    USER_ADD_LINK_REQUEST,
    USER_ADD_LINK_RESET,
    USER_APPLY_JOB_FAIL,
    USER_APPLY_JOB_REQUEST,
    USER_APPLY_JOB_RESET,
    USER_APPLY_JOB_SUCCESS,
    USER_LINK_FAIL,
    USER_LINK_REQUEST,
    USER_LINK_RESET,
    USER_LINK_SUCCESS,
    USER_LOAD_FAIL,
    USER_LOAD_REQUEST,
    USER_LOAD_RESET,
    USER_LOAD_SUCCESS,
    USER_LOGOUT_FAIL,
    USER_LOGOUT_REQUEST,
    USER_LOGOUT_RESET, USER_LOGOUT_SUCCESS,
    USER_SIGNIN_FAIL,
    USER_SIGNIN_REQUEST,
    USER_SIGNIN_RESET,
    USER_SIGNIN_SUCCESS
} from "../constants/userConstant"


export const userReducerSignIn = (state = {}, action) => {
    switch (action.type) {
        case USER_SIGNIN_REQUEST:
            return { loading: true, 
                userInfo: null,
                isAuthenticated: false }
        case USER_SIGNIN_SUCCESS:
            return {
                loading: false,
                userInfo: action.payload.id,
                role: action.payload.role,
                isAuthenticated: true
            }
        case USER_SIGNIN_FAIL:
            return { loading: false, 
                 userInfo: null, 
                 isAuthenticated: false, 
                 error: action.payload.message }
        case USER_SIGNIN_RESET:
            return {}
        default:
            return state;
    }

}

//user profile
export const userReducerProfile = (state = { user: null }, action) => {
    switch (action.type) {
        case USER_LOAD_REQUEST:
            return { loading: true, user: null }
        case USER_LOAD_SUCCESS:
            return {
                loading: false,
                user: action.payload.data ,
            }
        case USER_LOAD_FAIL:
            return { loading: false, user: null, error: action.payload }
        case USER_LOAD_RESET:
            return {}
        default:
            return state;
    }

}

//log out reducer
export const userReducerLogout = (state = {}, action) => {
    switch (action.type) {
        case USER_LOGOUT_REQUEST:
            return { loading: true }
        case USER_LOGOUT_SUCCESS:
            return {
                loading: false
            }
        case USER_LOGOUT_FAIL:
            return { loading: false }
        case USER_LOGOUT_RESET:
            return {}
        default:
            return state;
    }

}

// apply for a job reducer
export const userApplyJobReducer = (state = {}, action) => {
    switch (action.type) {
        case USER_APPLY_JOB_REQUEST:
            return { loading: true }
        case USER_APPLY_JOB_SUCCESS:
            return {
                loading: false,
                userJob: action.payload,
            }
        case USER_APPLY_JOB_FAIL:
            return { loading: false, error: action.payload }
        case USER_APPLY_JOB_RESET:
            return {}
        default:
            return state;
    }
}


export const applicantLinkControlReducer = (state = {}, action) => {
    switch (action.type) {
        case USER_LINK_REQUEST:
            return { loading: true }
        case USER_LINK_SUCCESS:
            return {
                loading: false,
                link: action.payload,
            }
        case USER_LINK_FAIL:
            return { loading: false, error: action.payload }
        case USER_LINK_RESET:
            return {}
        default:
            return state;
    }

}

export const addApplicantLinkReducer = (state = {}, action) => {
    switch (action.type) {
        case USER_ADD_LINK_REQUEST:
            return { loading: true }
        case USER_LINK_SUCCESS:
            return {
                loading: false,
                link: action.payload,
            }
        case USER_ADD_LINK_FAIL:
            return { loading: false, error: action.payload }
        case USER_ADD_LINK_RESET:
            return {}
        default:
            return state;
    }

}

export const getApplicantionsReducer = (state = { applications:[] }, action) => {
    switch (action.type) {
        case LOAD_USER_APPLICATIONS_REQUEST:
            return { loading: true }
        case LOAD_USER_APPLICATIONS_SUCCESS:
            return {
                loading: false,
                applications: action.payload,
            }
        case LOAD_USER_APPLICATIONS_FAIL:
            return { loading: false, error: action.payload }
        case LOAD_USER_APPLICATIONS_RESET:
            return {}
        default:
            return state;
    }

}