import React from "react";
import { useLocation } from "react-router-dom";
import "../index.css";

const Header = ({ onRefresh, lastUpdated, loading }) => {
  const location = useLocation();

  let title = "🏠 Log Monitoring Dashboard";
  if (location.pathname === "/tableView") {
    title = "📋 Log Monitoring Table View";
  } else if (location.pathname === "/graphView") {
    title = "📊 Log Monitoring Time Series";
  }

  return (
    <header className="topbar">
      <h2>{title}</h2>
      <div style={{ display: "flex", alignItems: "center", gap: "15px" }}>
        {lastUpdated && (
          <span className="time-ago">Last updated: {lastUpdated}</span>
        )}
        <button
          className="refresh-btn"
          onClick={onRefresh}
          disabled={loading}
          style={{
            opacity: loading ? 0.7 : 1,
            cursor: loading ? "not-allowed" : "pointer",
            display: "flex",
            alignItems: "center",
            gap: "8px",
          }}
        >
          Refresh
          {loading && <span className="spinner"></span>}
        </button>
      </div>
    </header>
  );
};

export default Header;
