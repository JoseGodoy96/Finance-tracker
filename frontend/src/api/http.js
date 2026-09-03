const API_URL = 'http://localhost:8080'

export async function apiFetch(path, options = {}) {
	const token = localStorage.getItem('token');
	const headers = {
		'Content-Type': 'application/json' ,
		...(token && { 'Authorization': `Bearer ${token}` }),
		...options.headers
	};
	const response = await fetch(`${API_URL}${path}`, {
		...options,
		headers
	});

	if (response.status === 401 || response.status === 403) {
		localStorage.removeItem('token');
		window.location.href = '/login'
		return ;
	}

	return response;
}