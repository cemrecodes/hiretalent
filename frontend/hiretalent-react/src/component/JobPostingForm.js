import { useFormik } from 'formik';
import {
  Card,
  CardContent,
  FormControl,
  FormLabel,
  TextField,
  Button,
  FormControlLabel,
  Switch,
  Box,
} from '@mui/material';
import * as yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { addJobPostingAction } from '../redux/actions/adminAction';
import { useCookies } from 'react-cookie';

const validationSchema = yup.object({
  title: yup.string('İlan Başlığı').required('Bu alan zorunludur!'),
  description: yup.string('İş Tanımı').required('Bu alan zorunludur!'),
  qualifications: yup.string('Adaydan Beklenen Özellikler').required('Bu alan zorunludur!'),
  dateActivated: yup.date('Geçerli Bir Tarih Seçiniz!') ,
  dateDeactivated: yup.date('Geçerli Bir Tarih Seçiniz!'),
  status: yup.boolean(),
});

const JobPostingForm = () => {
  const dispatch = useDispatch();
  const { userInfo, token } = useSelector(state => state.signIn);
  const initialValues = {
    title: '',
    description: '',
    qualifications: '',
    dateActivated: '',
    dateDeactivated: '',
    status: false,
  };
  const [cookies] = useCookies(['XSRF-TOKEN']);

  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    onSubmit: (values, actions) => {
      console.log(userInfo)
        console.log(values)
        let job = {
            hrUserId: userInfo,
            title: values.title,
            description: values.description,
            qualifications: values.qualifications,
            activationDateTime: values.dateActivated ? new Date(values.dateActivated).toISOString() : null,
            deactivationDateTime: values.dateDeactivated ? new Date(values.dateDeactivated).toISOString() : null,
            status: values.status ? "ACTIVE" : "PASSIVE"
        }
        console.log(job)
        console.log(values)
        dispatch(addJobPostingAction(token, job, cookies['XSRF-TOKEN']));
        console.log(values);
    }
  });

  return (
    <form onSubmit={formik.handleSubmit}>
      <Card>
        <CardContent>
          <FormControl>
            <Box>
              <TextField sx={{ mb: 3 }}
                label="Başlık"
                variant="outlined"
                fullWidth
                type="text"
                id="title"
                name="title"
                value={formik.values.title}
                onChange={formik.handleChange}
              />
            </Box>
            <Box>
              <TextField sx={{ mb: 3 }}
                label="Açıklama"
                variant="outlined"
                fullWidth
                id="description"
                name="description"
                multiline
                rows={4}
                value={formik.values.description}
                onChange={formik.handleChange}
              />
            </Box>
            <Box>
              <TextField sx={{ mb: 3 }}
                label="Nitelikler"
                variant="outlined"
                fullWidth
                type="text"
                id="qualifications"
                name="qualifications"
                multiline
                rows={4}
                value={formik.values.qualifications}
                onChange={formik.handleChange}
              />
            </Box>
            <Box>
              <FormLabel sx={{ mr:2 }}>İlan Durumu</FormLabel>
              <FormControlLabel sx={{ mb: 3 }}
                control={
                  <Switch
                    checked={formik.values.status}
                    onChange={formik.handleChange}
                    name="status"
                  />
                }
                label={formik.values.status ? 'Aktif' : 'Pasif'}
              />
            </Box>
            { !formik.values.status && ( 
              <Box>
                <FormLabel>İsteğe Bağlı Aktifleştirme Tarihi</FormLabel>
                <TextField  
                  sx = {{ mb: 2}}
                  variant="outlined"
                  fullWidth
                  type="date"
                  id="dateActivated"
                  name="dateActivated"
                  value={formik.values.dateActivated}
                  onChange={formik.handleChange}
                  InputProps={{
                    inputProps: { min: new Date().toISOString().split('T')[0] },
                  }}
                />
              </Box>
            )}
            {formik.values.status && (
              <Box>
                <FormLabel>İsteğe Bağlı Pasifleştirme Tarihi</FormLabel>
                <TextField
                  sx = {{ mb: 2}}
                  variant="outlined"
                  fullWidth
                  type="date"
                  id="dateDeactivated"
                  name="dateDeactivated"
                  value={formik.values.dateDeactivated}
                  onChange={formik.handleChange}
                  InputProps={{
                    inputProps: { min: new Date().toISOString().split('T')[0] },
                  }}
                />
              </Box>
            )}
            <Button type="submit" variant="contained" color="primary">
              Oluştur
            </Button>
          </FormControl>
        </CardContent>
      </Card>
    </form>
  );
}

export default JobPostingForm;