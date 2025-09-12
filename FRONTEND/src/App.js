import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Home from "./screens/Home";
import TableScreen from "./screens/TableScreen";
import GraphScreen from "./screens/Graphscreen";
import Header from "./components/Header";
import { fetchLogs, fetchCounts, fetchGraphData } from "./utils/Utils";

const App = () => {
  const [logs, setLogs] = useState([]);
  const [counts, setCounts] = useState({});
  const [graphData, setGraphData] = useState([]);
  const [lastUpdated, setLastUpdated] = useState(null);
  const [loading, setLoading] = useState(false);   
  const [pageLoading, setPageLoading] = useState(true); 

  const fetchData = async () => {
    try {
      const logsData = await fetchLogs();
      const countsData = await fetchCounts();
      const graph = await fetchGraphData();

      setLogs(logsData);
      setCounts(countsData);
      setGraphData(graph);
      setLastUpdated(new Date().toLocaleTimeString());
    } catch (err) {
      console.error("Error fetching data:", err);
    }
  };

  const handleManualRefresh = async () => {
    setLoading(true);
    try {
      await fetchData();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const loadInitial = async () => {
      setPageLoading(true);
      await fetchData();
      setPageLoading(false);
    };

    loadInitial();

    const interval = setInterval(() => {
      fetchData();
    }, 30000);

    return () => clearInterval(interval);
  }, []);

  return (
    <Router>
      <div style={{ display: "flex" }}>
        <Sidebar />
        <div className="main-content">
          <div style={{ marginLeft: "195px", flex: 1 }}>
            <Header
              onRefresh={handleManualRefresh}
              lastUpdated={lastUpdated}
              loading={loading}
            />

            {pageLoading ? (
              <div className="page-loader">
                <div className="spinner"></div>
                <p>Loading data...</p>
              </div>
            ) : (
              <Routes>
                <Route path="/" element={<Home counts={counts} />} />
                <Route path="/tableView" element={<TableScreen logs={logs} />} />
                <Route path="/graphView" element={<GraphScreen graphData={graphData} />} />
              </Routes>
            )}
          </div>
        </div>
      </div>
    </Router>
  );
};

export default App;
