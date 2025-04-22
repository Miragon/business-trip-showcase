import JsonFormRenderer from "./component/JsonFormRenderer.tsx";
import {BusinessTripRequestDto, StartProcessControllerApi} from "./api/generated";

export default function StartProcess() {

    function startProcess(data: BusinessTripRequestDto): boolean {
        const api = new StartProcessControllerApi();
        api.startProcess(data);
        return true;
    }

    return (
        <JsonFormRenderer schema={schema} uiSchema={uiSchema} onSubmit={startProcess} />
    );

}

const schema: { [key: string]: any } = {
    "type": "object",
    "properties": {
        "name": {
            "type": "string",
            "title": "Name",
            "description": "The name of the applicant"
        },
        "email": {
            "type": "string",
            "title": "Email",
            "description": "The email of the approver.",
            "format": "email"
        },
        "dateTo": {
            "type": "string",
            "title": "Bis",
            "format": "date"
        },
        "dateFrom": {
            "type": "string",
            "title": "Von",
            "format": "date"
        },
        "cost": {
            "type": "integer",
            "title": "Kosten"
        },
        "destination": {
            "type": "string",
            "title": "Ziel"
        },
    }
};

const uiSchema: { [key: string]: any } = {
    "type": "VerticalLayout",
    "elements": [
        {
            "type": "Control",
            "scope": "#/properties/name",
            "label": "Name",
        },
        {
            "type": "Control",
            "scope": "#/properties/email",
            "label": "Email",
        },
        {
            "type": "HorizontalLayout",
            "elements": [
                {
                    "type": "Control",
                    "scope": "#/properties/dateFrom",
                    "label": "Von",
                },
                {
                    "type": "Control",
                    "scope": "#/properties/dateTo",
                    "label": "Bis",
                }
            ]
        },
        {
            "type": "Control",
            "scope": "#/properties/cost",
            "label": "Kosten",
        },
        {
            "type": "Control",
            "scope": "#/properties/destination",
            "label": "Ziel",
        },
    ]
}

