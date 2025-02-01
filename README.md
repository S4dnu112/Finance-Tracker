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
- Set a future transaction
- Delete a transaction
- End a recurring transaction
- View aggregates (such as balances per account and expenses per category)
- View transaction history

## Requirements
> **Note:** External dependencies are already in the lib folder 
- Sqlite
- Java

## How to Run the Program
Compile
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java src/Finance_Tracker.java; ```

Run
```
java -cp "src;lib/*" Finance_Tracker ```

Combined
```
javac -cp "lib/*" -sourcepath src src/backend/FinanceBackend.java src/transactionModels/*.java src/frontend/*.java src/Finance_Tracker.java; java -cp "src;lib/*" Finance_Tracker
```

## Contributors
[![S4dnu112](https://github.com/S4dnu112.png?size=50)](https://github.com/S4dnu112)
[![neo-geroda](https://github.com/neo-geroda.png?size=50)](https://github.com/neo-geroda)
[![DLJocson](https://github.com/DLJocson.png?size=50)](https://github.com/DLJocson)
[![EmsGarbino](https://github.com/EmsGarbino.png?size=50)](https://github.com/EmsGarbino)
[![kristinebae](https://github.com/kristinebae.png?size=50)](https://github.com/kristinebae)
[![Felicityjoy](https://github.com/Felicityjoy.png?size=50)](https://github.com/Felicityjoy)






