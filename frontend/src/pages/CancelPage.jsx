import { useNavigate } from 'react-router-dom';

const CancelPage = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md text-center">
        <h2 className="text-3xl font-bold text-red-600 mb-4">Payment Cancelled</h2>
        <p className="text-gray-600">Your payment process was cancelled. No charges were made.</p>
        <button
          onClick={() => navigate('/pricing')}
          className="mt-6 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
        >
          Back to Pricing
        </button>
      </div>
    </div>
  );
};

export default CancelPage;
