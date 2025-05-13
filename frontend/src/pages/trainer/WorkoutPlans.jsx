import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { useNavigate } from 'react-router-dom';
import {
  getWorkoutPlans,
  deleteWorkoutPlan,
} from '@/services/trainer/workoutService';
import PaginationComponent from '@/components/PaginationComponent';

const WorkoutPlans = () => {
  const [plans, setPlans] = useState([]);
  const [message, setMessage] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;
  const navigate = useNavigate();

  const fetchPlans = async () => {
    try {
      const res = await getWorkoutPlans();
      console.log(res.data.workoutPlans)
      setPlans(res.data.workoutPlans);
    } catch (error) {
      showMessage(error.message || 'Failed to load plans.');
    }
  };

  useEffect(() => {
    fetchPlans();
  }, []);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this plan?')) {
      try {
        await deleteWorkoutPlan(id);
        showMessage('Workout plan deleted.');
        fetchPlans();
      } catch (error) {
        showMessage(error.message || 'Delete failed.');
      }
    }
  };

  const paginatedPlans = plans.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <Layout>
      <div className="max-w-5xl mx-auto mt-10">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-2xl font-bold text-purple-50">Workout Plans</h1>
          <button
            onClick={() => navigate('/trainer/workouts/add')}
            className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded"
          >
            + Add Plan
          </button>
        </div>

        {message && (
          <div className="bg-green-100 text-green-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        {paginatedPlans.length === 0 ? (
          <p className="text-gray-500">No workout plans found.</p>
        ) : (
          <div className="space-y-4">
            {paginatedPlans.map((plan) => (
              <div
                key={plan.plan_id}
                className="border border-gray-200 rounded shadow hover:shadow-md transition-all p-4 flex justify-between items-center bg-white"
              >
                <div>
                  <h2 className="font-semibold text-lg">{plan.workout_name}</h2>
                  <p className="text-gray-600">{plan.description}</p>
                  <div className="text-sm text-gray-500 mt-1">
                    <span>Start: {plan.start_date}</span> |{' '}
                    <span>End: {plan.end_date}</span>
                  </div>
                </div>

                <div className="flex gap-2">
                  <button
                    onClick={() =>
                      navigate(`/trainer/workouts/edit/${plan.plan_id}`)
                    }
                    className="text-indigo-600 hover:text-indigo-800 font-medium"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(plan.plan_id)}
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
            totalPages={Math.ceil(plans.length / itemsPerPage)}
            onPageChange={setCurrentPage}
          />
        </div>
      </div>
    </Layout>
  );
};

export default WorkoutPlans;
