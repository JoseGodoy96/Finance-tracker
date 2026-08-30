import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Navigate to="/login" replace/>} />
				<Route path="/login" element={<div>Login (todavia por hacer)</div>} />
				<Route path="/register" element={<div>Register (todavia por hacer)</div>} />
				<Route path="/dashboard" element={<div>Dashboard (todavia por hacer)</div>} />
				<Route path="*" element={<div>404 - Página no encontrada</div>} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
