package exercise;

public class PassByRefrence {
	public static void main(String[] args) {
		Bank bank=new Bank();
		Money money=new Money();
		bank.AcceptMoney(money);
		bank.TransferMoney(1000, new Account());
		
	}

}

class Bank{
	public void AcceptMoney(Money m) {
		
	}
	public void TransferMoney(int atm,Account acct) {
		acct.Credit(10000);
		acct.Debit(2000);
		System.out.println(atm);
		
	}
}
	

class Money{
	
}
class Account{
	public void Credit(int crd) {
		System.out.println("Credit..:"+crd);
	}
	public void Debit(int dir ) {
		System.out.println("Debit...:"+dir);
	}
}
