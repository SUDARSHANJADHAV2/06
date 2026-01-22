import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const SuccessPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate('/dashboard');
    }, 5000);
    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md text-center">
        <h2 className="text-3xl font-bold text-green-600 mb-4">Payment Successful!</h2>
        <p className="text-gray-600">Thank you for your subscription. Your account is being updated.</p>
        <p className="mt-4 text-sm text-gray-500">Redirecting to dashboard in 5 seconds...</p>
        <button
          onClick={() => navigate('/dashboard')}
          className="mt-6 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
        >
          Go to Dashboard Now
        </button>
      </div>
    </div>
  );
};

export default SuccessPage;
