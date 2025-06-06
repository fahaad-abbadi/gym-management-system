import React, { useState, useEffect } from 'react';
import { getAllUsers } from '@/services/admin/adminService';
import { FaSearch } from 'react-icons/fa';
import { toast } from 'react-hot-toast';

const ManageTrainers = () => {
  const [trainers, setTrainers] = useState([]);
  const [filteredTrainers, setFilteredTrainers] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchTrainers = async () => {
    try {
      const res = await getAllUsers();
      const trainerList = (res.data.users || []).filter((u) => u.role === 'TRAINER');
      setTrainers(trainerList);
      setFilteredTrainers(trainerList);
    } catch (error) {
      console.error('Failed to load trainers:', error);
      toast.error('Failed to load trainers');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTrainers();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredTrainers(trainers);
    } else {
      setFilteredTrainers(
        trainers.filter((trainer) =>
          trainer.userName.toLowerCase().includes(searchQuery.toLowerCase()) ||
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
            <thead className="bg-gray-100 text-gray-700">
              <tr>
                <th className="px-6 py-3 text-left">Name</th>
                <th className="px-6 py-3 text-left">Email</th>
                <th className="px-6 py-3 text-left">Phone</th>
                <th className="px-6 py-3 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredTrainers.map((trainer) => (
                <tr key={trainer.id} className="border-b hover:bg-gray-50">
                  <td className="px-6 py-4 font-medium">{trainer.userName}</td>
                  <td className="px-6 py-4">{trainer.email}</td>
                  <td className="px-6 py-4">{trainer.phoneNumber || '-'}</td>
                  <td className="px-6 py-4 space-x-2">
                    <button className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-1 rounded text-sm">
                      Edit
                    </button>
                    <button className="bg-red-500 hover:bg-red-600 text-white px-4 py-1 rounded text-sm">
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
