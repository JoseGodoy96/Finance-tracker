import { apiFetch } from "./http";

export const getTransactions = async () => {
	
	const response = await apiFetch("/api/transactions");
	if (!response.ok) {
		throw new Error("Error");
	}
	return await response.json();
}

export const createTransaction = async (transaction, categoryId) => {
	
	const response = await apiFetch(`/api/transactions?categoryId=${categoryId}`, {
		method: 'POST',
		body: JSON.stringify( transaction ),
	});
	if (!response.ok) {
		throw new Error('Create transaction failed');
	}
	return await response.json();
}

export const updateTransaction = async (id, transaction, categoryId) => {
	
	const response = await apiFetch(`/api/transactions/${id}?categoryId=${categoryId}`, {
		method: 'PUT',
		body: JSON.stringify( transaction ),
	});
	if (!response.ok) {
		throw new Error('Update transaction failed');
	}
	return await response.json();
}

export const deleteTransaction = async (id) => {

	const response = await apiFetch(`/api/transactions/${id}`, {
		method: 'DELETE'
	});
	if (!response.ok) {
		throw new Error('Delete transaction failed');
	}
	return;
}