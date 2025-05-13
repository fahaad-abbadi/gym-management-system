import React, { useState, useEffect } from 'react';
import { getAllFeedbacks } from '@/services/member/feedbackService'; // use feedback service
import { FaSearch } from 'react-icons/fa';

const ManageTrainerRatings = () => {
  const [ratings, setRatings] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRatings = async () => {
      try {
        const res = await getAllFeedbacks();
        const feedbackList = res.data.feedbacks || [];

        console.log(res)

        // Filter only feedbacks which have trainer_id (trainer feedbacks)
        const trainerRatings = feedbackList.filter(f => f.trainerId !== null);

        setRatings(trainerRatings);
      } catch (error) {
        console.error('Failed to load ratings:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchRatings();
  }, []);

  const filteredRatings = ratings.filter((r) =>
    r.feedbackText?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Trainer Ratings</h1>

      <div className="flex items-center mb-6">
        <div className="relative w-full md:w-1/3">
          <FaSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search feedback..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-md border border-gray-300 focus:ring-2 focus:ring-indigo-400 focus:outline-none"
          />
        </div>
      </div>

      {loading ? (
        <div className="text-center py-10 text-gray-400">Loading ratings...</div>
      ) : filteredRatings.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No ratings found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left">Trainer ID</th>
                <th className="px-6 py-3 text-left">Rating</th>
                <th className="px-6 py-3 text-left">Feedback</th>
                <th className="px-6 py-3 text-left">Date</th>
              </tr>
            </thead>
            <tbody>
              {filteredRatings.map((rating) => (
                <tr key={rating.feedbackId} className="border-b">
                  <td className="px-6 py-4">{rating.trainerId}</td>
                  <td className="px-6 py-4">{rating.rating} / 5</td>
                  <td className="px-6 py-4">{rating.feedbackText || 'No feedback'}</td>
                  <td className="px-6 py-4">{rating.feedbackDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ManageTrainerRatings;
