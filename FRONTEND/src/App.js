import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Home from "./screens/Home";
import TableScreen from "./screens/TableScreen";
import GraphScreen from "./screens/Graphscreen";
import Header from "./components/Header";
import data from "./mock/logs.json";

const App = () => {

  const [logs, setLogs] = useState(data.logs);
  const [counts, setCounts] = useState(data.totalCounts);
  const [lastUpdated, setLastUpdated] = useState(null);

  const handleRefresh = () => {
    setLogs(data.logs);
    setCounts(data.totalCounts);
    setLastUpdated(new Date().toLocaleTimeString());
  };

  return (
    <Router>
      <div style={{ display: "flex" }}>
        <Sidebar />
        <div className="main-content">

          <div style={{ marginLeft: "195px", flex: 1 }}>
            <Header onRefresh={handleRefresh} lastUpdated={lastUpdated} />
            <Routes>
              <Route path="/" element={<Home logs={logs} counts={counts} />} />
              <Route path="/tableView" element={<TableScreen logs={logs} />} />
              <Route path="/graphView" element={<GraphScreen logs={logs} />} />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
