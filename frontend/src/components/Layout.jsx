import React from 'react';
import Sidebar from './Sidebar';
import Topbar from './Topbar';

const Layout = ({ children }) => {
  return (
    <div className="flex h-screen bg-gray-50 dark:bg-gray-900">
      {/* Sidebar */}
      <Sidebar />

      {/* Main content area */}
      <div className="flex flex-col flex-1 overflow-hidden">
        {/* Top navigation bar */}
        <Topbar />

        {/* Page content */}
        <main className="flex-1 overflow-y-auto p-6 bg-gray-100 dark:bg-gray-800">
          {children}
        </main>
      </div>
    </div>
  );
};

export default Layout;
