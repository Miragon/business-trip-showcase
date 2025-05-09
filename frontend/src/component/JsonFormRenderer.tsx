import {Button, Card, Grid, Typography} from "@mui/material";
import {materialCells, materialRenderers} from "@jsonforms/material-renderers";
import {JsonForms} from "@jsonforms/react";
import {useState} from "react";
import {UISchemaElement} from "@jsonforms/core";
import SendIcon from "@mui/icons-material/Check";
import {tss} from "tss-react";
import {BusinessTripRequestDetailedDto} from "../api/generated";

interface Props {
    schema: { [key: string]: any };
    uiSchema: { [key: string]: any };
    data?: BusinessTripRequestDetailedDto;
    onSubmit: (data: any) => Promise<boolean> | boolean;
}

const useStyles = tss.create({
    formContainer: {
        padding: "1rem",
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

export default function JsonFormRenderer({schema, uiSchema, data, onSubmit}: Props) {
    const renderers = [...materialRenderers];
    const [formData, setFormData] = useState<BusinessTripRequestDetailedDto | undefined>(data);
    const [formError, setFormError] = useState(true);

    const {classes} = useStyles();

    function handleFormChange({errors, data}: any) {
        setFormError(errors.length > 0);
        setFormData(data)
    }

    function submit() {
        return onSubmit(formData);
    }

    return (
        <Grid container direction="column" justifyContent="start" alignContent="center"
              className={classes.formContainer}>
            <Card>
                <Grid p={10}>
                    <Typography mb={5} variant={"h3"}>Daten weiterverarbeiten</Typography>
                    <Grid>
                        <div className={classes.demoform}>
                            <JsonForms
                                schema={schema}
                                uischema={uiSchema as unknown as UISchemaElement}
                                data={formData}
                                renderers={renderers}
                                cells={materialCells}
                                onChange={({errors, data}) =>
                                    handleFormChange({errors, data})
                                }
                            />
                        </div>
                    </Grid>
                    <Grid>
                        <div className={classes.buttonContainer}>
                            <Button
                                className={classes.btn}
                                variant="contained"
                                color="primary"
                                size="large"
                                endIcon={<SendIcon/>}
                                disabled={formError}
                                onClick={submit}
                            >
                                Aufgabe abschließen
                            </Button>
                        </div>
                    </Grid>
                </Grid>
            </Card>
        </Grid>
    )
}
