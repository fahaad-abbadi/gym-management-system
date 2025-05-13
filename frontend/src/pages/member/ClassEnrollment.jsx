import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { getAllClasses, enrollInClass } from '@/services/shared/classService';
import { getCurrentUser } from '@/services/api/authService';
import { classTypes } from '@/services/shared/enumService';

const ClassEnrollment = () => {
  const [classes, setClasses] = useState([]);
  const [user, setUser] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      const classData = await getClasses();
      setClasses(classData);
      const userData = await getCurrentUser();
      setUser(userData);
    };
    fetchData();
  }, []);

  const handleEnroll = async (classId) => {
    try {
      await enrollInClass(classId, user.member_id);
      showMessage('Enrollment successful!');
    } catch (error) {
      showMessage(error.message || 'Enrollment failed.');
    }
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 3000);
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">Available Classes</h1>

        {message && (
          <div className="bg-green-100 text-green-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {classes.length === 0 ? (
            <p>No classes available.</p>
          ) : (
            classes.map((cls) => (
              <div
                key={cls.class_id}
                className="bg-white p-4 shadow border rounded hover:shadow-md transition-all"
              >
                <h2 className="text-lg font-bold mb-1">{cls.class_name}</h2>
                <p className="text-sm text-gray-500">
                  {classTypes.find((t) => t.value === cls.classType)?.label}
                </p>
                <p className="text-sm text-gray-600 mt-1">
                  🕒 {new Date(cls.schedule_time).toLocaleString()}
                </p>
                <p className="text-sm text-gray-600">
                  ⏱ {cls.duration_minutes} minutes | 👥 Max {cls.max_capacity}
                </p>

                <button
                  className="mt-4 bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded w-full"
                  onClick={() => handleEnroll(cls.class_id)}
                >
                  Enroll
                </button>
              </div>
            ))
          )}
        </div>
      </div>
    </Layout>
  );
};

export default ClassEnrollment;
    