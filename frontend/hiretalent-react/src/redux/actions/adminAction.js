import axios from "axios";
import { toast } from "react-toastify";
import { ADD_BLACKLIST_REQUEST, ADD_BLACKLIST_REQUEST_FAIL, ADD_BLACKLIST_REQUEST_SUCCESS, 
        ADD_JOB_REQUEST, ADD_JOB_REQUEST_FAIL, ADD_JOB_REQUEST_SUCCESS, 
        CHANGE_JOB_STATUS_FAIL, CHANGE_JOB_STATUS_REQUEST, CHANGE_JOB_STATUS_SUCCESS, 
        CLOSE_JOB_FAIL, CLOSE_JOB_REQUEST, CLOSE_JOB_SUCCESS, 
        EVALUATE_APPLICATION_REQUEST, EVALUATE_APPLICATION_REQUEST_FAIL, EVALUATE_APPLICATION_REQUEST_SUCCESS, 
        SEARCH_APPLICANT_FAIL, SEARCH_APPLICANT_REQUEST, SEARCH_APPLICANT_SUCCESS } 
        from "../constants/adminConstant";


export const evaluateApplicationAction = (token, jobId, hrUserId, status, xsrfToken) => async (dispatch) => {
    dispatch({ type: EVALUATE_APPLICATION_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
      };
    const data = {
        jobApplicationId: jobId,
        hrUserId: hrUserId,
        status: status
      };
    try {
        const res = await axios.put("http://localhost:8080/job-applications/evaluate", data, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: EVALUATE_APPLICATION_REQUEST_SUCCESS,
            payload: res
        });
        toast.success("Başvuru değerlendirildi!");
    } catch (error) {
        dispatch({
            type: EVALUATE_APPLICATION_REQUEST_FAIL,
        });
        toast.error("Başvuru değerlendirilemedi!");
    }
}

export const addToBlackListAction = (token, hrUserId, applicantId, reason, xsrfToken) => async (dispatch) => {
    dispatch({ type: ADD_BLACKLIST_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
      };
    const data = {
        hrUserId: hrUserId,
        applicantId: applicantId,
        reason: reason
      };
    try {
        const res = await axios.post("http://localhost:8080/blacklist", data, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: ADD_BLACKLIST_REQUEST_SUCCESS,
            payload: res
        });
        toast.success("Kişi kara listeye alındı!");
    } catch (error) {
        dispatch({
            type: ADD_BLACKLIST_REQUEST_FAIL,
        });
        toast.error("Kişi kara listeye alınamadı!");
    }
}

export const addJobPostingAction = (token, post, xsrfToken) => async (dispatch) => {
    dispatch({ type: ADD_JOB_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
    };
    try {
        const res = await axios.post("http://localhost:8080/job-postings", post, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: ADD_JOB_REQUEST_SUCCESS,
            payload: res
        });
        toast.success("İş ilanı başarıyla oluşturuldu!");
    } catch (error) {
        dispatch({
            type: ADD_JOB_REQUEST_FAIL,
        });
        toast.error("İş ilanı oluşturulamadı!");
    }
}

export const searchApplicantAction = (token, search) => async (dispatch) => {
    dispatch({ type: SEARCH_APPLICANT_REQUEST });
    const headers = {
        Authorization: token, 
    };
    try {
        const { data } = await axios.get("http://localhost:8080/applicants/search?q=" + search, {headers: headers});
        console.log(data)
        dispatch({
            type: SEARCH_APPLICANT_SUCCESS,
            payload: data
        });
    } catch (error) {
        dispatch({
            type: SEARCH_APPLICANT_FAIL, 
        });
        toast.error("Arama gerçekleştirilemedi!");
    }
}

export const closeJobPostingAction = (token, id, xsrfToken) => async (dispatch) => {
    dispatch({ type: CLOSE_JOB_REQUEST });
    const headers = {
        Authorization: token,
        'X-XSRF-TOKEN': xsrfToken 
    };
    console.log(headers)
    try {
        const res = await axios.put(`http://localhost:8080/job-postings/close/${id}`, "", {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: CLOSE_JOB_SUCCESS,
            payload: res
        });
        toast.success("İlan başarıyla kapatıldı!");
    } catch (error) {
        dispatch({
            type: CLOSE_JOB_FAIL,
        });
        toast.error("İlan kapatılamadı!");
    }
}

export const changeJobStatusAction = (token, id, status, date, xsrfToken) => async (dispatch) => {
    dispatch({ type: CHANGE_JOB_STATUS_REQUEST });
    const headers = {
        Authorization: token, 
        'X-XSRF-TOKEN': xsrfToken
    };
    const data = {
        jobPostingId: id,
        status: status,
        date: date
    };
    try {
        const res = await axios.put("http://localhost:8080/job-postings/change-status", data, {
            headers: headers,    
            withCredentials: true
        });
        dispatch({
            type: CHANGE_JOB_STATUS_SUCCESS,
            payload: res
        });
        toast.success("İlan durumu başarıyla değiştirildi!");
    } catch (error) {
        dispatch({
            type: CHANGE_JOB_STATUS_FAIL,
        });
        toast.error("İlan durumu değiştirilemedi!");
    }
}

export const searchApplicantOnApplicationAction = (token, id, text) => async (dispatch) => {
    dispatch({ type: SEARCH_APPLICANT_REQUEST });
    const headers = {
        Authorization: token, 
      };
    try {
        const { data } = await axios.get(`http://localhost:8080/job-applications/search?id=${id}&searchText=${text}`, {headers: headers});
        console.log(data)
        dispatch({
            type: SEARCH_APPLICANT_SUCCESS,
            payload: data
        });
    } catch (error) {
        dispatch({
            type: SEARCH_APPLICANT_FAIL,
        });
        toast.error("Arama gerçekleştirilemedi!");
    }
}
