import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { getCurrentUser, updateUser } from '@/services/authService';

const ProfilePage = () => {
  const [user, setUser] = useState(null);
  const [form, setForm] = useState({ name: '', phone: '' });
  const [message, setMessage] = useState('');

  useEffect(() => {
    const load = async () => {
      const data = await getCurrentUser();
      setUser(data);
      setForm({ name: data.first_name, phone: data.phone_number });
    };
    load();
  }, []);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await updateUser(user.id, form);
      setMessage('Profile updated!');
    } catch (err) {
      setMessage('Failed to update profile.');
    }
  };

  return (
    <Layout>
      <div className="max-w-xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-4">Edit Profile</h1>
        {message && <p className="text-sm mb-4 text-indigo-600">{message}</p>}
        <form onSubmit={handleUpdate} className="space-y-4">
          <input
            type="text"
            placeholder="Full Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
            className="w-full border px-4 py-2 rounded"
          />
          <input
            type="text"
            placeholder="Phone Number"
            value={form.phone}
            onChange={(e) => setForm({ ...form, phone: e.target.value })}
            className="w-full border px-4 py-2 rounded"
          />
          <button
            type="submit"
            className="bg-indigo-600 text-white px-6 py-2 rounded hover:bg-indigo-700"
          >
            Save Changes
          </button>
        </form>
      </div>
    </Layout>
  );
};

export default ProfilePage;
