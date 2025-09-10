/* import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import { timeSeriesData } from '../mock/logData';
import './LineChart.css';

const LogLineChart = ({ onRefresh }) => {
  return (
    <div className="chart-container">
      <div className="chart-header">
        <h2></h2>
        <button className="refresh-button" onClick={onRefresh}>🔄 Refresh</button>
      </div>
      <LineChart width={800} height={400} data={timeSeriesData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="timestamp" angle={0} textAnchor="end" interval={0} />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="ERROR" stroke="#ff4d4f" />
        <Line type="monotone" dataKey="WARN" stroke="#faad14" />
        <Line type="monotone" dataKey="INFO" stroke="#1890ff" />
        <Line type="monotone" dataKey="DEBUG" stroke="#52c41a" />
      </LineChart>
    </div>
  );
};

export default LogLineChart;
 */


import React from "react";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from "recharts";
import "./LineChart.css";

const LogLineChart = ({ data }) => {
  return (
    <div className="chart-container">
      <LineChart width={800} height={400} data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="timestamp" />
        <YAxis allowDecimals={false}/>
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="ERROR" stroke="#ff4d4f" />
        <Line type="monotone" dataKey="WARN" stroke="#faad14" />
        <Line type="monotone" dataKey="INFO" stroke="#1890ff" />
        <Line type="monotone" dataKey="DEBUG" stroke="#52c41a" />
      </LineChart>
    </div>
  );
};

export default LogLineChart;

