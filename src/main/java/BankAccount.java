import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class BankAccount {
    private double balance;
    private Lock balanceChangeLock;
    private Condition sufficientFundsCondition;


    public BankAccount() {
        balance = 0;
        balanceChangeLock = new ReentrantLock();
        sufficientFundsCondition = balanceChangeLock.newCondition();
    }

    public void deposit(int transferAmount) {
        balanceChangeLock.lock();

        try {
            System.out.print("Depositing " + transferAmount);
            double newBalance = balance + transferAmount;
            System.out.println(", new balance is " + newBalance);
            balance = newBalance;
            sufficientFundsCondition.signalAll();
        } finally {
            balanceChangeLock.unlock();
        }
    }

    public void withdraw(int transferAmount) throws InterruptedException{

        balanceChangeLock.lock();
        try {
            while (balance < transferAmount) {
                sufficientFundsCondition.await();

            }
            System.out.print("Withdrawing " + transferAmount);
            double newBalance = balance - transferAmount;
            System.out.println(", new balance is " + newBalance);
            balance = newBalance;
        } finally {
            balanceChangeLock.unlock();
        }
    }



}






