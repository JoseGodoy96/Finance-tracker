import { useState, useEffect } from "react";
import { getCategories } from "../api/categories";
import { getTransactions, createTransaction, updateTransaction, deleteTransaction } from "../api/transactions";
import { suggestCategory } from "../api/ai/ai";

function TransactionsPage() {
	const [transactions, setTransactions] = useState([]);
	const [categories, setCategories] = useState([]);
	const [amount, setAmount] = useState('');
	const [date, setDate] = useState('');
	const [description, setDescription] = useState('');
	const [type, setType] = useState('EXPENSE');
	const [categoryId, setCategoryId] = useState('');
	const [editingId, setEditingId] = useState(null);

	useEffect(() => {
		async function loadTransactions() {
			const data = await getTransactions();
			setTransactions(data);
		}
		async function loadCategories() {
			const data = await getCategories();
			setCategories(data);
		}
		loadTransactions();
		loadCategories();
	}, [])

	useEffect(() => {

		if (description.trim().length < 3)
			return;
		const timeoutId = setTimeout(async () => {
			try {
				const suggested = await suggestCategory(description);
				setCategoryId(suggested.id);
				setType(suggested.type);
			} catch (err) {
				
			}
		}, 800);
		return () => clearTimeout(timeoutId);
	}, [description]);

	async function handleSubmit(e) {
		e.preventDefault();


		const transaction = {
			amount: parseFloat(amount),
			date,
			description,
			type,
		};

		try {
			if (editingId === null) {
				const data = await createTransaction(transaction, categoryId);
				setTransactions([...transactions, data]);
			} else {
				const data = await updateTransaction(editingId, transaction, categoryId);
				setTransactions(transactions.map(t => {
					if (t.id === editingId) return data;
					return t;
				}));
			}
			setAmount('');
			setDate('');
			setDescription('');
			setType('EXPENSE');
			setCategoryId('');
			setEditingId(null);
		} catch (err) {
			alert('No se pudo crear la Transaction' + err);
		}
	}

	async function handleDelete(id) {

		try {
			await deleteTransaction(id);
			setTransactions(transactions.filter(c => c.id !== id));
		} catch (err) {
			alert('No se pudo borrar la transaccion' + err);
		}
	}

	function handleEdit(t) {
		setEditingId(t.id);
		setAmount(t.amount);
		setDate(t.date);
		setDescription(t.description);
		setType(t.type);
		setCategoryId(t.categoryId);
	}

	return (
		<div style={{ maxWidth: 600, margin: '2rem auto', padding: '1rem'}}>
			<h1>Transactions</h1>

			<form onSubmit={handleSubmit}>
				<input 
					type="number"
					step="0.01"
					value={amount}
					onChange={(e) => setAmount(e.target.value)}
					required/>
				<input 
					type="date"
					value={date}
					onChange={(e) => setDate(e.target.value)}
					required/>
				<input 
					type="text" 
					value={description}
					onChange={(e) => setDescription(e.target.value)}
					required/>
				<select 
					value={type}
					onChange={(e) => setType(e.target.value)}
					>
					<option value="INCOME">INCOME</option>
					<option value="EXPENSE">EXPENSE</option>
				</select>
				<select
					value={categoryId}
					onChange={(e) => setCategoryId(e.target.value)}
					required
					>
					<option value="">-- Elige categoría --</option>
					{categories.map(c => (
						<option key={c.id} value={c.id}>
							{c.name} ({c.type})
						</option>
					))}
				</select>
				<button type="submit">Crear</button>
			</form>

			<ul>
				{transactions.map(t => (
					<li key={t.id}>
						{t.date} - {t.amount} - {t.description}
						<small> ({t.type} / {t.categoryName}) </small>
						<button onClick={() => handleDelete(t.id)}>Eliminar</button>
						<button onClick={() => handleEdit(t)}>Editar</button>
					</li>
				))}
			</ul>
		</div>
	);
}

export default TransactionsPage;