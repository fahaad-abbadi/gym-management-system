import React, { useState, useEffect } from 'react';
import { getAllInventory } from '@/services/shared/inventoryService';
import { FaSearch } from 'react-icons/fa';

const ManageInventory = () => {
  const [inventory, setInventory] = useState([]);
  const [filteredInventory, setFilteredInventory] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchInventory = async () => {
      try {
        const res = await getAllInventory();
        const inventoryList = res.data.inventories || [];

        console.log(inventoryList)
        setInventory(inventoryList);
        setFilteredInventory(inventoryList);
      } catch (error) {
        console.error('Failed to load inventory:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchInventory();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredInventory(inventory);
    } else {
      setFilteredInventory(
        inventory.filter((item) =>
          item.name.toLowerCase().includes(searchQuery.toLowerCase())
        )
      );
    }
  }, [searchQuery, inventory]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Inventory</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search inventory..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading inventory...</div>
      ) : filteredInventory.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No inventory items found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left">Name</th>
                <th className="px-6 py-3 text-left">Quantity</th>
                <th className="px-6 py-3 text-left">Condition</th>
                <th className="px-6 py-3 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredInventory.map((item) => (
                <tr key={item.inventoryId} className="border-b">
                  <td className="px-6 py-4">{item.name}</td>
                  <td className="px-6 py-4">{item.quantity}</td>
                  <td className="px-6 py-4">{item.condition}</td>
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

export default ManageInventory;
