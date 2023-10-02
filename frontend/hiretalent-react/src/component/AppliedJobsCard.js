import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { CardActions, Collapse, Grid, IconButton, useTheme } from '@mui/material';
import { ArrowDropDown } from '@mui/icons-material';
import { useState } from 'react';
import LoadingBox from './LoadingBox';
import WorkIcon from '@mui/icons-material/Work';

const AppliedJobCard = (job) => {
    const { palette } = useTheme();
    const [expand, setExpand] = useState();
    const application = job.application;

    let jobStatus = '';

    if (application.jobPosting.status === 'ACTIVE') {
        jobStatus = 'AKTİF';
    } else if (application.jobPosting.status === 'PASSIVE') {
        jobStatus = 'PASİF';
    } else if (application.jobPosting.status === 'CLOSED') {
        jobStatus = 'KAPALI';
    }

    let applicationStatus = '';

    if (application.status  === 'ACCEPTED') {
        applicationStatus = 'OLUMLU';
    } else if (application.status  === 'REJECTED') {
        applicationStatus = 'OLUMSUZ';
    } else if (application.status === 'EVALUATION') {
        applicationStatus = 'İŞLEME ALINDI';
    } else {
        applicationStatus = "Henüz değerlendirilmedi."
    }


    const formatDate = (date) => {
        const newDate = new Date(date);
        const options = {
            timeZone: "Europe/Istanbul",
            year: "numeric",
            month: "long",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
          };
        const formattedDate = newDate.toLocaleString("tr-TR", options);
        return formattedDate;
    }

    return (
        <>
        { application ?
        <Card sx={{ minWidth: 275, mb: 3, mt: 3 }}>
            <CardContent >
                <Typography variant="h5" component="div">
                    <WorkIcon sx={{ color: palette.secondary.main, fontSize: 22 }} />
                        {" " + application.jobPosting.title}
                </Typography>
                <Typography sx={{ mb: 1.5 }}>
                 <b> İlan Kodu: </b> {application.jobPosting.code}
                </Typography>
                { !expand && 
                <Typography variant="body2">
                   <b>Açıklama: </b> {application.jobPosting.description.split(" ").slice(0, 15).join(" ") + "..."}
                </Typography>
                }
            <CardActions sx={{ justifyContent: "flex-end" }}>
                <Grid container justifyContent="flex-end">
                    <IconButton onClick={() => setExpand(!expand)}>
                        <ArrowDropDown />
                    </IconButton>
                </Grid>
            </CardActions>
            <Collapse in={expand}>
            <Typography sx={{ mb: 1.5 }}>
                <b>Açıklama: </b>{application.jobPosting.description}
            </Typography>
            <Typography sx={{ mb: 1.5 }}>
                <b>Adaydan beklenen özellikler: </b>{application.jobPosting.qualifications}
            </Typography>
            <Typography sx={{ mb: 1.5 }}>
                <b>İş İlan Statüsü: </b>{
                jobStatus
                }
            </Typography>
            { application.jobPosting.dateClosed &&
            <Typography sx={{ mb: 1.5 }}>
                <b> İlan Kapanış Tarihi: </b>{formatDate(application.jobPosting.dateClosed)}
            </Typography>
            }
            <Typography sx={{ mb: 1.5 }}>
                <b> Başvuru Değerlendirilme Sonucu: </b> {applicationStatus}
            </Typography> 
            { application.status &&
            <Typography sx={{ mb: 1.5 }}>
                <b> Değerlendirilme tarihi: </b>{ formatDate(application.dateEvaluated) }
            </Typography> 
            }
            </Collapse>
            </CardContent> 
        </Card>
        :
        <LoadingBox></LoadingBox>
        }
        </>
    );
}

export default AppliedJobCard;