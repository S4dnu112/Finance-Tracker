# Located here are the source files for a finance-tracker program

To run the program, make sure you have sqlite installed


### Compiling everything
javac -sourcepath src src/test.java src/backend/FinanceBackend.java src/transactionModels/*.java 

### Running finance backend test file:
java -cp "src;lib/*" test

### Together
javac -sourcepath src src/test.java src/backend/FinanceBackend.java src/transactionModels/*.java; java -cp "src;lib/*" test



