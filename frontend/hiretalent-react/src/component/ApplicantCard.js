import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, Collapse, Container, Grid, IconButton, TextField, useTheme } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import { useState } from 'react';
import ExpandCircleDownIcon from '@mui/icons-material/ExpandCircleDown';
import BlockIcon from '@mui/icons-material/Block';
import { useDispatch, useSelector } from 'react-redux';
import { addToBlackListAction } from '../redux/actions/adminAction';
import { useCookies } from 'react-cookie';

const ApplicantCardElement = ({ applicant }) => {
    const { palette } = useTheme();
    const [expand, setExpand] = useState();
    const [isBlacklisted, setIsBlacklisted] = useState(false);
    const [reasonForBlacklist, setReasonForBlacklist] = useState("");
    const dispatch = useDispatch();
    const { userInfo, token } = useSelector(state => state.signIn);
    const [cookies] = useCookies(['XSRF-TOKEN']);

    const handleExpandClick = () => {
        setExpand(!expand);
    };
    
    const handleBlacklist = () => {
        setIsBlacklisted(true);
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
                    <Grid item xs={6}>
                        <Box sx={{ p: 2 }}>
                            <Typography variant="h5" component="div">
                            <PersonIcon fontSize='large' sx={{ marginRight: "0.5rem" }} />
                            { applicant.blacklisted &&
                                <BlockIcon fontSize='large' sx={{color: "red", marginRight: "0.5rem" }}/>
                            }
                            {applicant.name} {applicant.surname}
                            </Typography>
                            {
                                applicant.blacklisted &&
                                <Typography variant="h6" component="div" >
                                <i><b>Bu kişi kara listeye alınmıştır.</b></i> 
                                </Typography>
                            }

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
                                    Kara Listeye Ekle
                                </Button>
                                </CardContent>
                            )}
                        </Box>
                    </Grid>
                    <Grid item xs={6}>
                        <Box sx={{ p:2, display: 'flex', justifyContent: 'flex-end' }}>
                            {!isBlacklisted && !applicant.blacklisted && (
                                <Button sx={{ backgroundColor: "black", color: "white", marginRight: "0.5rem" }} onClick={handleBlacklist}>
                                    Kara Listeye Al
                                </Button>
                            )}
                            <IconButton
                                    expand={expand}
                                    onClick={handleExpandClick}
                                    aria-expanded={expand}
                            >
                            <ExpandCircleDownIcon fontSize='large' sx={{ color: palette.secondary.main}}/>
                            </IconButton>
                        </Box>
                    </Grid>
                </Grid>
                <Collapse in={expand}>
                    <Container fixed>
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
                </Container>
                </Collapse>
            </CardContent>
        </Card>
    );
}

export default ApplicantCardElement;