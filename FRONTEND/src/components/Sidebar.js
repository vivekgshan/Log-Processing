import React from 'react';
import { NavLink } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = () => (
  <div className="sidebar">
    <h3>📊 Log Monitoring</h3>
    <nav>
      <ul>
        <li><NavLink exact="true" to="/" activeClassName="active">🏠 Home</NavLink></li>
        <li><NavLink to="/tableView" activeClassName="active">📋 Table view</NavLink></li>
        <li><NavLink to="/graphView" activeClassName="active">📈 Graphical view</NavLink></li>
      </ul>
    </nav>
  </div>
);

export default Sidebar;
