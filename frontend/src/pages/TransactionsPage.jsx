import { useState, useEffect } from "react";
import { getCategories } from "../api/categories";
import { getTransactions, createTransaction, updateTransaction, deleteTransaction } from "../api/transactions";

function TransactionsPage() {
	const [transactions, setTransactions] = useState([]);
	const [categories, setCategories] = useState([]);

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

	return (
		<div style={{ maxWidth: 600, margin: '2rem auto', padding: '1rem'}}>
			<h1>Transactions</h1>
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