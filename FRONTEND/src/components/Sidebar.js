import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = () => (
  <div className="sidebar">
    <h3>Log Monitoring</h3>
    <nav>
      <ul>
        <li><Link to="/">🏠 Home</Link></li>
        <li><Link to="/tableView">📋 Table view</Link></li>
        <li><Link to="/graphView">📊 Graphical view</Link></li>
      </ul>
    </nav>
  </div>
);

export default Sidebar;
