import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { Box, Collapse, Grid, IconButton, TextField } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import { ArrowDropDown } from '@mui/icons-material';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addToBlackListAction, evaluateApplicationAction } from '../redux/actions/adminAction';
import BlockIcon from '@mui/icons-material/Block';
import { Link } from 'react-router-dom';
import AddIcon from '@mui/icons-material/Add';
import { useCookies } from 'react-cookie';

const AdminApplicationCard = ({ id, applicant, dateCreated, status}) => {
    console.log("applicant: " , applicant);
    const dateString = dateCreated;
    const date = new Date(dateString);
    const options = {
        timeZone: "Europe/Istanbul",
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
      };
    const formattedDate = date.toLocaleString("tr-TR", options);
    const [expand, setExpand] = useState();
    const [isBlacklisted, setIsBlacklisted] = useState(false);
    const [reasonForBlacklist, setReasonForBlacklist] = useState("");
    const dispatch = useDispatch();
    const { userInfo, token } = useSelector(state => state.signIn);
    const [ colourAccept, setColourAccept ] = useState("white");
    const [ colourReject, setColourReject ] = useState("white");
    const [ colourBlacklist, setColourBlacklist ] = useState("white");
    const [ colour, setColour] = useState("white");
    const [cookies] = useCookies(['XSRF-TOKEN']);

    let statusText = '';

    if (status === 'ACCEPTED') {
      statusText = 'KABUL EDİLDİ';
    } else if (status === 'REJECTED') {
      statusText = 'REDDEDİLDİ';
    } else if (status === 'EVALUATION') {
      statusText = 'İŞLEME ALINDI';
    } else {
      statusText = 'Bu kişi henüz değerlendirilmemiştir!';
    }

    const handleAccept = () => {
       dispatch(evaluateApplicationAction(token, id, userInfo, "ACCEPTED", cookies['XSRF-TOKEN']))
    };
    
    const handleProcess = () => {
       dispatch(evaluateApplicationAction(token, id, userInfo, "EVALUATION", cookies['XSRF-TOKEN']))
    };
    
    const handleReject = () => {
       dispatch(evaluateApplicationAction(token, id, userInfo, "REJECTED", cookies['XSRF-TOKEN']))
    };
    
    const openBlacklist = () => {
        setIsBlacklisted(true);
    };

    const closeBlacklist = () => {
      setIsBlacklisted(false);
  };

    const handleAddBlacklist = () => {
        dispatch(addToBlackListAction(token, userInfo, applicant.id, reasonForBlacklist, cookies['XSRF-TOKEN']))
    };
    
    const handleReasonChange = (event) => {
        setReasonForBlacklist(event.target.value);
    };

    return (
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
            <CardContent>
              <Grid container >
                <Grid item xs={5}>
                  <Box sx={{ p: 2 }}>
                  <Typography variant="h5" component="div" sx={{ mb:"0.5rem" }}>
                  <PersonIcon sx={{ fontSize: 30, marginRight: "0.5rem" }} />
                  { applicant.blacklisted &&
                      <BlockIcon fontSize='large' sx={{color: "red", marginRight: "0.5rem" }}/>
                  }
                      {applicant.name} {applicant.surname}
                  </Typography>
              <Typography variant="h6" component="div" sx={{ mb:"0.5rem" }}>
                <b> Başvuru Tarihi:</b> {formattedDate}
              </Typography>
              <Typography variant="h6" component="div" sx={{ mb:"0.5rem" }} >
                <b> Statüs:</b> {statusText}
              </Typography>
              {
                applicant.isBlacklisted &&
                <Typography variant="h6" component="div" sx={{ mb:"0.5rem" }}>
                <i><b>Bu kişi kara listeye alınmıştır.</b></i> 
                </Typography>
              }
              </Box>
            </Grid>
            <Grid item xs={7}>
              <CardActions sx={{ justifyContent: "flex-end" }}>
                  <Grid container justifyContent="flex-end">
              {!isBlacklisted && !applicant.isBlacklisted && (
                  <>
                  <Button disableElevation variant='contained' size="small" sx={{ marginRight: "0.5rem" ,mb:"0.5rem" }} startIcon={<AddIcon />}>
                    <Link style={{ textDecoration: "none", color: "white", boxShadow: 0 ,marginRight: "0.5rem" }} to={`/admin/applicant/${applicant.id}`}>
                      DİĞER BAŞVURULAR
                    </Link>
                  </Button>
                  <Button size="small" sx={{ backgroundColor: "green", color: `${colourAccept}`, marginRight: "0.5rem" , style: {color: "red"}, mb:"0.5rem"}} onClick={handleAccept}
                            onMouseEnter={() => setColourAccept('black')}
                            onMouseLeave={() => setColourAccept('white')}>
                    Kabul Et
                  </Button>
                  <Button size="small" sx={{ backgroundColor: "yellow", color: "black", marginRight: "0.5rem", mb:"0.5rem" }} onClick={handleProcess}>
                    İşleme Al
                  </Button>
                  <Button size="small" sx={{ backgroundColor: "red", color: `${colourReject}`, marginRight: "0.5rem", mb:"0.5rem" }} onClick={handleReject}
                            onMouseEnter={() => setColourReject('black')}
                            onMouseLeave={() => setColourReject('white')}>
                    Reddet
                  </Button>
                  <Button size="small" sx={{ backgroundColor: "black", color: `${colourBlacklist}`, marginRight: "0.5rem", mb:"0.5rem" }} onClick={openBlacklist}
                            onMouseEnter={() => setColourBlacklist('black')}
                            onMouseLeave={() => setColourBlacklist('white')}>                
                    KARA LİSTEYE EKLE
                  </Button>
                  </>
                )}
                { isBlacklisted && !applicant.isBlacklisted &&
                  <Button sx={{ backgroundColor: "green", color: `${colour}`, marginRight: "0.5rem" }} onClick={closeBlacklist}
                            onMouseEnter={() => setColour('black')}
                            onMouseLeave={() => setColour('white')}>                
                    GERİ DÖN
                  </Button>
                }
                  <IconButton onClick={() => setExpand(!expand)}>
                    <ArrowDropDown />
                  </IconButton>           
                </Grid>
              </CardActions>
            </Grid>
          </Grid>
          
        {isBlacklisted && (
        <CardContent>
          <TextField
            label="Kara Listeye Alma Nedeni"
            variant="outlined"
            fullWidth
            value={reasonForBlacklist}
            onChange={handleReasonChange}
            sx={{mb:3}}
          />
          <Button
            sx={{ backgroundColor: "black", color: "white", marginRight: "0.5rem" }}
            onClick={() => {
              handleAddBlacklist();
              setIsBlacklisted(false);
            }}
          >
            KARA LİSTEYE EKLE
          </Button>
        </CardContent>
          )}
            <Collapse in={expand}>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
                <b>Eğitim</b>
                {applicant.educations.map((education) => (
                <div key={education.id}>
                    <Typography variant="body1">
                    <b>  Okul Adı:</b> {education.schoolName}
                    </Typography>
                    <Typography variant="body1">
                    <b> Bölüm: </b>{education.department || "Bilgi Yok"}
                    </Typography>
                    <Typography variant="body1">
                        <b>Zaman:</b> {education.time}</Typography>
                    <Typography variant="body1">
                    <b> Detay:</b> {education.detail || "Bilgi Yok"}
                    </Typography>
                    <br />
                </div>
                ))}
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
                <b> Deneyimler</b>
                {applicant.experiences.map((experience) => (
                <div key={experience.id}>
                    <Typography variant="body1">
                    <b>Başlık:</b> {experience.title}
                    </Typography>
                    <Typography variant="body1">
                    <b>Şirket Pozisyonu:</b> {experience.companyPosition || "Bilgi Yok"}
                    </Typography>
                    <Typography variant="body1"><b>Zaman:</b> {experience.time}</Typography>
                    <Typography variant="body1">
                    <b> Detay:</b> {experience.detail || "Bilgi Yok"}
                    </Typography>
                    <br />
                </div>
                ))}
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
             <b> Sertifikalar</b>
                {applicant.certificates.map((certificate) => (
                <div key={certificate.id}>
                    <Typography variant="body1">
                    <b>  Başlık: </b>{certificate.title}
                    </Typography>
                    <Typography variant="body1">
                    <b>Veren Kuruluş:</b> {certificate.issuer}
                    </Typography>
                    <br />
                </div>
                ))}
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
            <b>Yetenekler</b>
                {applicant.skills.map((skill) => (
                <div key={skill.id}>
                    <Typography variant="body1">{skill.skill}</Typography>
                </div>
                ))}
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
            <b> Diller</b>
                {applicant.languages.map((language) => (
                <div key={language.id}>
                    <Typography variant="body1">{language.language}</Typography>
                </div>
                ))}
            </Typography>
            <Typography sx={{ mb: 1.5 }} color="text.secondary">
                  <b>Linkedin Profili: </b>
                  <a href={applicant.link}>{applicant.link}</a>
            </Typography>
            </Collapse>
            </CardContent>
        </Card>
    );
}

export default AdminApplicationCard;