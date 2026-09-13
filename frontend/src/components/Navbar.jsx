import { Link, useNavigate } from 'react-router-dom';


function Navbar() {
	const navigate = useNavigate();

	function handleLogout() {
		localStorage.removeItem('token');
		navigate('/login');
	}

	return (
		<nav>
			<div>
				<Link to="/dashboard">Dashboard</Link>
				<Link to="/categories">Categories</Link>
				<Link to="/transactions">Transactions</Link>
			</div>
			<button onClick={handleLogout}>Logout</button>
		</nav>
	);
}

export default Navbar;