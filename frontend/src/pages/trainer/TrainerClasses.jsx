import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { useNavigate } from 'react-router-dom';
import {
  getAllClasses,
  deleteClass,
} from '@/services/shared/classService';
import PaginationComponent from '@/components/PaginationComponent';
import { classTypes } from '@/services/shared/enumService';

const TrainerClasses = () => {
  const [classes, setClasses] = useState([]);
  const [message, setMessage] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const navigate = useNavigate();

  const fetchClasses = async () => {
    try {
      const res = await getAllClasses();
      console.log(res.data.classes);
      setClasses(res.data.classes || []);
    } catch (err) {
      showMessage(err.message || 'Failed to load classes.');
    }
  };

  useEffect(() => {
    fetchClasses();
  }, []);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this class?')) {
      try {
        await deleteClass(id);
        showMessage('Class deleted successfully.');
        fetchClasses();
      } catch (err) {
        showMessage(err.message || 'Delete failed.');
      }
    }
  };

  const paginatedClasses = classes.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <Layout>
      <div className="max-w-6xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-slate-100">My Classes</h1>
          <button
            onClick={() => navigate('/trainer/classes/add')}
            className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded"
          >
            + Create Class
          </button>
        </div>

        {message && (
          <div className="bg-blue-100 text-blue-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        {paginatedClasses.length === 0 ? (
          <p className="text-gray-500">No classes scheduled yet.</p>
        ) : (
          <div className="grid gap-4">
            {paginatedClasses.map((cls) => (
              <div
                key={cls.classId}
                className="bg-white border p-4 rounded shadow flex justify-between items-center"
              >
                <div>
                  <h3 className="text-lg font-semibold">{cls.className}</h3>
                  <p className="text-gray-600">
                    {classTypes.find(t => t.value === cls.classType)?.label || cls.classType}
                  </p>
                  <p className="text-sm text-gray-500">
                    Time: {new Date(cls.scheduleTime).toLocaleString()} | Duration: {cls.durationMinutes} min
                  </p>
                </div>

                <div className="flex gap-2">
                  <button
                    onClick={() => navigate(`/trainer/classes/edit/${cls.classId}`)}
                    className="text-indigo-600 hover:text-indigo-800 font-medium"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(cls.classId)}
                    className="text-red-600 hover:text-red-800 font-medium"
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}

        <div className="mt-6">
          <PaginationComponent
            currentPage={currentPage}
            totalPages={Math.ceil(classes.length / itemsPerPage)}
            onPageChange={setCurrentPage}
          />
        </div>
      </div>
    </Layout>
  );
};

export default TrainerClasses;
