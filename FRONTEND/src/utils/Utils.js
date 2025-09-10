import axios from "axios";
import { MOCK_LOGS, MOCK_COUNTS } from "../mock/logs"; 
import { timeSeriesData } from "../mock/logData";

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const fetchLogs = async () => {
  try {
    const res = await axios.get(`${BASE_URL}/logs`);
    console.log("Logs API Response:", res.data);
    return res.data;
  } catch (err) {
    console.error("Error fetching logs, using mock:", err);
    return MOCK_LOGS; 
  }
};
export const fetchCounts = async () => {
  try {
    const res = await axios.get(`${BASE_URL}/logs/count`);
    console.log("Counts API Response:", res.data);
    return res.data;
  } catch (err) {
    console.error("Error fetching counts, using mock:", err);
    return MOCK_COUNTS; 
  }
};
export const fetchGraphData = async () => {
  try {
    const res = await axios.get(`${BASE_URL}/logs/linegraph`);
    console.log("Graph API Response:", res.data);
    return res.data;
  } catch (err) {
    console.error("Error fetching graph data, using mock:", err);
    return timeSeriesData; 
  }
};
