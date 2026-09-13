import { Link, useNavigate } from 'react-router-dom';
import styles from '../styles/Navbar.module.css';

function Navbar() {
	const navigate = useNavigate();

	function handleLogout() {
		localStorage.removeItem('token');
		navigate('/login');
	}

	return (
		<nav className={styles.navbar}>
			<div className={styles.links}>
				<Link to="/dashboard" className={styles.link}>Dashboard</Link>
				<Link to="/categories" className={styles.link}>Categories</Link>
				<Link to="/transactions" className={styles.link}>Transactions</Link>
			</div>
			<button onClick={handleLogout} className={styles.logout}>Logout</button>
		</nav>
	);
}

export default Navbar;