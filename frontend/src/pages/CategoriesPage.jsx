import { useState, useEffect } from "react";
import { getCategories, createCategory, deleteCategory } from "../api/categories";

function CategoriesPage() {
	const [categories, setCategories] = useState([]);
	const [name, setName] = useState('');
	const [type, setType] = useState('');
	const [id, setId] = useState('');

	useEffect(() => {
		async function loadCategories() {
			const data = await getCategories();
			setCategories(data);
		}
		loadCategories();
	}, []);

	return (
		<div style={{ maxWidth: 600, margin: '2rem auto', padding: '1rem' }}>
			<h1>Categories</h1>

			<h2>Del sistema</h2>
			<ul>
				{categories
					.filter(c => c.system)
					.map(c => (
						<li key={c.id}>
							{c.name} <small>({c.type})</small>
						</li>
					))
				}
			</ul>

			<h2>Mis categorias</h2>
			<ul>
				{categories
					.filter(c => !c.system)
					.map(c => (
						<li key={c.id}>
							{c.name} <small>({c.type})</small>
						</li>
					))
				}
			</ul>
		</div>
	);
}

export default CategoriesPage;