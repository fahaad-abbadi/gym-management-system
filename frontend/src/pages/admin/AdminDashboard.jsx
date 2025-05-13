import React, { useEffect, useState } from "react";
import Layout from "@/components/Layout";
import {
  getAllUsers,
  getAllTrainers,
  getAllMemberships,
  getAllClasses,
  getAllFeedbacks,
} from "@/services/admin/adminService";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  BarElement,
} from "chart.js";
import { Doughnut, Bar } from "react-chartjs-2";

ChartJS.register(
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  BarElement
);

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    members: 0,
    trainers: 0,
    classes: 0,
    feedbackCount: 0,
    paidRevenue: 0,
  });

  useEffect(() => {
    const loadStats = async () => {
      try {
        const [usersRes, trainersRes, membershipsRes, classesRes, feedbacksRes] = await Promise.all([
          getAllUsers(),
          getAllTrainers(),
          getAllMemberships(),
          getAllClasses(),
          getAllFeedbacks(),
        ]);
  
        const users = usersRes.data.users || [];
        const trainers = trainersRes.data.trainers || [];
        const memberships = membershipsRes.data.memberships || [];
        const classes = classesRes.data.classes || [];
        const feedbacks = feedbacksRes.data.feedbacks || [];
  
        const paidRevenue = memberships
          .filter((m) => m.paymentStatus === "PAID")  // (make sure enum matches exactly)
          .reduce((sum, m) => sum + (600 || 0), 600); // safer with (m.price || 0)
  
        setStats({
          members: users.length,
          trainers: trainers.length,
          classes: classes.length,
          feedbackCount: feedbacks.length,
          paidRevenue,
        });
      } catch (error) {
        console.error("Failed to load admin stats:", error);
      }
    };
  
    loadStats();
  }, []);
  

  return (
    <Layout>
      <div className="max-w-6xl mx-auto">
        {/* <h1 className="text-2xl font-bold mb-8 text-white">Dashboard</h1> */}

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
          <StatCard title="Total Members" value={stats.members} color="red" />
          <StatCard
            title="Active Trainers"
            value={stats.trainers}
            color="green"
          />
          <StatCard
            title="Active Classes"
            value={stats.classes}
            color="yellow"
          />
          <StatCard
            title="Feedback Received"
            value={stats.feedbackCount}
            color="blue"
          />
          <StatCard
            title="Revenue Collected"
            value={`$${stats.paidRevenue}`}
            color="red"
          />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white p-6 rounded shadow h-[350px] flex flex-col">
            <h2 className="text-lg font-semibold mb-4">Member/Trainer Ratio</h2>
            <div className="flex-grow">
              <Doughnut
                data={{
                  labels: ["Members", "Trainers"],
                  datasets: [
                    {
                      label: "Users",
                      data: [stats.members, stats.trainers],
                      backgroundColor: ["#6366f1", "#22c55e"],
                      borderWidth: 1,
                    },
                  ],
                }}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                }}
              />
            </div>
          </div>

          <div className="bg-white p-6 rounded shadow h-[350px] flex flex-col">
            <h2 className="text-lg font-semibold mb-4">Revenue Bar</h2>
            <div className="flex-grow">
              <Bar
                data={{
                  labels: ["Collected"],
                  datasets: [
                    {
                      label: "Revenue",
                      data: [stats.paidRevenue],
                      backgroundColor: "#8b5cf6",
                    },
                  ],
                }}
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  scales: {
                    y: {
                      beginAtZero: true,
                    },
                  },
                }}
              />
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

const StatCard = ({ title, value, color }) => (
  <div
    className={`bg-${color}-100 border-l-4 border-${color}-500 p-4 rounded shadow`}
  >
    <p className={`text-${color}-700 font-semibold`}>{title}</p>
    <h3 className="text-2xl font-bold">{value}</h3>
  </div>
);

export default AdminDashboard;
