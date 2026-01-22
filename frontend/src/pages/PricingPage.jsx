import { useEffect, useState } from 'react';
import api from '../api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const PricingPage = () => {
  const [plans, setPlans] = useState([]);
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const response = await api.get('/subscriptions/plans');
        setPlans(response.data);
      } catch (error) {
        console.error('Error fetching plans', error);
      }
    };
    fetchPlans();
  }, []);

  const handleSubscribe = async (priceId) => {
    if (!user) {
      navigate('/login');
      return;
    }
    try {
      const response = await api.post('/subscriptions/checkout', { priceId });
      window.location.href = response.data.url;
    } catch (error) {
      alert('Checkout failed');
    }
  };

  return (
    <div className="bg-gray-100 min-h-screen py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <div className="text-center">
          <h2 className="text-3xl font-extrabold text-gray-900 sm:text-4xl">
            Simple, transparent pricing
          </h2>
          <p className="mt-4 text-xl text-gray-600">
            Choose the plan that's right for you.
          </p>
        </div>
        <div className="mt-12 grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
          {plans.length > 0 ? plans.map((plan) => (
            <div key={plan.id} className="bg-white rounded-lg shadow-lg overflow-hidden flex flex-col">
              <div className="px-6 py-8">
                <h3 className="text-2xl font-bold text-gray-900 text-center">{plan.name}</h3>
                <p className="mt-4 text-gray-500 text-center">{plan.description}</p>
                <p className="mt-8 text-center">
                  <span className="text-4xl font-extrabold text-gray-900">${plan.price}</span>
                  <span className="text-base font-medium text-gray-500">/{plan.interval}</span>
                </p>
                <button
                  onClick={() => handleSubscribe(plan.stripePriceId)}
                  className="mt-8 block w-full bg-blue-600 border border-transparent rounded-md py-2 text-sm font-semibold text-white text-center hover:bg-blue-700 transition"
                >
                  Subscribe to {plan.name}
                </button>
              </div>
            </div>
          )) : (
            <div className="col-span-3 text-center text-gray-500">
              No plans available at the moment.
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default PricingPage;
