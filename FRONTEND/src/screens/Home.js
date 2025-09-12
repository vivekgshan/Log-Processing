import React from "react";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";
import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import InfoOutlineIcon from "@mui/icons-material/InfoOutline";
import AdbOutlinedIcon from "@mui/icons-material/AdbOutlined";
import "../index.css";

const Home = ({ counts }) => {
  const { ERROR = 0, WARN = 0, INFO = 0, DEBUG = 0 } = counts;

  return (
    <div className="dashboard">
      <div className="summary">
        <div className="card error">
          <div className="icon"><ErrorOutlineIcon /></div>
          <h3>{ERROR}</h3>
          <span>ERROR</span>
        </div>

        <div className="card warn">
          <div className="icon"><WarningAmberIcon /></div>
          <h3>{WARN}</h3>
          <span>WARNING</span>
        </div>

        <div className="card info">
          <div className="icon"><InfoOutlineIcon /></div>
          <h3>{INFO}</h3>
          <span>INFO</span>
        </div>

        <div className="card debug">
          <div className="icon"><AdbOutlinedIcon /></div>
          <h3>{DEBUG}</h3>
          <span>DEBUG</span>
        </div>
      </div>
    </div>
  );
};

export default Home;


