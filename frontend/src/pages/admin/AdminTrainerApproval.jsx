import React, { useEffect, useState } from 'react';
import { getPendingTrainers, approveTrainer } from '@/services/admin/adminService';
import { toast } from 'react-hot-toast';

const AdminTrainerApproval = () => {
  const [pendingTrainers, setPendingTrainers] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchPendingTrainers = async () => {
    try {
      setLoading(true);
      const res = await getPendingTrainers();
      console.log(res.users)
      setPendingTrainers(res.users || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load pending trainers.");
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async (trainerId) => {
    try {
      await approveTrainer(trainerId);
      toast.success('Trainer approved successfully.');
      fetchPendingTrainers();
    } catch (error) {
      console.error(error);
      toast.error('Failed to approve trainer.');
    }
  };

  useEffect(() => {
    fetchPendingTrainers();
  }, []);

  return (
    <div className="max-w-5xl mx-auto mt-10">
      <h1 className="text-2xl font-bold mb-8 text-gray-700">Pending Trainer Approvals</h1>

      {loading ? (
        <p className="text-center text-gray-400">Loading...</p>
      ) : pendingTrainers.length === 0 ? (
        <p className="text-center text-gray-500">No pending trainers 🎉</p>
      ) : (
        <div className="bg-white rounded-lg shadow p-6 space-y-4">
          {pendingTrainers.map((trainer) => (
            <div key={trainer.id} className="flex justify-between items-center border-b py-3">
              <div>
                <p className="font-semibold">{trainer.firstName} {trainer.lastName}</p>
                <p className="text-sm text-gray-500">{trainer.email}</p>
              </div>
              <button
                onClick={() => handleApprove(trainer.id)}
                className="bg-green-500 hover:bg-green-600 text-white px-4 py-1 rounded text-sm"
              >
                Approve
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AdminTrainerApproval;
