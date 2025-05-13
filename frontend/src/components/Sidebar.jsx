import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import {
  FaHome,
  FaUsers,
  FaUserTie,
  FaClipboardList,
  FaCalendarAlt,
  FaDumbbell,
  FaUtensils,
  FaComments,
  FaWallet,
  FaStar,
  FaCog,
  FaChevronDown,
  FaChevronRight,
  FaCheckCircle,
} from "react-icons/fa";
import { getRole } from "@/services/api/apiConfig";

const role = getRole();

const navStructure = {
  ADMIN: [
    {
      section: "Main",
      items: [{ to: "/admin", label: "Dashboard", icon: <FaHome /> }],
    },
    {
      section: "Management",
      items: [
        { to: "/admin/members", label: "Manage Members", icon: <FaUsers /> },
        {
          to: "/admin/trainers",
          label: "Manage Trainers",
          icon: <FaUserTie />,
        },
        { to: "/admin/staff", label: "Manage Staff", icon: <FaUserTie /> }, // ➡ Newly added Manage Staff
        {
          to: "/admin/trainer-approvals",
          label: "Approvals",
          icon: <FaCheckCircle />,
        },
        { to: "/admin/classes", label: "Classes", icon: <FaCalendarAlt /> },
        { to: "/admin/inventory", label: "Inventory", icon: <FaDumbbell /> },
      ],
    },
    {
      section: "Programs",
      items: [
        { to: "/admin/workouts", label: "Workout Plans", icon: <FaDumbbell /> },
        { to: "/admin/diets", label: "Diet Plans", icon: <FaUtensils /> },
      ],
    },
    {
      section: "Feedback & Reviews",
      items: [
        {
          to: "/admin/feedback",
          label: "Member Feedback",
          icon: <FaComments />,
        },
        { to: "/admin/ratings", label: "Trainer Ratings", icon: <FaStar /> },
      ],
    },
    {
      section: "Analytics & Finance",
      items: [
        {
          to: "/admin/analytics",
          label: "Class Analytics",
          icon: <FaClipboardList />,
        },
        { to: "/admin/revenue", label: "Revenue Report", icon: <FaWallet /> },
      ],
    },
    {
      section: "Settings",
      items: [{ to: "/profile", label: "Admin Profile", icon: <FaCog /> }],
    },
  ],
};

const Sidebar = () => {
  const location = useLocation();
  const items = navStructure[role] || [];

  const [openSections, setOpenSections] = useState({});

  const toggleSection = (section) => {
    setOpenSections((prev) => ({
      ...prev,
      [section]: !prev[section],
    }));
  };

  return (
    <aside className="w-64 h-screen bg-white dark:bg-gray-900 border-r shadow-sm hidden md:flex flex-col overflow-y-auto">
      <div className="h-16 flex items-center justify-center font-bold text-3xl text-gray-300">
        GymFlow
      </div>
      <nav className="flex flex-col px-4 space-y-1 mt-4">
        {items.map((group, idx) => (
          <div key={idx}>
            <button
              className="w-full flex items-center justify-between text-sm text-gray-500 uppercase tracking-wider py-4 hover:text-indigo-600"
              onClick={() => toggleSection(group.section)}
            >
              <span>{group.section}</span>
              {openSections[group.section] ? (
                <FaChevronDown size={12} />
              ) : (
                <FaChevronRight size={12} />
              )}
            </button>

            {openSections[group.section] && (
              <div className="space-y-1 ml-2 border-l border-gray-300 dark:border-gray-700 pl-4">
                {group.items.map((item, subIdx) => (
                  <Link
                    key={subIdx}
                    to={item.to}
                    className={`flex items-center gap-3 px-3 py-2 rounded-md text-sm text-gray-700 dark:text-gray-300 hover:bg-indigo-100 dark:hover:bg-indigo-900 transition ${
                      location.pathname === item.to
                        ? "bg-indigo-50 text-indigo-700 dark:bg-indigo-800"
                        : ""
                    }`}
                  >
                    {item.icon}
                    {item.label}
                  </Link>
                ))}
              </div>
            )}
          </div>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;
