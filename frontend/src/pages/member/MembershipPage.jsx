import React, { useEffect, useState } from 'react';
import Layout from '@/components/Layout';
import {
  getCurrentUser,
  getMembershipsByMember,
  createMembership,
} from '@/services/member/membershipService';
import { paymentStatuses } from '@/services/shared/enumService';

const MembershipPage = () => {
  const [member, setMember] = useState(null);
  const [memberships, setMemberships] = useState([]);
  const [message, setMessage] = useState('');
  const [form, setForm] = useState({
    durationMonths: 1,
    price: 50,
    paymentStatus: 'Paid',
  });

  useEffect(() => {
    const loadData = async () => {
      const memberData = await getCurrentUser();
      const membershipData = await getMembershipsByMember(memberData.member_id);
      setMember(memberData);
      setMemberships(membershipData);
    };

    loadData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const today = new Date();
      const endDate = new Date();
      endDate.setMonth(today.getMonth() + parseInt(form.durationMonths));

      const membershipPayload = {
        member_id: member.member_id,
        start_date: today.toISOString().split('T')[0],
        end_date: endDate.toISOString().split('T')[0],
        price: form.price,
        paymentStatus: form.paymentStatus,
      };

      await createMembership(membershipPayload);
      setForm({ durationMonths: 1, price: 50, paymentStatus: 'Paid' });
      setMessage('Membership activated successfully.');
    } catch (err) {
      setMessage('Failed to activate membership.');
    }
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto mt-10">
        <h1 className="text-2xl font-bold mb-6">My Memberships</h1>

        {message && (
          <div className="bg-green-100 text-green-800 p-3 rounded mb-4">
            {message}
          </div>
        )}

        <div className="bg-white border p-4 rounded mb-10 shadow">
          <h2 className="text-lg font-semibold mb-4">Purchase New Membership</h2>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium">Duration (Months)</label>
              <select
                value={form.durationMonths}
                onChange={(e) =>
                  setForm({ ...form, durationMonths: e.target.value, price: e.target.value * 50 })
                }
                className="w-full px-3 py-2 border rounded"
              >
                {[1, 3, 6, 12].map((m) => (
                  <option key={m} value={m}>
                    {m} Month{m > 1 ? 's' : ''}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium">Price</label>
              <input
                type="text"
                readOnly
                value={`$${form.price}`}
                className="w-full px-3 py-2 border bg-gray-100 rounded"
              />
            </div>

            <div>
              <label className="block text-sm font-medium">Payment Status</label>
              <select
                value={form.paymentStatus}
                onChange={(e) => setForm({ ...form, paymentStatus: e.target.value })}
                className="w-full px-3 py-2 border rounded"
              >
                {paymentStatuses.map((status) => (
                  <option key={status.value} value={status.value}>
                    {status.label}
                  </option>
                ))}
              </select>
            </div>

            <button
              type="submit"
              className="bg-indigo-600 text-white px-6 py-2 rounded font-semibold hover:bg-indigo-700"
            >
              Activate Membership
            </button>
          </form>
        </div>

        <div>
          <h2 className="text-lg font-semibold mb-3">Past Memberships</h2>
          {memberships.length === 0 ? (
            <p className="text-gray-500">No previous memberships found.</p>
          ) : (
            <div className="grid gap-4">
              {memberships.map((m) => (
                <div
                  key={m.membership_id}
                  className="bg-white border p-4 rounded shadow flex justify-between items-center"
                >
                  <div>
                    <p>
                      🗓 {m.start_date} → {m.end_date}
                    </p>
                    <p className="text-sm text-gray-500">💰 ${m.price}</p>
                  </div>
                  <span
                    className={`px-3 py-1 rounded text-sm font-medium ${
                      m.paymentStatus === 'Paid'
                        ? 'bg-green-100 text-green-700'
                        : 'bg-yellow-100 text-yellow-700'
                    }`}
                  >
                    {m.paymentStatus}
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </Layout>
  );
};

export default MembershipPage;
