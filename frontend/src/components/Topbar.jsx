import React from 'react';
import { FaMoon, FaSun } from 'react-icons/fa';

const Topbar = () => {
  const toggleDark = () => {
    document.documentElement.classList.toggle('dark');
  };

  return (
    <header className="h-16 bg-white dark:bg-gray-900 border-b px-6 flex items-center justify-between shadow-sm">
      <h1 className="text-lg font-semibold text-gray-800 dark:text-gray-100">
        Dashboard
      </h1>
      <div className="flex items-center space-x-4">
        <button
          onClick={toggleDark}
          className="text-gray-500 dark:text-gray-300 hover:text-indigo-500"
        >
          <FaMoon className="dark:hidden" />
          <FaSun className="hidden dark:inline" />
        </button>
        <div className="flex items-center gap-2">
          <img
            src=""
            alt="User"
            className="h-8 w-8 rounded-full"
          />
          <span className="text-sm font-medium text-gray-700 dark:text-gray-200">You</span>
        </div>
      </div>
    </header>
  );
};

export default Topbar;
