import { useState, useEffect } from "react";
import { getCategories } from "../api/categories";
import { getTransaction, createTransaction, updateTransaction, deleteTransaction } from "../api/transactions";

function TransactionsPage() {

	return (
		<h1>Transactions</h1>
	)
}

export default TransactionsPage;