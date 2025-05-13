import React, { useState, useEffect } from 'react';
import { getAllDietPlans } from '@/services/trainer/dietPlanService';
import { FaSearch } from 'react-icons/fa';

const ManageDiets = () => {
  const [diets, setDiets] = useState([]);
  const [filteredDiets, setFilteredDiets] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDiets = async () => {
      try {
        const res = await getAllDietPlans();
        const dietList = res.data.dietPlans || [];

        console.log(dietList);
        setDiets(dietList);
        setFilteredDiets(dietList);
      } catch (error) {
        console.error('Failed to load diet plans:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchDiets();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredDiets(diets);
    } else {
      setFilteredDiets(
        diets.filter((d) =>
          d.planName.toLowerCase().includes(searchQuery.toLowerCase())
        )
      );
    }
  }, [searchQuery, diets]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Diet Plans</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search diets..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading diet plans...</div>
      ) : filteredDiets.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No diet plans found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left">Name</th>
                <th className="px-6 py-3 text-left">Description</th>
                <th className="px-6 py-3 text-left">Start Date</th>
                <th className="px-6 py-3 text-left">End Date</th>
                <th className="px-6 py-3 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredDiets.map((diet) => (
                <tr key={diet.dietPlanId} className="border-b">
                  <td className="px-6 py-4">{diet.planName}</td>
                  <td className="px-6 py-4 line-clamp-2 text-ellipsis overflow-hidden">{diet.description}</td>
                  <td className="px-6 py-4">{new Date(diet.startDate).toLocaleDateString()}</td>
                  <td className="px-6 py-4">{new Date(diet.endDate).toLocaleDateString()}</td>
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

export default ManageDiets;
