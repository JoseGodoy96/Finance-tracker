import { useState, useEffect } from "react";
import { getCategories, createCategory, deleteCategory } from "../api/categories";
import styles from "../styles/CategoriesPage.module.css"

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
		<div className={styles.page}>
			<h1 className={styles.heading}>Categories</h1>
			<div className={styles.card}>
				<form onSubmit={handleSubmit} className={styles.form}>
					<input
					className={styles.input}
					type="text"
					value={name}
					onChange={(e) => setName(e.target.value)}
					required
					/>
					<select
					className={styles.select}
					value={type}
					onChange={(e) => setType(e.target.value)}
					>
						<option value="INCOME">INCOME</option>
						<option value="EXPENSE">EXPENSE</option>
					</select>
					<button type="submit" className={styles.button}>Crear</button>
				</form>
			</div>
			<div className={styles.section}>
				<h2 className={styles.sectionTitle}>Del sistema</h2>
				<ul className={styles.list}>
					{categories
						.filter(c => c.system)
						.map(c => (
							<li key={c.id} className={styles.item}>
								{c.name} <small className={styles.itemType}>({c.type})</small>
							</li>
						))
					}
				</ul>
			</div>
			<div className={styles.section}>
				<h2 className={styles.sectionTitle}>Mis categorias</h2>
				<ul className={styles.list}>
					{categories
						.filter(c => !c.system)
						.map(c => (
							<li key={c.id} className={styles.item}>
								{c.name} <small className={styles.button}>({c.type})</small>
								<button onClick={() => handleDelete(c.id)} className={styles.deleteBtn}>Eliminar</button>
							</li>
						))
					}
				</ul>
			</div>
		</div>
	);
}

export default CategoriesPage;