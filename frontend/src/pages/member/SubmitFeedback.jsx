import React, { useEffect, useState } from 'react';
import Layout from "@/components/Layout";
import { getAllTrainers } from '@/services/trainer/trainerService';
import { postFeedback } from '@/services/member/feedbackService';
import { getCurrentUser } from '@/services/api/authService';

const SubmitFeedback = () => {
  const [trainers, setTrainers] = useState([]);
  const [member, setMember] = useState(null);
  const [formData, setFormData] = useState({
    trainer_id: '',
    feedback_text: '',
    rating: 0,
  });
  const [message, setMessage] = useState('');

  useEffect(() => {
    const loadData = async () => {
      const [trainerData, memberData] = await Promise.all([
        getTrainers(),
        getCurrentUser(),
      ]);
      setTrainers(trainerData);
      setMember(memberData);
    };

    loadData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...formData,
        member_id: member.member_id,
        feedbackDate: new Date().toISOString(),
      };
      await postFeedback(payload);
      showMessage('Feedback submitted successfully!');
      setFormData({ trainer_id: '', feedback_text: '', rating: 0 });
    } catch (err) {
      showMessage(err.message || 'Failed to submit feedback.');
    }
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  return (
    <Layout>
      <div className="max-w-3xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">Submit Feedback</h1>

        {message && (
          <div className="bg-blue-100 text-blue-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm font-medium">Trainer</label>
            <select
              name="trainer_id"
              value={formData.trainer_id}
              onChange={(e) =>
                setFormData({ ...formData, trainer_id: e.target.value })
              }
              required
              className="w-full px-3 py-2 border rounded"
            >
              <option value="">Select a Trainer</option>
              {trainers.map((trainer) => (
                <option key={trainer.trainer_id} value={trainer.trainer_id}>
                  {trainer.first_name} {trainer.last_name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium">Your Feedback</label>
            <textarea
              value={formData.feedback_text}
              onChange={(e) =>
                setFormData({ ...formData, feedback_text: e.target.value })
              }
              rows="4"
              className="w-full px-3 py-2 border rounded"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">
              Rating (1-5 Stars)
            </label>
            <div className="flex gap-1 text-yellow-400 text-2xl">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  type="button"
                  key={star}
                  onClick={() =>
                    setFormData({ ...formData, rating: star })
                  }
                  className={
                    star <= formData.rating ? 'text-yellow-500' : 'text-gray-300'
                  }
                >
                  ★
                </button>
              ))}
            </div>
          </div>

          <button
            type="submit"
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold px-6 py-2 rounded"
          >
            Submit Feedback
          </button>
        </form>
      </div>
    </Layout>
  );
};

export default SubmitFeedback;
