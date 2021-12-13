public class DepositRunnable implements Runnable {
    private static final int DELAY = 1;
    private BankAccount account1;
    private BankAccount account2;
    private int amount;

    public DepositRunnable(BankAccount fromAccount, BankAccount toAccount, int transferAmount) {
        account1 = fromAccount;
        account2 = toAccount;
        amount = transferAmount;

    }

    @Override
    public void run() {
        try {
            account1.deposit(amount);


            Thread.sleep(DELAY);

        } catch (InterruptedException exception) {
        }
    }

}
