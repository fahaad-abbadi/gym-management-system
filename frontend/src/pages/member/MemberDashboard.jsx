import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { getCurrentUser } from '@/services/api/authService';
import {
  getWorkoutPlansByMember,
  getDietPlansByMember,
  getAttendanceByMember,
} from '@/services/member/memberService';

const MemberDashboard = () => {
  const [member, setMember] = useState(null);
  const [workouts, setWorkouts] = useState([]);
  const [diets, setDiets] = useState([]);
  const [attendance, setAttendance] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const loadData = async () => {
      try {
        const user = await getCurrentUser();
        setMember(user);

        const [wp, dp, att] = await Promise.all([
          getWorkoutPlansByMember(user.member_id),
          getDietPlansByMember(user.member_id),
          getAttendanceByMember(user.member_id),
        ]);

        setWorkouts(wp);
        setDiets(dp);
        setAttendance(att);
      } catch (err) {
        setMessage('Error loading dashboard data.');
      }
    };

    loadData();
  }, []);

  const attendanceRate = attendance.length
    ? Math.floor(
        (attendance.filter((a) => a.attended).length / attendance.length) * 100
      )
    : 0;

  return (
    <Layout>
      <div className="max-w-6xl mx-auto mt-10 space-y-10">
        <h1 className="text-2xl font-bold">Welcome back, {member?.first_name} 👋</h1>

        {message && (
          <div className="bg-red-100 text-red-700 p-3 rounded">{message}</div>
        )}

        <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <DashboardCard
            title="Workout Plans"
            count={workouts.length}
            icon="🏋️"
            link="/member/workouts"
          />
          <DashboardCard
            title="Diet Plans"
            count={diets.length}
            icon="🥗"
            link="/member/diet"
          />
          <DashboardCard
            title="Attendance Rate"
            count={`${attendanceRate}%`}
            icon="📅"
            link="/member/attendance"
          />
        </section>

        <section>
          <h2 className="text-xl font-semibold mb-3">Current Workout Plan</h2>
          {workouts.length > 0 ? (
            <div className="bg-white p-4 rounded shadow border">
              <h3 className="text-lg font-semibold">{workouts[0].workout_name}</h3>
              <p className="text-sm text-gray-600">{workouts[0].description}</p>
              <p className="text-sm mt-2 text-gray-500">
                🗓 {workouts[0].start_date} → {workouts[0].end_date}
              </p>
            </div>
          ) : (
            <p className="text-gray-500">No workout plan assigned yet.</p>
          )}
        </section>

        <section>
          <h2 className="text-xl font-semibold mb-3">Current Diet Plan</h2>
          {diets.length > 0 ? (
            <div className="bg-white p-4 rounded shadow border">
              <h3 className="text-lg font-semibold">{diets[0].plan_name}</h3>
              <p className="text-sm text-gray-600">{diets[0].description}</p>
              <p className="text-sm mt-2 text-gray-500">
                🗓 {diets[0].start_date} → {diets[0].end_date}
              </p>
            </div>
          ) : (
            <p className="text-gray-500">No diet plan assigned yet.</p>
          )}
        </section>
      </div>
    </Layout>
  );
};

const DashboardCard = ({ title, count, icon, link }) => (
  <a
    href={link}
    className="bg-white shadow-md hover:shadow-lg p-6 rounded border transition-all cursor-pointer"
  >
    <div className="text-4xl mb-2">{icon}</div>
    <h3 className="text-lg font-semibold">{title}</h3>
    <p className="text-gray-700 text-xl">{count}</p>
  </a>
);

export default MemberDashboard;
