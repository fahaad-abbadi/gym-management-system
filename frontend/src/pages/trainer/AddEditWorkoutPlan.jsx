import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { useNavigate, useParams } from 'react-router-dom';
import {
  getWorkoutPlanById,
  addWorkoutPlan,
  updateWorkoutPlan,
} from '@/services/trainer/workoutService';
import { getMembers } from '@/services/member/memberService';

const AddEditWorkoutPlan = () => {
  const { id } = useParams();
  const isEditing = Boolean(id);
  const navigate = useNavigate();

  const [workout, setWorkout] = useState({
    workout_name: '',
    description: '',
    start_date: '',
    end_date: '',
    member_id: '',
  });

  const [members, setMembers] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const loadMembers = async () => {
      const data = await getMembers();
      setMembers(data);
    };

    const loadWorkout = async () => {
      if (isEditing) {
        const data = await getWorkoutPlanById(id);
        setWorkout(data);
      }
    };

    loadMembers();
    if (isEditing) loadWorkout();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setWorkout((prev) => ({ ...prev, [name]: value }));
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEditing) {
        await updateWorkoutPlan(id, workout);
        showMessage('Workout Plan Updated Successfully');
      } else {
        await addWorkoutPlan(workout);
        showMessage('Workout Plan Added Successfully');
      }
      navigate('/trainer/workouts');
    } catch (err) {
      showMessage(err.message || 'Error saving workout plan');
    }
  };

  return (
    <Layout>
      <div className="max-w-3xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">
          {isEditing ? 'Edit Workout Plan' : 'Add Workout Plan'}
        </h1>
        {message && (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
            {message}
          </div>
        )}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium">Workout Name</label>
            <input
              name="workout_name"
              value={workout.workout_name}
              onChange={handleChange}
              required
              className="mt-1 w-full border px-3 py-2 rounded shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium">Description</label>
            <textarea
              name="description"
              value={workout.description}
              onChange={handleChange}
              className="mt-1 w-full border px-3 py-2 rounded shadow-sm"
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium">Start Date</label>
              <input
                type="date"
                name="start_date"
                value={workout.start_date}
                onChange={handleChange}
                className="mt-1 w-full border px-3 py-2 rounded"
              />
            </div>
            <div>
              <label className="block text-sm font-medium">End Date</label>
              <input
                type="date"
                name="end_date"
                value={workout.end_date}
                onChange={handleChange}
                className="mt-1 w-full border px-3 py-2 rounded"
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium">Assign to Member</label>
            <select
              name="member_id"
              value={workout.member_id}
              onChange={handleChange}
              required
              className="mt-1 w-full border px-3 py-2 rounded"
            >
              <option value="">Select a Member</option>
              {members.map((member) => (
                <option key={member.member_id} value={member.member_id}>
                  {member.first_name} {member.last_name}
                </option>
              ))}
            </select>
          </div>

          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold px-6 py-2 rounded"
          >
            {isEditing ? 'Update Plan' : 'Add Plan'}
          </button>
        </form>
      </div>
    </Layout>
  );
};

export default AddEditWorkoutPlan;
