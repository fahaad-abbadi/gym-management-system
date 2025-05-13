import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { useNavigate, useParams } from 'react-router-dom';
import {
  createClass,
  getClassById,
  updateClass,
} from '@/services/shared/classService';
import { classTypes } from '@/services/shared/enumService';

const AddEditClass = () => {
  const { id } = useParams();
  const isEditing = Boolean(id);
  const navigate = useNavigate();

  const [classData, setClassData] = useState({
    class_name: '',
    classType: '',
    schedule_time: '',
    duration_minutes: '',
    max_capacity: '',
  });

  const [message, setMessage] = useState('');

  useEffect(() => {
    if (isEditing) {
      getClassById(id).then((data) => {
        setClassData({
          ...data,
          schedule_time: new Date(data.schedule_time).toISOString().slice(0, 16),
        });
      });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setClassData((prev) => ({ ...prev, [name]: value }));
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEditing) {
        await updateClass(id, classData);
        showMessage('Class updated successfully.');
      } else {
        await createClass(classData);
        showMessage('Class created successfully.');
      }
      navigate('/trainer/classes');
    } catch (err) {
      showMessage(err.message || 'Failed to save class.');
    }
  };

  return (
    <Layout>
      <div className="max-w-3xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">
          {isEditing ? 'Edit Class' : 'Add New Class'}
        </h1>

        {message && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium">Class Name</label>
            <input
              name="class_name"
              value={classData.class_name}
              onChange={handleChange}
              required
              className="w-full px-3 py-2 border rounded shadow-sm"
            />
          </div>

          <div>
            <label className="block text-sm font-medium">Class Type</label>
            <select
              name="classType"
              value={classData.classType}
              onChange={handleChange}
              required
              className="w-full px-3 py-2 border rounded"
            >
              <option value="">Select Type</option>
              {classTypes.map((type) => (
                <option key={type.value} value={type.value}>
                  {type.label}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium">Schedule Time</label>
            <input
              type="datetime-local"
              name="schedule_time"
              value={classData.schedule_time}
              onChange={handleChange}
              required
              className="w-full px-3 py-2 border rounded"
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium">Duration (min)</label>
              <input
                type="number"
                name="duration_minutes"
                value={classData.duration_minutes}
                onChange={handleChange}
                required
                className="w-full px-3 py-2 border rounded"
              />
            </div>
            <div>
              <label className="block text-sm font-medium">Max Capacity</label>
              <input
                type="number"
                name="max_capacity"
                value={classData.max_capacity}
                onChange={handleChange}
                required
                className="w-full px-3 py-2 border rounded"
              />
            </div>
          </div>

          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded font-semibold"
          >
            {isEditing ? 'Update Class' : 'Create Class'}
          </button>
        </form>
      </div>
    </Layout>
  );
};

export default AddEditClass;
