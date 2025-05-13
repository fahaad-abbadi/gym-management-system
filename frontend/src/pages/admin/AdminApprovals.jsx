import React, { useEffect, useState } from 'react';
import { getPendingTrainers, approveTrainer, getPendingStaff, approveStaff } from '@/services/admin/adminService';
import { toast } from 'react-hot-toast';

const AdminApprovals = () => {
  const [pendingTrainers, setPendingTrainers] = useState([]);
  const [pendingStaff, setPendingStaff] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchAllApprovals = async () => {
    try {
      setLoading(true);
      const [trainersRes, staffRes] = await Promise.all([
        getPendingTrainers(),
        getPendingStaff(),
      ]);
      setPendingTrainers(trainersRes.users || []);
      console.log(trainersRes)
      setPendingStaff(staffRes.users || []);
    } catch (error) {
      console.error(error);
      toast.error("Failed to load pending approvals.");
    } finally {
      setLoading(false);
    }
  };

  const handleApproveTrainer = async (id) => {
    try {
      await approveTrainer(id);
      toast.success('Trainer approved successfully.');
      fetchAllApprovals();
    } catch (error) {
      toast.error('Failed to approve trainer.');
    }
  };

  const handleApproveStaff = async (id) => {
    try {
      await approveStaff(id);
      toast.success('Staff approved successfully.');
      fetchAllApprovals();
    } catch (error) {
      toast.error('Failed to approve staff.');
    }
  };

  useEffect(() => {
    fetchAllApprovals();
  }, []);

  return (
    <div className="max-w-6xl mx-auto mt-10 space-y-10">
      {/* Trainer Approvals */}
      <div>
        <h2 className="text-xl font-bold mb-4">Pending Trainer Approvals</h2>
        {loading ? (
          <p>Loading...</p>
        ) : pendingTrainers.length === 0 ? (
          <p>No pending trainers 🎉</p>
        ) : (
          <ApprovalList data={pendingTrainers} handleApprove={handleApproveTrainer} />
        )}
      </div>

      {/* Staff Approvals */}
      <div>
        <h2 className="text-xl font-bold mb-4">Pending Staff Approvals</h2>
        {loading ? (
          <p>Loading...</p>
        ) : pendingStaff.length === 0 ? (
          <p>No pending staff 🎉</p>
        ) : (
          <ApprovalList data={pendingStaff} handleApprove={handleApproveStaff} />
        )}
      </div>
    </div>
  );
};

const ApprovalList = ({ data, handleApprove }) => (
  <div className="bg-white rounded-lg shadow p-6 space-y-4">
    {data.map((item) => (
      <div key={item.id} className="flex justify-between items-center border-b py-3">
        <div>
          <p className="font-semibold">{item.firstName} {item.lastName}</p>
          <p className="text-sm text-gray-500">{item.email}</p>
        </div>
        <button
          onClick={() => handleApprove(item.id)}
          className="bg-green-500 hover:bg-green-600 text-white px-4 py-1 rounded text-sm"
        >
          Approve
        </button>
      </div>
    ))}
  </div>
);

export default AdminApprovals;
