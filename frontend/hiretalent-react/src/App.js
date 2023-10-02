import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import NotFound from './pages/NotFound';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme';
import { ToastContainer } from 'react-toastify';
import Login from './pages/Login';
import 'react-toastify/dist/ReactToastify.css';
import UserRoute from './component/UserRoute';
import AdminRoute from './component/AdminRoute';
import Layout from './pages/global/Layout';
import { ProSidebarProvider } from 'react-pro-sidebar';
import UserJobsHistory from './pages/global/user/UserJobsHistory';
import UserInfoDashboard from './pages/global/user/UserInfoDashboard';
import SingleJob from './pages/SingleJob';
import DashJobs from './pages/global/admin/DashJobs';
import DashApplications from './pages/global/admin/DashApplications';
import DashAddJob from './pages/global/admin/DashAddJob';
import DashUsers from './pages/global/admin/DashUsers';
import Applicant from './pages/Applicant';

const UserJobsHistoryHOC = Layout(UserJobsHistory);
const UserInfoDashboardHOC = Layout(UserInfoDashboard);
const AdminDashJobsHOC = Layout(DashJobs);
const ApplicationsHOC = Layout(DashApplications);
const AddJobHOC = Layout(DashAddJob);
const AdminDashUsersHOC = Layout(DashUsers);

const App = () => {
  return (
    <>
    <ToastContainer/>
      <ThemeProvider theme={theme}>
        <CssBaseline>
          <ProSidebarProvider>
            <BrowserRouter>
              <Routes>
                <Route path='/' element={<Home/>}></Route>
                <Route path='/login' element={<Login/>}></Route>
                <Route path='/job/:id' element={<SingleJob/>}></Route>
                <Route path='/user/jobs' element={<UserRoute><UserJobsHistoryHOC/></UserRoute>}></Route>
                <Route path='/user/info' element={<UserRoute><UserInfoDashboardHOC/></UserRoute>}></Route>
                <Route path='/admin/add-job' element={<AdminRoute><AddJobHOC/></AdminRoute>}></Route>
                <Route path='/admin/job/application/:id' element={<AdminRoute><ApplicationsHOC/></AdminRoute>}></Route>
                <Route path='/admin/jobs' element={<AdminRoute><AdminDashJobsHOC/></AdminRoute>}></Route>
                <Route path='/admin/users' element={<AdminRoute><AdminDashUsersHOC/></AdminRoute>}></Route>
                <Route path='/admin/applicant/:id' element={<Applicant/>}></Route>
                <Route path='*' element={<NotFound/>}></Route>
              </Routes>
            </BrowserRouter>
          </ProSidebarProvider>
        </CssBaseline>
      </ThemeProvider>
    </>
  );
}

export default App;
