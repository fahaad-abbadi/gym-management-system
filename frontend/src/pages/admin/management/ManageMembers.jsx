import React, { useState, useEffect } from 'react';
import { getMembers } from '@/services/member/memberService';
import { FaSearch } from 'react-icons/fa';

const ManageMembers = () => {
  const [members, setMembers] = useState([]);
  const [filteredMembers, setFilteredMembers] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMembers = async () => {
      try {
        const res = await getMembers();
        const memberList = res.data.members || [];
        console.log(memberList[0].firstName)
        setMembers(memberList);
        setFilteredMembers(memberList);
      } catch (error) {
        console.error('Failed to load members:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchMembers();
  }, []);

  useEffect(() => {
    if (searchQuery.trim() === '') {
      setFilteredMembers(members);
    } else {
      setFilteredMembers(
        members.filter((member) =>
          member.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
          member.email.toLowerCase().includes(searchQuery.toLowerCase())
        )
      );
    }
  }, [searchQuery, members]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Members</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search members..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading members...</div>
      ) : filteredMembers.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No members found.</div>
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
              {filteredMembers.map((member) => (
                <tr key={member.memberId} className="border-b">
                  <td className="px-6 py-4">{member.firstName} {member.lastName}</td>
                  <td className="px-6 py-4">{member.email}</td>
                  <td className="px-6 py-4">{member.phoneNumber || '-'}</td>
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

export default ManageMembers;
