import logo from "./logo.svg";
import "./App.css";
import Dashboard from "./components/Dashboard";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LookerView from "./components/LookerView";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/looker-view/:id" element={<LookerView />} />
      </Routes>
    </Router>
  );
}

export default App;
