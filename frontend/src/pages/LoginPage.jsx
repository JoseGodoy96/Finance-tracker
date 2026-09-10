import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { login } from '../api/auth';
import styles from './LoginPage.module.css';

function LoginPage() {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const navigate = useNavigate();

	async function handleSubmit(e) {
		e.preventDefault();
		setError('');

		try {
			const data = await login(username, password);
			localStorage.setItem('token', data.token);
			navigate('/dashboard');
		} catch (err) {
			setError('Usuario o contraseña incorrecta' + err);
		}
	}

	return (
		<div className={styles.container}>
			<div className={styles.card}>
				<h1 className={styles.title}>Login</h1>
				<form onSubmit={handleSubmit} className={styles.form}>
					<div className={styles.field}>
						<label className={styles.label}>Usuario:</label>
						<input
							className={styles.input}
							type="text"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
							required
						/>
					</div>
					<div className={styles.field}>
						<label className={styles.label}>Contraseña:</label>
						<input
							className={styles.input}
							type="password"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
							required
						/>
					</div>
					{error && <p className={styles.error}>{error}</p>}
					<button type="submit" className={styles.button}>Entrar</button>
				</form>
				<p className={styles.link}>
					¿No tienes cuenta? <Link to="/register">Regístrate</Link>
				</p>
			</div>
        </div>
	);
}

export default LoginPage;