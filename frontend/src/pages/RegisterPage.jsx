import { useNavigate, Link } from 'react-router-dom';
import { register } from '../api/auth';
import { useState } from 'react';

function RegisterPage() {
	const [username, setUsername] = useState('');
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const navigate = useNavigate();
	
	async function handleSubmit(e) {
		e.preventDefault();
		setError('');

		try {
			await register(username, email, password);
			navigate('/dashboard');
		} catch {
			setError('No se pudo registrar. Comprueba los datos.');
		}
	}

	return (
		<div style={{ maxWidth: 400, margin: '2rem auto', padding: '1rem' }}>
			<h1>Register</h1>
			<form onSubmit={handleSubmit}>
				<div>
					<label>Usuario</label>
					<input type="text"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
							required
					/>
				</div>
				<div>
					<label>Email</label>
					<input type="email"
							value={email}
							onChange={(e) => setEmail(e.target.value)}
							required
					/>
				</div>
				<div>
					<label>Contraseña</label>
					<input type="password"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
							required
					/>
				</div>
				{error && <p style={{ color: 'red' }}>{error}</p>}
				<button type="submit">Registrar</button>
			</form>
			<p>
				¿Ya tienes cuenta? <Link to="/login">Iniciar sesión</Link>
			</p>
		</div>
	);
}

export default RegisterPage;