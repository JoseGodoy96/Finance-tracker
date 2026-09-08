import { apiFetch } from "./http"

export const suggestCategory = async(description) => {

	const response = await apiFetch("/api/ai/suggest-category", {
		method: 'POST',
		body: JSON.stringify({ description }),
	});
	if (!response.ok) {
		throw new Error("Error");
	}
	return await response.json();
}