import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import JsonForm from "./JsonForm.tsx";

export default function App() {

    return (
        <Router>
            <Routes>
                <Route path="/:taskId" element={<JsonForm/>}/>
            </Routes>
        </Router>
    )
}
