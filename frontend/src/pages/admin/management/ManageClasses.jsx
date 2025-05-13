import React, { useState, useEffect } from 'react';
import { getAllClasses } from '@/services/admin/adminService';
import { FaSearch } from 'react-icons/fa';

const ManageClasses = () => {
  const [classes, setClasses] = useState([]);
  const [filteredClasses, setFilteredClasses] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchClasses = async () => {
      try {
        const res = await getAllClasses();
        const classList = res.data.classes || [];

        setClasses(classList);
        setFilteredClasses(classList);
      } catch (error) {
        console.error('Failed to load classes:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchClasses();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredClasses(classes);
    } else {
      setFilteredClasses(
        classes.filter((c) =>
          c.className.toLowerCase().includes(searchQuery.toLowerCase())
        )
      );
    }
  }, [searchQuery, classes]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Classes</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search classes..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading classes...</div>
      ) : filteredClasses.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No classes found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left">Class Name</th>
                <th className="px-6 py-3 text-left">Type</th>
                <th className="px-6 py-3 text-left">Trainer ID</th>
                <th className="px-6 py-3 text-left">Schedule</th>
                <th className="px-6 py-3 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredClasses.map((cls) => (
                <tr key={cls.classId} className="border-b">
                  <td className="px-6 py-4">{cls.className}</td>
                  <td className="px-6 py-4">{cls.classType}</td>
                  <td className="px-6 py-4">{cls.trainerId}</td>
                  <td className="px-6 py-4">
                    {cls.scheduleTime
                      ? new Date(cls.scheduleTime).toLocaleString()
                      : '-'}
                  </td>
                  <td className="px-6 py-4">
                    <button className="bg-indigo-600 text-white px-3 py-1 rounded-md text-xs hover:bg-indigo-700 transition mr-2">
                      Edit
                    </button>
                    <button className="bg-red-500 text-white px-3 py-1 rounded-md text-xs hover:bg-red-600 transition">
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ManageClasses;
