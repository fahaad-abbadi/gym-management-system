import React, { useState, useEffect } from 'react';
import { getAllFeedbacks } from '@/services/member/feedbackService';
import { FaSearch } from 'react-icons/fa';
import { toast } from 'react-hot-toast';

const ManageFeedback = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchFeedbacks = async () => {
      try {
        const res = await getAllFeedbacks();
        const feedbackList = res.data.feedbacks || [];
        console.log(res.data.feedbacks);
        setFeedbacks(feedbackList);
      } catch (error) {
        console.error('Failed to load feedbacks:', error);
        toast.error('Failed to load feedbacks');
      } finally {
        setLoading(false);
      }
    };

    fetchFeedbacks();
  }, []);

  const filteredFeedbacks = feedbacks.filter((fb) =>
    fb.feedbackText?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Manage Member Feedback</h1>

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
        <div className="text-center py-10 text-gray-400">Loading feedback...</div>
      ) : filteredFeedbacks.length === 0 ? (
        <div className="text-center py-10 text-gray-400">No feedback found.</div>
      ) : (
        <div className="overflow-x-auto bg-white rounded-lg shadow">
          <table className="w-full table-auto text-sm">
            <thead className="bg-gray-100 text-gray-700">
              <tr>
                <th className="px-6 py-3 text-left">Member</th>
                <th className="px-6 py-3 text-left">Trainer</th>
                <th className="px-6 py-3 text-left">Feedback</th>
                <th className="px-6 py-3 text-left">Rating</th>
                <th className="px-6 py-3 text-left">Date</th>
              </tr>
            </thead>
            <tbody>
              {filteredFeedbacks.map((fb) => (
                <tr key={fb.feedbackId} className="border-b hover:bg-gray-50">
                  <td className="px-6 py-4">{fb.memberName || 'Unknown Member'}</td>
                  <td className="px-6 py-4">{fb.trainerName || 'Unknown Trainer'}</td>
                  <td className="px-6 py-4">{fb.feedbackText || 'No feedback'}</td>
                  <td className="px-6 py-4">{fb.rating || '-'}</td>
                  <td className="px-6 py-4">{new Date(fb.feedbackDate).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ManageFeedback;
