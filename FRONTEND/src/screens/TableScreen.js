import React, { useState, useEffect } from "react";
import "../index.css";

const TableScreen = ({ logs }) => {
  const [filter, setFilter] = useState("ALL");
  const [search, setSearch] = useState("");
  const [filteredLogs, setFilteredLogs] = useState(logs);
  const [currentPage, setCurrentPage] = useState(1);
  const logsPerPage = 5;

  useEffect(() => {
    let result =
      filter === "ALL"
        ? logs
        : logs.filter((log) => log.level.toUpperCase() === filter.toUpperCase());

    if (search.trim() !== "") {
      const searchText = search.toLowerCase();
      result = result.filter(
        (log) =>
          log.message.toLowerCase().includes(searchText) ||
          log.level.toLowerCase().includes(searchText) ||
          log.timestamp.toLowerCase().includes(searchText)
      );
    }

    setFilteredLogs(result);
    setCurrentPage(1);
  }, [logs, filter, search]);

  const totalPages = Math.ceil(filteredLogs.length / logsPerPage);
  const indexOfLast = currentPage * logsPerPage;
  const indexOfFirst = indexOfLast - logsPerPage;
  const currentLogs = filteredLogs.slice(indexOfFirst, indexOfLast);

  return (
    <div className="dashboard">
      <div className="controls">
        <select value={filter} onChange={(e) => setFilter(e.target.value)}>
          <option value="ALL">All Logs</option>
          <option value="ERROR">Error</option>
          <option value="WARN">Warn</option>
          <option value="INFO">Info</option>
          <option value="DEBUG">Debug</option>
        </select>

        <input
          type="text"
          placeholder="Search logs..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>
      <table>
        <thead>
          <tr>
            <th>S.No</th>
            <th>Timestamp</th>
            <th>Type</th>
            <th>Message</th>
          </tr>
        </thead>
        <tbody>
          {currentLogs.map((log, index) => (
            <tr key={log.id}>
              <td>{indexOfFirst + index + 1}</td>
              <td>{log.timestamp}</td>
              <td className={`level ${log.level.toLowerCase()}`}>
                {log.level}
              </td>
              <td>{log.message}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="pagination">
        <button
          onClick={() => setCurrentPage((p) => Math.max(p - 1, 1))}
          disabled={currentPage === 1}
        >
          Prev
        </button>
        <span>
          Page {currentPage} of {totalPages || 1}
        </span>
        <button
          onClick={() => setCurrentPage((p) => Math.min(p + 1, totalPages))}
          disabled={currentPage === totalPages || totalPages === 0}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default TableScreen;
