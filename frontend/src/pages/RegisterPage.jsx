import { useNavigate, Link } from 'react-router-dom';
import { register } from '../api/auth';
import { useState } from 'react';
import styles from '../styles/AuthForm.module.css';

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
			navigate('/login');
		} catch {
			setError('No se pudo registrar. Comprueba los datos.');
		}
	}

	return (
		<div className={styles.container}>
			<div className={styles.card}>
				<h1 className={styles.title}>Register</h1>
				<form onSubmit={handleSubmit} className={styles.form}>
					<div className={styles.field}>
						<label className={styles.label}>Usuario</label>
						<input
								className={styles.input} 
								type="text"
								value={username}
								onChange={(e) => setUsername(e.target.value)}
								required
						/>
					</div>
					<div className={styles.field}>
						<label className={styles.label}>Email</label>
						<input
								className={styles.input}
								type="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								required
						/>
					</div>
					<div className={styles.field}>
						<label className={styles.label}>Contraseña</label>
						<input
								className={styles.input}
								type="password"
								value={password}
								onChange={(e) => setPassword(e.target.value)}
								required
						/>
					</div>
					{error && <p className={styles.error}>{error}</p>}
					<button type="submit" className={styles.button}>Registrar</button>
				</form>
				<p className={styles.link}>
					¿Ya tienes cuenta? <Link to="/login">Iniciar sesión</Link>
				</p>
			</div>
		</div>

	);
}

export default RegisterPage;