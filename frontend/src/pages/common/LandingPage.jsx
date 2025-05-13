import React from 'react';
import { Link } from 'react-router-dom';

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* HERO */}
      <div className="max-w-7xl mx-auto px-6 py-20 text-center">
        <h1 className="text-4xl md:text-6xl font-bold mb-6 leading-tight">
          Your Fitness. Your Control. 💪
        </h1>
        <p className="text-lg text-gray-600 max-w-xl mx-auto mb-8">
          Manage your gym. Track your progress. Elevate your health journey with our all-in-one fitness platform.
        </p>
        <Link
          to="/login"
          className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 text-lg rounded shadow"
        >
          Get Started
        </Link>
      </div>

      {/* FEATURES */}
      <div className="bg-white py-16 px-6">
        <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
          <Feature icon="🏋️" title="Trainer Dashboards" desc="Schedule, assign plans, and manage sessions." />
          <Feature icon="🧍" title="Member Portal" desc="Track progress, diet, attendance and enroll in classes." />
          <Feature icon="📊" title="Admin Insights" desc="Control memberships, monitor revenue, and analyze trends." />
        </div>
      </div>

      {/* TESTIMONIAL */}
      <div className="bg-indigo-50 py-16 text-center">
        <div className="max-w-xl mx-auto">
          <p className="text-xl font-semibold text-indigo-700">
            “The best gym management tool I’ve used. Everything just works.”
          </p>
          <p className="mt-4 text-gray-600">— Aditi Sharma, Gym Owner</p>
        </div>
      </div>

      {/* FOOTER */}
      <footer className="bg-white border-t py-6 mt-10 text-center text-sm text-gray-500">
        Built for modern gyms © {new Date().getFullYear()} | <Link to="/admin">Admin Login</Link>
      </footer>
    </div>
  );
};

const Feature = ({ icon, title, desc }) => (
  <div>
    <div className="text-4xl mb-2">{icon}</div>
    <h3 className="text-lg font-semibold">{title}</h3>
    <p className="text-gray-600 mt-1">{desc}</p>
  </div>
);

export default LandingPage;
