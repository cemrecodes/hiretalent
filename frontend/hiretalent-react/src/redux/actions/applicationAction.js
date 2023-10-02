import axios from "axios";
import { APPLICATION_LOAD_FAIL, 
        APPLICATION_LOAD_REQUEST, 
        APPLICATION_LOAD_SUCCESS, 
        LOAD_APPLICANT_APPLICATION_FAIL, 
        LOAD_APPLICANT_APPLICATION_REQUEST,
        LOAD_APPLICANT_APPLICATION_SUCCESS} 
from "../constants/applicationConstant";


export const applicationLoadAction = (token, id) => async(dispatch) => {
    dispatch({ type: APPLICATION_LOAD_REQUEST });
    const headers = {
        Authorization: token, 
      };
    try{
        const { data } = await axios.get("http://localhost:8080/job-applications/job-posting/" + id, {headers: headers});
        if (data) {
            console.log(data.length); 
            console.log(data);
          } else {
            console.log("data is undefined or null");
          }
        dispatch({ 
            type: APPLICATION_LOAD_SUCCESS,
            totalItems: data.length,
            payload: data        
        });
    } catch ( error ){
        dispatch({ 
            type: APPLICATION_LOAD_FAIL,
            // payload: error.response.data.error       
        });
    }
}


export const loadApplicantApplicationsAction = (token, id) => async (dispatch) => {
  dispatch({ type: LOAD_APPLICANT_APPLICATION_REQUEST });
  const headers = {
      Authorization: token, 
    };
  try {
      const { data } = await axios.get(`http://localhost:8080/job-applications/applicant/${id}`, {headers: headers});
      dispatch({
          type: LOAD_APPLICANT_APPLICATION_SUCCESS,
          payload: data
      });
  } catch (error) {
      dispatch({
          type: LOAD_APPLICANT_APPLICATION_FAIL,
      });
  }
}
