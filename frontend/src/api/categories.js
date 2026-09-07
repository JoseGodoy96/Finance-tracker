import { apiFetch } from "./http";

export const getCategories = async () => {
	const response = await apiFetch("/api/categories");
	if (!response.ok) {
		throw new Error("Error");
	}
	return await response.json();
}

export const createCategory = async (name, type) => {
	
	const response = await apiFetch("/api/categories", {
		method: 'POST',
		body: JSON.stringify({ name, type }),
	});
	if (!response.ok) {
		throw new Error('Create category failed');
	}
	return await response.json();
}

export const deleteCategory = async (id) => {

	const response = await apiFetch(`/api/categories/${id}`, {
		method: 'DELETE'
	});
	if (!response.ok) {
		throw new Error('Delete category failed');
	}

	return;
}