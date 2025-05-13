import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "@/services/api"; // 👈 central access to everything

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const loginData = { email, password };
      const res = await ApiService.loginUser(loginData);
  
      if (res.token) {
        ApiService.saveToken(res.token);
        ApiService.saveRole(res.role);
        setMessage(res.message || "Login successful");
  
        console.log(res.token);
        console.log(res.role);
        console.log(res.message);
  
        // 🌟 Smart routing based on role
        const roleRouteMap = {
          ADMIN: "/admin",
          TRAINER: "/trainer",
          MEMBER: "/member",
        };
  
        const path = roleRouteMap[res.role] || "/login"; // fallback if role not found
        navigate(path);
        window.location.reload(); // re-evaluate auth state
      } else {
        showMessage(res.message || "Login failed. No token.");
      }
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Login error: " + error.message
      );
      console.error(error);
    }
  };
  

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(""), 4000);
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4">
      <div className="w-full max-w-md bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Login to Gym Portal
        </h2>

        {message && (
          <p className="bg-red-100 text-red-700 p-2 rounded text-sm mb-4 text-center">
            {message}
          </p>
        )}

        <form onSubmit={handleLogin} className="space-y-4">
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-indigo-500"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-indigo-500"
          />
          <button
            type="submit"
            className="w-full bg-indigo-600 text-white py-2 rounded font-semibold hover:bg-indigo-700 transition"
          >
            Login
          </button>
        </form>

        <p className="text-center text-sm text-gray-500 mt-4">
          Don’t have an account?{" "}
          <a
            href="/register"
            className="text-indigo-600 font-semibold hover:underline"
          >
            Register
          </a>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
