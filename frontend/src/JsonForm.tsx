import { useEffect, useState } from "react";
import { Grid } from "@mui/material";
import CelebrationRoundedIcon from "@mui/icons-material/CelebrationRounded";
import HourglassBottomRounded from "@mui/icons-material/HourglassBottomRounded";
import { tss } from "tss-react";
import { BusinessTripRequestDto, FormDataDto, TaskControllerApi } from "./api/generated";
import { useParams } from "react-router-dom";
import JsonFormRenderer from "./component/JsonFormRenderer.tsx";

const useStyles = tss.create({
    formContainer: {
        padding: "1rem",
    },
    result: {
        height: "100vh",
    },
    demoform: {
        margin: "auto",
    },
    buttonContainer: {
        padding: "1rem",
        display: "flex",
        justifyContent: "center",
        gap: "1rem",
    },
    btn: {
        width: "100%",
        margin: "auto",
        marginTop: "1rem",
    },
});

export default function JsonForm() {
    const [taskId] = useState(useParams().taskId ?? "");
    const [completion, setCompletion] = useState(false);
    const [formData, setFormData] = useState<FormDataDto>();

    const { classes } = useStyles();

    const api = new TaskControllerApi();

    async function submit(data: BusinessTripRequestDto) {
        const result = await api.complete(taskId, data);
        setCompletion(result.data);
        return result.data;
    }

    useEffect(() => {
        async function fetch() {
            const response = await api.load(taskId);
            setFormData(response.data);
        }

        fetch();
    }, []);

    return formData ? (
        completion ? (
            <Grid
                container
                justifyContent="center"
                alignContent="center"
                className={classes.result}
            >
                <CelebrationRoundedIcon /> Task has been completed!
            </Grid>
        ) : (
            <JsonFormRenderer
                schema={formData.schema}
                uiSchema={formData.uiSchema}
                data={formData.data}
                onSubmit={submit}
            />
        )
    ) : (
        <Grid
            container
            justifyContent="center"
            alignContent="center"
            className={classes.result}
        >
            <HourglassBottomRounded /> Loading...
        </Grid>
    );
}
