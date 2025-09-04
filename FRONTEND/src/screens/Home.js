import React from "react";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import InfoOutlineIcon from "@mui/icons-material/InfoOutline";
import AdbOutlinedIcon from "@mui/icons-material/AdbOutlined";
import "../index.css";

const Home = ({ counts }) => {
  return (
    <div className="dashboard">
      <div className="summary">
        <div className="card error">
          <div className="icon"><ErrorOutlineIcon /></div>
          <h3>{counts.ERROR}</h3>
          <span>ERROR</span>
        </div>

        <div className="card warn">
          <div className="icon"><WarningAmberIcon /></div>
          <h3>{counts.WARNING}</h3>
          <span>WARNING</span>
        </div>

        <div className="card info">
          <div className="icon"><InfoOutlineIcon /></div>
          <h3>{counts.INFO}</h3>
          <span>INFO</span>
        </div>

        <div className="card debug">
          <div className="icon"><AdbOutlinedIcon /></div>
          <h3>{counts.DEBUG}</h3>
          <span>DEBUG</span>
        </div>
      </div>
    </div>
  );
};

export default Home;
