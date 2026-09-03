import { apiFetch } from "./http";

export async function login(username, password) {
	const response = await apiFetch("/api/auth/login", {
		method: 'POST',
		body: JSON.stringify({ username, password })
    });

	if (!response.ok) {
		throw new Error('Login failed');
	}

	return await response.json();
}

export async function register(username, email, password) {
	const response = await apiFetch("/api/auth/register", {
		method: 'POST',
		body: JSON.stringify({ username, email, password })
	});

	if (!response.ok) {
		throw new Error('Register failed');
	}

	return await response.json();
}