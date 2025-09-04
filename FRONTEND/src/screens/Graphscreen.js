import React, { useState } from 'react';
import LogLineChart from '../components/LineChart';
import { timeSeriesData } from '../mock/logData';
import "../index.css";

const GraphScreen = () => {
  const [data, setData] = useState(timeSeriesData);
  const [startDate, setStartDate] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endDate, setEndDate] = useState('');
  const [endTime, setEndTime] = useState('');

  const handleRefresh = () => {
    setData([...timeSeriesData]);
  };

  const handleApply = () => {
    if (!startDate || !startTime || !endDate || !endTime) {
      alert('Please select all date and time fields.');
      return;
    }

    const start = new Date(`${startDate}T${startTime}:00Z`);
    const end = new Date(`${endDate}T${endTime}:00Z`);

    const filtered = timeSeriesData.filter(entry => {
      const timestamp = new Date(entry.timestamp);
      return timestamp >= start && timestamp <= end;
    });

    setData(filtered);
  };

  return (
    <div className="graph-container">
      <div className="filters">
        <label>
          Start Date:
          <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
        </label>
        <label>
          Start Time:
          <input type="time" value={startTime} onChange={e => setStartTime(e.target.value)} />
        </label>
        <label>
          End Date:
          <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
        </label>
        <label>
          End Time:
          <input type="time" value={endTime} onChange={e => setEndTime(e.target.value)} />
        </label>
        <button className="apply-btn" onClick={handleApply}>
          Apply
        </button>
      </div>

      <div className="chart-wrapper">
        <LogLineChart data={data} onRefresh={handleRefresh} />
      </div>
    </div>
  );
};

export default GraphScreen;
