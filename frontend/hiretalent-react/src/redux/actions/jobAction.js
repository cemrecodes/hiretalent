import axios from "axios";
import { JOB_LOAD_FAIL, JOB_LOAD_REQUEST, JOB_LOAD_SINGLE_FAIL, JOB_LOAD_SINGLE_REQUEST, JOB_LOAD_SINGLE_SUCCESS, JOB_LOAD_SUCCESS } from "../constants/jobConstant"

// load all jobs
export const jobLoadAction = () => async(dispatch) => {
    dispatch({ type: JOB_LOAD_REQUEST });
    try{
        const { data } = await axios.get("http://localhost:8080/job-postings");
        if (data) {
            console.log(data.length);
          } else {
            console.log("data is undefined or null");
          }
        dispatch({ 
            type: JOB_LOAD_SUCCESS,
            totalItems: data.length,
            payload: data        
        });
    } catch ( error ){
        dispatch({ 
            type: JOB_LOAD_FAIL,
            payload: error.response.data.error       
        });
    }
}

// load all jobs
export const loadActiveJobAction = () => async(dispatch) => {
    dispatch({ type: JOB_LOAD_REQUEST });
    try{
        const { data } = await axios.get("http://localhost:8080/job-postings/get-active-not-closed");
        if (data) {
            console.log(data.length);
          } else {
            console.log("Data is undefined or null");
          }
        dispatch({ 
            type: JOB_LOAD_SUCCESS,
            totalItems: data.length,
            payload: data        
        });
    } catch ( error ){
        dispatch({ 
            type: JOB_LOAD_FAIL,
            payload: error.response.data.error       
        });
    }
}

// load single job action
export const jobLoadSingleAction = (id) => async (dispatch) => {
    dispatch({ type: JOB_LOAD_SINGLE_REQUEST });
    try {
        const { data } = await axios.get(`http://localhost:8080/job-postings/${id}`);
        dispatch({
            type: JOB_LOAD_SINGLE_SUCCESS,
            payload: data
        });
    } catch (error) {
        dispatch({
            type: JOB_LOAD_SINGLE_FAIL,
            payload: error.response.data.error
        });
    }
}