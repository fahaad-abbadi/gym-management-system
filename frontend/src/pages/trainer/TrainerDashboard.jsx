import React, { useEffect, useState } from "react";
import Layout from "@/components/Layout";
import { getCurrentUser } from "@/services/api/authService";
import { getUpcomingSessionsByTrainer } from "@/services/trainer/trainerService";
import { getWorkoutPlansByTrainer } from "@/services/trainer/trainerService";
import { getAllFeedbacks } from "@/services/member/feedbackService";
import { Link } from "react-router-dom";

const TrainerDashboard = () => {
  const [trainer, setTrainer] = useState(null);
  const [plans, setPlans] = useState([]);
  const [sessions, setSessions] = useState([]);
  const [feedbacks, setFeedbacks] = useState([]);

  useEffect(() => {
    const loadDashboard = async () => {
      const res = await getCurrentUser();
      console.log(res.data.user.id);
      setTrainer(res.data.user);

      const [planData, scheduleData, feedbackData] = await Promise.all([
        getWorkoutPlansByTrainer(res.data.user.id),
        getUpcomingSessionsByTrainer(res.data.user.id),
        getAllFeedbacks(),
      ]);

      setPlans(planData || []);
      setSessions(scheduleData || []);
      setFeedbacks(
        (feedbackData || []).filter((fb) => fb.trainerId === res.data.user.id)
      );
    };

    console.log(plans)

    loadDashboard();
  }, []);

  const upcomingSessions = sessions
    .filter((s) => new Date(s.sessionDate) > new Date())
    .slice(0, 5);

  const avgRating = feedbacks.length
    ? (
        feedbacks.reduce((acc, cur) => acc + cur.rating, 0) / feedbacks.length
      ).toFixed(1)
    : "N/A";

  return (
    <Layout>
      <div className="max-w-7xl mx-auto mt-10 px-4 space-y-10">
        <h1 className="text-4xl font-bold text-gray-800">
          Welcome Back,{" "}
          <span className="text-indigo-600">{trainer?.userName}</span>
        </h1>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <StatCard
            label="Workout Plans"
            value={plans.length}
            icon="🏋️"
            gradientFrom="from-purple-400"
            gradientTo="to-purple-600"
          />
          <StatCard
            label="Upcoming Sessions"
            value={upcomingSessions.length}
            icon="📅"
            gradientFrom="from-green-400"
            gradientTo="to-green-600"
          />
          <StatCard
            label="Average Rating"
            value={`⭐ ${avgRating}`}
            icon="⭐"
            gradientFrom="from-yellow-400"
            gradientTo="to-yellow-500"
          />
        </div>

        <div>
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">
            Upcoming Sessions
          </h2>
          {upcomingSessions.length === 0 ? (
            <p className="text-gray-500">No upcoming sessions scheduled.</p>
          ) : (
            <div className="bg-white p-6 rounded shadow space-y-3">
              {upcomingSessions.map((s, idx) => (
                <div key={idx} className="flex justify-between items-center">
                  <div>
                    <p className="font-semibold">
                      {s.sessionType} with Member #
                      {s.trainersMemberSchedule?.memberId}
                    </p>
                    <p className="text-sm text-gray-500">
                      {new Date(s.sessionDate).toLocaleString()}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        <div>
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">
            Quick Actions
          </h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <QuickAction to="/trainer/workouts" label="Manage Workout Plans" />
            <QuickAction to="/trainer/classes" label="Manage Classes" />
            <QuickAction to="/trainer/schedule" label="My Schedule" />
            <QuickAction to="/trainer/workouts/add" label="Create New Plan" />
          </div>
        </div>
      </div>
    </Layout>
  );
};

const StatCard = ({ label, value, icon, gradientFrom, gradientTo }) => (
  <div
    className={`bg-gradient-to-r ${gradientFrom} ${gradientTo} p-6 rounded-xl shadow text-white text-center`}
  >
    <div className="text-4xl mb-3">{icon}</div>
    <p className="text-lg">{label}</p>
    <h3 className="text-3xl font-bold mt-2">{value}</h3>
  </div>
);

const QuickAction = ({ to, label }) => (
  <Link
    to={to}
    className="bg-indigo-600 hover:bg-indigo-700 text-white py-3 px-4 rounded-lg shadow text-center font-medium transition"
  >
    {label}
  </Link>
);

export default TrainerDashboard;
