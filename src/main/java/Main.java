import lombok.SneakyThrows;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        final int AMOUNT = 100;
        final int THREADS = 100;
        for (int i = 1; i <= THREADS; i++) {

            DepositRunnable d = new DepositRunnable(account, account, AMOUNT);
            WithdrawRunnable w = new WithdrawRunnable(account, account, AMOUNT);
            Thread dt = new Thread(d);
            Thread wt = new Thread(w);
            dt.start();
            wt.start();
        }
        transfer(account,account,AMOUNT);
    }

    @SneakyThrows
    static private void transfer(BankAccount fromAccount, BankAccount toAccount, int transferAmount) {
        BankAccount fromAccountTemp = fromAccount;
        BankAccount toAccountTemp = toAccount;

        if (fromAccountTemp.equals(toAccountTemp) ) {
            fromAccountTemp = toAccountTemp;
            toAccountTemp = fromAccountTemp;
        }

        synchronized (fromAccountTemp) {
            synchronized (toAccountTemp) {
                fromAccount.withdraw(transferAmount);
                toAccount.deposit(transferAmount);
            }
        }
    }


}
