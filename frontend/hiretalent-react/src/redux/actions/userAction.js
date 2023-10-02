import axios from 'axios';
import { toast } from "react-toastify";
import {
    ALL_USER_LOAD_FAIL,
    ALL_USER_LOAD_REQUEST,
    ALL_USER_LOAD_SUCCESS,
    LOAD_USER_APPLICATIONS_FAIL,
    LOAD_USER_APPLICATIONS_REQUEST,
    LOAD_USER_APPLICATIONS_SUCCESS,
    USER_ADD_LINK_FAIL,
    USER_ADD_LINK_REQUEST,
    USER_ADD_LINK_SUCCESS,
    USER_APPLY_JOB_FAIL,
    USER_APPLY_JOB_REQUEST,
    USER_APPLY_JOB_SUCCESS,
    USER_LINK_FAIL,
    USER_LINK_REQUEST,
    USER_LINK_SUCCESS,
    USER_LOAD_FAIL,
    USER_LOAD_REQUEST,
    USER_LOAD_SUCCESS,
    USER_LOGOUT_FAIL,
    USER_LOGOUT_REQUEST,
    USER_LOGOUT_SUCCESS,
    USER_SIGNIN_FAIL,
    USER_SIGNIN_REQUEST,
    USER_SIGNIN_SUCCESS
} from '../constants/userConstant';


export const hrUserSignInAction = (user, xsrfToken) => async (dispatch) => {
    const headers = {
        'X-XSRF-TOKEN': xsrfToken,
    };
    console.log("XSRF Token: ", xsrfToken);
    dispatch({ type: USER_SIGNIN_REQUEST });
    try {
        const response  = await axios.post("http://localhost:8080/auth/hr-login", user, {
        headers: headers,    
        withCredentials: true
        });
        console.log("headers" , response.headers['x-auth-token']);
        localStorage.setItem('token', response.headers['x-auth-token']);
        localStorage.setItem('userInfo', response.data.userId);
        localStorage.setItem('role', response.data.role);
        dispatch({
            type: USER_SIGNIN_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        dispatch({
            type: USER_SIGNIN_FAIL,
            payload: error.response.data
        });
        toast.error(error.response.data);
    }
}

export const applicantUserSignInAction = (authCode, xsrfToken) => async (dispatch) => {
    const headers = {
        'X-XSRF-TOKEN': xsrfToken,
    };
    dispatch({ type: USER_SIGNIN_REQUEST });
    try {
        const data = {
            code: authCode,
          };
          
        const response  = await axios.post("http://localhost:8080/auth/applicant-login",data, {
            headers: headers,    
            withCredentials: true
            });
        console.log("headers" , response.headers['x-auth-token']);
        localStorage.setItem('token', response.headers['x-auth-token']);
        localStorage.setItem('userInfo', response.data.userId);
        localStorage.setItem('role', response.data.role);
        dispatch({
            type: USER_SIGNIN_SUCCESS,
            payload: response.data
        });
        toast.success("Başarıyla giriş yapıldı, hoş geldiniz!");
    } catch (error) {
        dispatch({
            type: USER_SIGNIN_FAIL,
            payload: error.response.data
        });
        toast.error(error.response.data);
    }
}

export const userLogoutAction = (xsrfToken) => async (dispatch) => {
    const headers = {
        'X-XSRF-TOKEN': xsrfToken,
    };
    dispatch({ type: USER_LOGOUT_REQUEST });
    try {
        const res = await axios.post("http://localhost:8080/auth/logout", {
            headers: headers,    
            withCredentials: true
        });
        localStorage.removeItem('token');
        localStorage.removeItem('userInfo');
        localStorage.removeItem('role');
        dispatch({
            type: USER_LOGOUT_SUCCESS
        });
        toast.success("Başarıyla çıkış yapıldı!");
    } catch (error) {
        dispatch({
            type: USER_LOGOUT_FAIL
        });
        toast.error("Çıkış yapmada sorun çıktı. Yeniden deneyiniz.");
    }
}

export const userProfileAction = (token, id) => async (dispatch) => {
    dispatch({ type: USER_LOAD_REQUEST });
    const headers = {
        Authorization: token, 
        
      };
    try {
        const response = await axios.get("http://localhost:8080/applicants/" + id, {headers: headers});
        dispatch({
            type: USER_LOAD_SUCCESS,
            payload: response
        });

    } catch (error) {
        dispatch({
            type: USER_LOAD_FAIL,
            payload: error.response
        });
    }
}

export const allUserAction = (token) => async (dispatch) => {
    dispatch({ type: ALL_USER_LOAD_REQUEST });
    const headers = {
        Authorization: token, 
      };
    try {
        const { data } = await axios.get("http://localhost:8080/applicants", {headers: headers});
        dispatch({
            type: ALL_USER_LOAD_SUCCESS,
            payload: data
        });

    } catch (error) {
        dispatch({
            type: ALL_USER_LOAD_FAIL,
            payload: error.response.data.error
        });
    }
}

export const userApplyJobAction = (token, jobId, applicantId, xsrfToken) => async (dispatch) => {
    dispatch({ type: USER_APPLY_JOB_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
      };
    const application = {
        jobPostingId: jobId,
        applicantId: applicantId
      };
    try {
        const { data } = await axios.post("http://localhost:8080/job-applications", application, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: USER_APPLY_JOB_SUCCESS,
            payload: data
        });
        toast.success("Bu işe başarıyla başvuruldu!");
    } catch (error) {
        dispatch({
            type: USER_APPLY_JOB_FAIL,
            payload: error.response.data
        });
        toast.error(error.response.data);
    }
}

export const applicantLinkControlAction = (token, applicantId) => async (dispatch) => {
    dispatch({ type: USER_LINK_REQUEST });
    const headers = {
        Authorization: token, 
      };
    try {
        const res = await axios.get("http://localhost:8080/applicants/get-link/" + applicantId, {headers: headers});
        dispatch({
            type: USER_LINK_SUCCESS,
            payload: res
        });
    } catch (error) {
        dispatch({
            type: USER_LINK_FAIL,
            payload: error.response.data.error
        });
        toast.error("Linkedin hesap linkiniz eksik! Link eklemeden başvuru yapamazsınız!");
    }
}

export const addApplicantLinkAction = (token, applicantId, link, xsrfToken) => async (dispatch) => {
    dispatch({ type: USER_ADD_LINK_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
      };
    const data = {
        applicantId: applicantId,
        link: link
      };
    try {
        const res = await axios.post("http://localhost:8080/applicants/add-link", data, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: USER_ADD_LINK_SUCCESS,
            payload: res.json()
        });
        toast.error("Lütfen bilgilerinizin alınması için biraz bekleyiniz.");
    } catch (error) {
        dispatch({
            type: USER_ADD_LINK_FAIL,
        });
        toast.error("Lütfen bilgilerinizin alınması için biraz bekleyiniz.");
    }
}

export const getApplicationsAction = (token, applicantId) => async (dispatch) => {
    dispatch({ type: LOAD_USER_APPLICATIONS_REQUEST });
    const headers = {
        Authorization: token, 
      };
    try {
        const res = await axios.get("http://localhost:8080/job-applications/applicant/" + applicantId , {headers: headers});
        console.log(res.data)
        dispatch({
            type: LOAD_USER_APPLICATIONS_SUCCESS,
            payload: res.data
        });
    } catch (error) {
        dispatch({
            type: LOAD_USER_APPLICATIONS_FAIL,
        });
    }
}