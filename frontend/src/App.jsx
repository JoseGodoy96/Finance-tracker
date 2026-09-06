import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import PrivateRoute from './components/PrivateRoute';
import CategoriesPage from './pages/CategoriesPage';

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Navigate to="/login" replace/>} />
				<Route path="/login" element={<LoginPage />} />
				<Route path="/register" element={<RegisterPage />} />
				<Route path="/dashboard" element={
					<PrivateRoute>
						<div>Dashboard (todavia por hacer)</div>
					</PrivateRoute>
					} />
				<Route path="/categories" element={
					<PrivateRoute>
						<CategoriesPage />
					</PrivateRoute>
					} />
				<Route path="*" element={<div>404 - Página no encontrada</div>} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
