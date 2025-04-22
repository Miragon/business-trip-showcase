import { BrowserRouter as Router, Route, Routes, useParams } from 'react-router-dom';
import JsonForm from "./JsonForm.tsx";
import StartProcess from "./StartProcess.tsx";

export default function App() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<StartProcess />} />
                <Route path="/:taskId" element={<JsonForm />} />
            </Routes>
        </Router>
    )
}
