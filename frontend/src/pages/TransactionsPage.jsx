import { useState, useEffect } from "react";
import { getCategories } from "../api/categories";
import { getTransactions, createTransaction, updateTransaction, deleteTransaction } from "../api/transactions";

function TransactionsPage() {
	const [transactions, setTransactions] = useState([]);
	const [categories, setCategories] = useState([]);
	const [amount, setAmount] = useState('');
	const [date, setDate] = useState('');
	const [description, setDescription] = useState('');
	const [type, setType] = useState('EXPENSE');
	const [categoryId, setCategoryId] = useState('');

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

	async function handleSubmit(e) {
		e.preventDefault();

		try {
			const transaction = {
				amount: parseFloat(amount),
				date,
				description,
				type,
			};
			const data = await createTransaction(transaction, categoryId);
			setTransactions([...transactions, data]);
			setAmount('');
			setDate('');
			setDescription('');
			setType('EXPENSE');
			setCategoryId('');
		} catch (err) {
			alert('No se pudo crear la Transaction' + err);
		}
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
					</li>
				))}
			</ul>
		</div>
	);
}

export default TransactionsPage;