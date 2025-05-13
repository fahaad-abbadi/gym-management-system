import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { Bar, Doughnut } from 'react-chartjs-2';
import { getAllClasses, getAttendanceByClass } from '@/services/shared/classService';
import { Chart as ChartJS, BarElement, ArcElement, Tooltip, Legend, CategoryScale, LinearScale } from 'chart.js';

ChartJS.register(BarElement, ArcElement, Tooltip, Legend, CategoryScale, LinearScale);

const ClassAnalytics = () => {
  const [classes, setClasses] = useState([]);
  const [attendanceMap, setAttendanceMap] = useState({});
  const [message, setMessage] = useState('');

  useEffect(() => {
    const loadData = async () => {
      try {
        const classList = await getAllClasses();
        setClasses(classList);

        const attendanceData = {};
        for (const cls of classList) {
          const attendance = await getAttendanceByClass(cls.class_id);
          attendanceData[cls.class_name] = {
            total: attendance.length,
            attended: attendance.filter((a) => a.attended).length,
          };
        }

        setAttendanceMap(attendanceData);
      } catch (err) {
        setMessage('Failed to load attendance analytics.');
      }
    };

    loadData();
  }, []);

  const chartData = {
    labels: Object.keys(attendanceMap),
    datasets: [
      {
        label: 'Attended',
        data: Object.values(attendanceMap).map((data) => data.attended),
        backgroundColor: '#34d399', // green
      },
      {
        label: 'Total Enrolled',
        data: Object.values(attendanceMap).map((data) => data.total),
        backgroundColor: '#93c5fd', // blue
      },
    ],
  };

  const doughnutData = {
    labels: Object.keys(attendanceMap),
    datasets: [
      {
        data: Object.values(attendanceMap).map((data) =>
          data.total > 0 ? Math.floor((data.attended / data.total) * 100) : 0
        ),
        backgroundColor: ['#6366f1', '#22c55e', '#facc15', '#f87171', '#ec4899'],
      },
    ],
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto mt-10 space-y-10">
        <h1 className="text-2xl font-bold">Class Attendance Analytics</h1>

        {message && (
          <div className="bg-red-100 text-red-700 p-3 rounded">{message}</div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div className="bg-white p-6 rounded shadow">
            <h2 className="text-lg font-semibold mb-4">Attendance Count (Per Class)</h2>
            <Bar data={chartData} height={300} />
          </div>

          <div className="bg-white p-6 rounded shadow">
            <h2 className="text-lg font-semibold mb-4">Attendance % Share</h2>
            <Doughnut data={doughnutData} />
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default ClassAnalytics;
