import React, { useState, useEffect } from 'react';
import { getAllFeedbacks } from '@/services/member/feedbackService';
import { FaSearch } from 'react-icons/fa';
import { toast } from 'react-hot-toast';

const ManageTrainerRatings = () => {
  const [ratings, setRatings] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRatings = async () => {
      try {
        const res = await getAllFeedbacks();
        const feedbackList = res.data.feedbacks || [];

        // Filter only feedbacks where trainerName exists (cleaner approach)
        const trainerRatings = feedbackList.filter(f => f.trainerName);
        console.log(trainerRatings)

        setRatings(trainerRatings);
      } catch (error) {
        console.error('Failed to load ratings:', error);
        toast.error('Failed to load ratings');
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
            <thead className="bg-gray-100 text-gray-700">
              <tr>
                <th className="px-6 py-3 text-left">Trainer</th>
                <th className="px-6 py-3 text-left">Rating</th>
                <th className="px-6 py-3 text-left">Feedback</th>
                <th className="px-6 py-3 text-left">Date</th>
              </tr>
            </thead>
            <tbody>
              {filteredRatings.map((rating) => (
                <tr key={rating.feedbackId} className="border-b hover:bg-gray-50">
                  <td className="px-6 py-4">{rating.trainerName || 'Unknown Trainer'}</td>
                  <td className="px-6 py-4">{rating.rating} / 5</td>
                  <td className="px-6 py-4">{rating.feedbackText || 'No feedback'}</td>
                  <td className="px-6 py-4">{new Date(rating.feedbackDate).toLocaleDateString()}</td>
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
