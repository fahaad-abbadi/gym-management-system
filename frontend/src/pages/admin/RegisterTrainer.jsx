const RegisterTrainer = () => {
    const [form, setForm] = useState({ userName: '', email: '', password: '', phoneNumber: '' });
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      const data = { ...form, role: 'TRAINER' }; // ✅ important
      try {
        await registerUser(data);
        setMessage("Registration submitted. Await admin approval.");
        setTimeout(() => navigate("/login"), 1500);
      } catch (err) {
        console.error(err);
        setMessage(err.response?.data?.message || "Error");
      }
    };
  
    return (
      <form onSubmit={handleSubmit}>
        {/* inputs for userName, email, phoneNumber, password */}
        <button>Register as Trainer</button>
        {message && <p>{message}</p>}
      </form>
    );
  };
  