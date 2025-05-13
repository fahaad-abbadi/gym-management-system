import React from 'react';

const PaginationComponent = ({ currentPage, totalPages, onPageChange }) => {
  if (totalPages <= 1) return null;

  const getVisiblePages = () => {
    if (totalPages <= 5) return Array.from({ length: totalPages }, (_, i) => i + 1);

    const pages = [];

    if (currentPage > 2) pages.push(1);
    if (currentPage > 3) pages.push('...');
    
    const start = Math.max(2, currentPage - 1);
    const end = Math.min(totalPages - 1, currentPage + 1);

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    if (currentPage < totalPages - 2) pages.push('...');
    if (currentPage < totalPages - 1) pages.push(totalPages);

    return pages;
  };

  const visiblePages = getVisiblePages();

  return (
    <div className="mt-8 flex justify-center items-center space-x-2 text-sm font-medium">
      <button
        onClick={() => onPageChange(1)}
        disabled={currentPage === 1}
        className={buttonClass(currentPage === 1)}
      >
        First
      </button>

      <button
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1}
        className={buttonClass(currentPage === 1)}
      >
        &laquo;
      </button>

      {visiblePages.map((page, idx) =>
        page === '...' ? (
          <span
            key={idx}
            className="px-3 py-1 text-gray-400 select-none"
          >
            ...
          </span>
        ) : (
          <button
            key={page}
            onClick={() => onPageChange(page)}
            className={`px-3 py-1 rounded border ${
              page === currentPage
                ? 'bg-indigo-600 text-white border-indigo-600'
                : 'bg-white text-gray-700 border-gray-300 hover:bg-indigo-50'
            }`}
          >
            {page}
          </button>
        )
      )}

      <button
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages}
        className={buttonClass(currentPage === totalPages)}
      >
        &raquo;
      </button>

      <button
        onClick={() => onPageChange(totalPages)}
        disabled={currentPage === totalPages}
        className={buttonClass(currentPage === totalPages)}
      >
        Last
      </button>
    </div>
  );
};

const buttonClass = (disabled) =>
  `px-3 py-1 rounded border transition ${
    disabled
      ? 'bg-gray-200 text-gray-400 cursor-not-allowed'
      : 'bg-white text-indigo-600 border-indigo-300 hover:bg-indigo-50'
  }`;

export default PaginationComponent;
