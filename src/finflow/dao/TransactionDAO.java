package finflow.dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import finflow.model.Transaction;

public interface TransactionDAO {
	int getTransactionTypeId(String Type);
	ResultSet getTransactionNamefromId(String id);
	int saveTransaction(Transaction transaction);
	List<String> getTransactionTypesByCategory(String category);
	Double getTotalIncomeUser(int userId);
	Double getTotalExpenseUser(int userId);
	List<Transaction> getRecentTransactions(int userId, int limit);
	List<Transaction> getTransactionsGroupedByCategory(int userId, String category);
	String getTransactionTypeNameById(int typeId);
	int updateTransactionDetails(int transactionId, String newTitle, double newAmount, Date newDate);
	int deleteTransaction(int transactionId);
	public Double getAvailableBalanceUser(int userId);
}
