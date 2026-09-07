import { useState, useEffect } from "react";
import { getCategories, createCategory, deleteCategory } from "../api/categories";

function CategoriesPage() {
	const [categories, setCategories] = useState([]);
	const [name, setName] = useState('');
	const [type, setType] = useState('EXPENSE');

	useEffect(() => {
		async function loadCategories() {
			const data = await getCategories();
			setCategories(data);
		}
		loadCategories();
	}, []);

	async function handleSubmit(e) {
		e.preventDefault();

		try {
			const data = await createCategory(name, type);
			setCategories([...categories, data]);
			setName('');
			setType('EXPENSE');
		} catch (err) {
			alert('No se pudo crear la categoria' + err);
		}
	}

	async function handleDelete(id) {

		try {
			await deleteCategory(id);
			setCategories(categories.filter(c => c.id !== id));
		} catch (err) {
			alert('No se pudo borrar la categoria' + err);
		}
	}

	return (
		<div style={{ maxWidth: 600, margin: '2rem auto', padding: '1rem' }}>
			<h1>Categories</h1>

			<form onSubmit={handleSubmit}>
				<input 
				type="text"
				value={name}
				onChange={(e) => setName(e.target.value)}
				required
				/>
				<select 
				value={type}
				onChange={(e) => setType(e.target.value)}
				>
					<option value="INCOME">INCOME</option>
					<option value="EXPENSE">EXPENSE</option>
				</select>
				<button type="submit">Crear</button>
			</form>

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
							<button onClick={() => handleDelete(c.id)}>Eliminar</button>
						</li>
					))
				}
			</ul>
		</div>
	);
}

export default CategoriesPage;