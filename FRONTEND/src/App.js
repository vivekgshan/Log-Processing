import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Home from './screens/Home';
import TableScreen from './screens/TableScreen';
import GraphScreen from './screens/Graphscreen';

function App() {
  return (
    <Router>
      <div style={{ display: 'flex' }}>
        <Sidebar />
        <div style={{ marginLeft: '195px', padding: '20px', flex: 1 }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/tableView" element={<TableScreen />} />
            <Route path="/graphView" element={<GraphScreen />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
