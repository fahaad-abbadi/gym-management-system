import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '@/services/api/authService'; // ✅ directly using registerUser

const RegisterPage = () => {
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [message, setMessage] = useState('');

  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const registerData = { userName, email, password, phoneNumber };
      console.log(registerData)
      const res = await registerUser(registerData); // ✅ use the imported function directly
      console.log(res); // just in case you want to see server response

      setMessage('Registration successful!');
      setTimeout(() => navigate('/login'), 1500); // smoother UX
    } catch (error) {
      showMessage(
        error.response?.data?.message || 'Error Registering: ' + error.message
      );
      console.error(error);
    }
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 4000);
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4">
      <div className="w-full max-w-md bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">
          Create Your Account
        </h2>

        {message && (
          <p className="bg-green-100 text-green-700 p-2 text-sm rounded mb-4 text-center">
            {message}
          </p>
        )}

        <form onSubmit={handleRegister} className="space-y-4">
          <input
            type="text"
            placeholder="Full Name"
            value={userName}
            onChange={(e) => setName(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:ring-2 ring-indigo-300"
          />

          <input
            type="email"
            placeholder="Email Address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:ring-2 ring-indigo-300"
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:ring-2 ring-indigo-300"
          />

          <input
            type="text"
            placeholder="Phone Number"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            required
            className="w-full px-4 py-2 border rounded focus:ring-2 ring-indigo-300"
          />

          <button
            type="submit"
            className="w-full bg-indigo-600 text-white py-2 rounded font-semibold hover:bg-indigo-700 transition"
          >
            Register
          </button>
        </form>

        <p className="text-center text-sm text-gray-500 mt-4">
          Already have an account?{' '}
          <a href="/login" className="text-indigo-600 font-semibold hover:underline">
            Login
          </a>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
