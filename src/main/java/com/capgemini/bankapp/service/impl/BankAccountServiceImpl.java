package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankapp.entities.BankAccount;
import com.capgemini.bankapp.exceptions.LowBalanceException;
import com.capgemini.bankapp.repository.BankAccounRepository;
import com.capgemini.bankapp.service.BankAccountService;
@Service
public class BankAccountServiceImpl implements BankAccountService {
	
private BankAccounRepository  bankAccountRepository;

//public void setBankAccountRepository(BankAccounRepository bankAccountRepository) {
	//this.bankAccountRepository = bankAccountRepository;
//}

	@Autowired

	private BankAccountServiceImpl(BankAccounRepository bankAccountRepository) {
	super();
	this.bankAccountRepository = bankAccountRepository;
}
	@Override
	public double getBalance(long accountId) {
		
		return bankAccountRepository.getBalance(accountId);
	}
	
	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException {
		
		double balance = bankAccountRepository.getBalance(accountId);
		if(balance != -1) {
			if(balance - amount >= 0) {
				bankAccountRepository.updateBalance(accountId, balance - amount);
				return bankAccountRepository.getBalance(accountId);
			}
			else 
				throw new LowBalanceException("You don't have sufficient fund");
		}
		return balance;
	}
	@Override
	public double deposit(long accountId, double amount) {
		
		double balance = bankAccountRepository.getBalance(accountId);
		if(balance != -1) {
			bankAccountRepository.updateBalance(accountId, amount+balance);
			return bankAccountRepository.getBalance(accountId);
		}
		return balance;
	}

	@Override
	public boolean fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException {
		
		double balance = withdraw(fromAccount, amount);
		if(balance != -1) {
			if (deposit(toAccount, amount) == -1) {
				deposit(fromAccount, amount);
				
				
			}
			return true;
		}
		return false;
	}


@Override
public boolean updateBalance(long accountId, double newBalance) {
	
	return true;
}
@Override
public boolean addBankAccount(BankAccount account) {
	
	return bankAccountRepository.addBankAccount(account);
}
@Override
public BankAccount findBankAccountById(long accountId) {
	
	return bankAccountRepository.findBankAccountById(accountId);
}
@Override
public List<BankAccount> findAllBankAccounts() {
	
	return bankAccountRepository.findAllBankAccounts();
}
@Override
public BankAccount updateBankAccount(BankAccount account) {
	
	return bankAccountRepository.updateBankAccount(account);
}
@Override
public boolean deleteBankAccount(long accountId) {
	
	return bankAccountRepository.deleteBankAccount(accountId);
}
}

