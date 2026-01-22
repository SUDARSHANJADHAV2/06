import { useEffect, useState } from 'react';
import api from '../api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const DashboardPage = () => {
  const [subscription, setSubscription] = useState(null);
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchStatus = async () => {
      try {
        const response = await api.get('/subscriptions/status');
        setSubscription(response.data);
      } catch (error) {
        console.error('Error fetching subscription status', error);
      }
    };
    fetchStatus();
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <nav className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16">
            <div className="flex items-center">
              <h1 className="text-xl font-bold text-gray-900">SaaS Platform</h1>
            </div>
            <div className="flex items-center">
              <span className="mr-4 text-gray-600">{user?.email}</span>
              <button
                onClick={handleLogout}
                className="text-sm font-medium text-red-600 hover:text-red-500"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>
      <main className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-2xl font-bold mb-6">Your Subscription</h2>
          {subscription ? (
            <div>
              <p className="text-lg">Status: <span className={`font-semibold ${subscription.status === 'active' ? 'text-green-600' : 'text-red-600'}`}>{subscription.status}</span></p>
              <p className="text-lg">Plan: <span className="font-semibold">{subscription.planName}</span></p>
              <p className="text-lg">Period End: <span className="font-semibold">{new Date(subscription.currentPeriodEnd).toLocaleDateString()}</span></p>
              {subscription.status !== 'active' && (
                <button
                  onClick={() => navigate('/pricing')}
                  className="mt-6 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                >
                  Upgrade Plan
                </button>
              )}
            </div>
          ) : (
            <div>
              <p className="text-gray-600">You don't have an active subscription.</p>
              <button
                onClick={() => navigate('/pricing')}
                className="mt-6 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
              >
                View Plans
              </button>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default DashboardPage;
