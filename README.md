# PennyWise Finance-Tracker

PennyWise is a simple finance tracker that allows users to track their income, expenses, and money from different accounts. It encourages users to take full control over their finances and thus make informed decisions.

## Screenshots

![Home_Panel](docs/HomePanel.png)

![AddIncome_Panel](docs/AddIncomePanel.png)

![AddExpense_Panel](docs/AddExpensePanel.png)

![Transaction_History](docs/TransactionsHistory.png)

## Features
- Add an income data
- Add an expense data
- Transfer money between different accounts
- Specify recurrence transactions (income, expense transfer)
- Delete a transaction
- End a recurring transaction
- View aggregates (such as balances per account and expenses per category)
- View transaction history

## Requirements
- Sqlite
- Java
External dependencies are store on the lib folder

## How to Run the Program
Compile
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java;
```
Run
```
java -cp "src;lib/*" frontend.MainFrame
```
Combined
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java; java -cp "src;lib/*" frontend.MainFrame
```


