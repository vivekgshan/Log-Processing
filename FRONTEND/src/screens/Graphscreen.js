import React, { useState } from 'react';
import LogLineChart from '../components/LineChart';
import { timeSeriesData } from '../mock/logData';

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
    <div>
      <h4>📊 Log Monitoring Time Series</h4>

      <div style={{ marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '10px' }}>
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
        <button
          style={{
            padding: '8px 12px',
            backgroundColor: '#1890ff',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '14px'
          }}
          onClick={handleApply}
        >
          Apply
        </button>
      </div>

      <LogLineChart data={data} onRefresh={handleRefresh} />
    </div>
  );
};

export default GraphScreen;
