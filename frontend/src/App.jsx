import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProtectedRoute from '@/services/api/Guard';

import LoginPage from './pages/auth/LoginPage';
import RegisterPage from './pages/auth/RegisterPage';
import LandingPage from './pages/common/LandingPage';

// Dashboards
import AdminDashboard from './pages/admin/AdminDashboard';
import TrainerDashboard from './pages/trainer/TrainerDashboard';
import MemberDashboard from './pages/member/MemberDashboard';

// Admin Pages
import InventoryPage from './pages/admin/InventoryPage';
import ClassAnalytics from './pages/admin/ClassAnalytics';
import MembershipPage from './pages/member/MembershipPage';
import AdminManageUsers from './pages/admin/AdminManageUsers';

import ManageMembers from '@/pages/admin/management/ManageMembers';
import ManageTrainers from '@/pages/admin/management/ManageTrainers';
import ManageClasses from '@/pages/admin/management/ManageClasses';
import ManageInventory from '@/pages/admin/management/ManageInventory';
import ManageWorkouts from '@/pages/admin/management/ManageWorkouts';
import ManageDiets from '@/pages/admin/management/ManageDiets';

import AdminTrainerApproval from './pages/admin/AdminTrainerApproval';

import ManageFeedback from '@/pages/admin/management/ManageFeedback';
import ManageTrainerRatings from '@/pages/admin/management/ManageTrainerRatings';

// Trainer Pages
import WorkoutPlans from './pages/trainer/WorkoutPlans';
import AddEditWorkoutPlan from './pages/trainer/AddEditWorkoutPlan';
import TrainerClasses from './pages/trainer/TrainerClasses';
import AddEditClass from './pages/trainer/AddEditClass';
import TrainerSchedule from './pages/trainer/TrainerSchedule';

// Member Pages
import ClassEnrollment from './pages/member/ClassEnrollment';
import SubmitFeedback from './pages/member/SubmitFeedback';

function App() {
  return (
    <Router>
      <Routes>
        {/* Public Pages */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* Admin Routes */}
        <Route path="/admin" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<AdminDashboard />} />} />
        <Route path="/admin/inventory" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<InventoryPage />} />} />
        <Route path="/admin/analytics" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ClassAnalytics />} />} />
        <Route path="/admin/manage-users" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<AdminManageUsers />} />}/>

        <Route path="/admin/members" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageMembers />} />} />
        <Route path="/admin/trainers" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageTrainers />} />} />
        <Route path="/admin/classes" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageClasses />} />} />
        <Route path="/admin/inventory" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageInventory />} />} />
        <Route path="/admin/workouts" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageWorkouts />} />} />
        <Route path="/admin/diets" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageDiets />} />} />

        <Route path="/admin/trainer-approvals" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<AdminTrainerApproval />} />} />

        <Route path="/admin/feedback" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageFeedback />} />} />
        <Route path="/admin/ratings" element={<ProtectedRoute allowedRoles={['ADMIN']} element={<ManageTrainerRatings />} />} />

        {/* Trainer Routes */}
        <Route path="/trainer" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<TrainerDashboard />} />} />
        <Route path="/trainer/workouts" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<WorkoutPlans />} />} />
        <Route path="/trainer/workouts/add" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<AddEditWorkoutPlan />} />} />
        <Route path="/trainer/workouts/edit/:id" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<AddEditWorkoutPlan />} />} />
        <Route path="/trainer/classes" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<TrainerClasses />} />} />
        <Route path="/trainer/classes/add" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<AddEditClass />} />} />
        <Route path="/trainer/classes/edit/:id" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<AddEditClass />} />} />
        <Route path="/trainer/schedule" element={<ProtectedRoute allowedRoles={['TRAINER']} element={<TrainerSchedule />} />} />

        {/* Member Routes */}
        <Route path="/member" element={<ProtectedRoute allowedRoles={['MEMBER']} element={<MemberDashboard />} />} />
        <Route path="/member/enroll" element={<ProtectedRoute allowedRoles={['MEMBER']} element={<ClassEnrollment />} />} />
        <Route path="/member/feedback" element={<ProtectedRoute allowedRoles={['MEMBER']} element={<SubmitFeedback />} />} />
        <Route path="/member/membership" element={<ProtectedRoute allowedRoles={['MEMBER']} element={<MembershipPage />} />} />

        {/* Fallback */}
        <Route path="*" element={<LoginPage />} />
      </Routes>
    </Router>
  );
}

export default App;
