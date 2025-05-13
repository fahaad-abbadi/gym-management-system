import React, { useEffect, useState } from 'react';
import { getAllUsers, updateUserRole } from '@/services/admin/adminService';
import { toast } from 'react-hot-toast';

const AdminManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const res = await getAllUsers();

      setUsers(res.data.users);
      setLoading(false);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load users");
      setLoading(false);
    }
  };

  const handleRoleChange = async (userId, newRole) => {
    try {
      await updateUserRole(userId, newRole);
      toast.success(`User role updated to ${newRole}`);
      fetchUsers(); // refresh after updating
    } catch (error) {
      console.error(error);
      toast.error("Failed to update role");
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="max-w-6xl mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-8 text-gray-700">Manage Users</h1>

      {loading ? (
        <p className="text-center text-gray-500">Loading...</p>
      ) : users.length === 0 ? (
        <p className="text-center text-gray-500">No users found</p>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="py-2 px-4 text-left">Name</th>
                <th className="py-2 px-4 text-left">Email</th>
                <th className="py-2 px-4 text-left">Current Role</th>
                <th className="py-2 px-4 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              {users.map(user => (
                <tr key={user.id} className="border-b">
                  <td className="py-2 px-4">{user.firstName} {user.lastName}</td>
                  <td className="py-2 px-4">{user.email}</td>
                  <td className="py-2 px-4">{user.role}</td>
                  <td className="py-2 px-4">
                    <select
                      className="border rounded px-2 py-1"
                      value={user.role}
                      onChange={(e) => handleRoleChange(user.id, e.target.value)}
                    >
                      <option value="MEMBER">MEMBER</option>
                      <option value="TRAINER">TRAINER</option>
                      <option value="ADMIN">ADMIN</option>
                    </select>
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

export default AdminManageUsers;
