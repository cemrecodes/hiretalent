import { combineReducers, applyMiddleware, createStore } from "redux";
import thunk from "redux-thunk";
import { composeWithDevTools } from "@redux-devtools/extension";
import { loadActiveJobReducer, loadJobReducer, loadJobSingleReducer } from "./reducers/jobReducer";
import { addApplicantLinkReducer, applicantLinkControlReducer, getApplicantionsReducer, userApplyJobReducer, userReducerLogout, userReducerProfile, userReducerSignIn } from "./reducers/userReducer";
import { loadApplicationReducer, loadApplicantApplicationsReducer } from "./reducers/applicationReducer";
import { addJobReducer, addToBlacklistReducer, evaluateApplicationReducer, searchApplicantOnApplicationReducer, searchApplicantReducer } from "./reducers/adminReducer";

const reducer = combineReducers({
    loadJobs: loadJobReducer,
    signIn: userReducerSignIn,
    logOut: userReducerLogout,
    userProfile: userReducerProfile,
    singleJob: loadJobSingleReducer,
    userJobApplication: userApplyJobReducer,
    loadApplications: loadApplicationReducer,
    addApplicantLink: addApplicantLinkReducer,
    applicantLinkControl: applicantLinkControlReducer,
    evaluateApplication: evaluateApplicationReducer,
    addToBlacklist: addToBlacklistReducer,
    addJob: addJobReducer,
    loadApplicantApplications: getApplicantionsReducer,
    loadActiveJobs: loadActiveJobReducer,
    searchResults: searchApplicantReducer,
    searchOnApplications: searchApplicantOnApplicationReducer,
    loadApplicant: loadApplicantApplicationsReducer
});


let initialState = {
  signIn: {
    userInfo: localStorage.getItem('userInfo') ? localStorage.getItem('userInfo') : null ,
    role: localStorage.getItem('role') ? localStorage.getItem('role') : null ,
    token: localStorage.getItem('token') ? localStorage.getItem('token') : null ,
    isAuthenticated: localStorage.getItem('userInfo') && localStorage.getItem('role') && localStorage.getItem('token') ? true : false
  } 
  };
  
const middleware = [thunk];
const store = createStore(reducer, initialState, composeWithDevTools(applyMiddleware(...middleware)))


export default store;