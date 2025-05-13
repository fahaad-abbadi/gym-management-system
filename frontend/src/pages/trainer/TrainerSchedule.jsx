import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { getCurrentUser } from '@/services/api/authService';
import { getTrainerSchedules } from '@/services/trainer/trainerScheduleService';

const localizer = momentLocalizer(moment);

const TrainerSchedule = () => {
  const [events, setEvents] = useState([]);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadSchedule = async () => {
      try {
        const res = await getCurrentUser();
        const user = res.data.user;  // ✅ Correct: extracting properly

        console.log(user);

        if (user?.id) {  // ✅ Use "id" not "trainer_id"
          const schedulesRes = await getTrainerSchedules(user.id); // Pass id here
          const schedules = schedulesRes.data.schedules || [];

          const formatted = schedules.map((s) => ({
            id: s.scheduleId,
            title: s.sessionType.replace(/([A-Z])/g, ' $1'),
            start: new Date(s.sessionDate),
            end: new Date(new Date(s.sessionDate).getTime() + s.sessionDuration * 60000),
          }));

          setEvents(formatted);
        } else {
          setMessage('Trainer information not available.');
        }
      } catch (err) {
        console.error(err);
        setMessage('Failed to load trainer schedule.');
      } finally {
        setLoading(false);
      }
    };

    loadSchedule();
  }, []);

  return (
    <Layout>
      <div className="max-w-6xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">My Training Schedule</h1>

        {loading ? (
          <div className="text-center text-gray-500">Loading schedule...</div>
        ) : message ? (
          <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
            {message}
          </div>
        ) : (
          <div className="bg-white p-4 rounded shadow border">
            <Calendar
              localizer={localizer}
              events={events}
              startAccessor="start"
              endAccessor="end"
              style={{ height: 600 }}
              className="rbc-calendar"
            />
          </div>
        )}
      </div>
    </Layout>
  );
};

export default TrainerSchedule;
