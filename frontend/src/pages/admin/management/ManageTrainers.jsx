import React, { useState, useEffect } from 'react';
import { getAllTrainers } from '@/services/admin/adminService';
import { FaSearch } from 'react-icons/fa';

const ManageTrainers = () => {
  const [trainers, setTrainers] = useState([]);
  const [filteredTrainers, setFilteredTrainers] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTrainers = async () => {
      try {
        const res = await getAllTrainers();
        const trainerList = res.data.trainers || [];
        setTrainers(trainerList);
        setFilteredTrainers(trainerList);
      } catch (error) {
        console.error('Failed to load trainers:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchTrainers();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredTrainers(trainers);
    } else {
      setFilteredTrainers(
        trainers.filter((trainer) =>
          trainer.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
          trainer.email.toLowerCase().includes(searchQuery.toLowerCase())
        )
      );
    }
  }, [searchQuery, trainers]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Trainers</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search trainers..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading trainers...</div>
      ) : filteredTrainers.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No trainers found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left">Name</th>
                <th className="px-6 py-3 text-left">Email</th>
                <th className="px-6 py-3 text-left">Phone</th>
                <th className="px-6 py-3 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredTrainers.map((trainer) => (
                <tr key={trainer.trainerId} className="border-b">
                  <td className="px-6 py-4">{trainer.firstName} {trainer.lastName}</td>
                  <td className="px-6 py-4">{trainer.email}</td>
                  <td className="px-6 py-4">{trainer.phoneNumber || '-'}</td>
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

export default ManageTrainers;
