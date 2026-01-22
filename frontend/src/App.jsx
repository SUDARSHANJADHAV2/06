import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import PricingPage from './pages/PricingPage';
import SuccessPage from './pages/SuccessPage';
import CancelPage from './pages/CancelPage';

const PrivateRoute = ({ children }) => {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/pricing" element={<PricingPage />} />
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <DashboardPage />
              </PrivateRoute>
            }
          />
          <Route path="/success" element={<SuccessPage />} />
          <Route path="/cancel" element={<CancelPage />} />
          <Route path="/" element={<Navigate to="/pricing" />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
