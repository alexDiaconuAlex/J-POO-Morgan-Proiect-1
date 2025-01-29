package org.poo.banking;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<ClassicAccount> accounts;
    private List<Transaction> transactions;


    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    /**
     * Adauga o tranzactie in lista de tranzactii ale utilizatorului.
     * @param transaction Tranzactia de adaugat.
     */
    public void addTransaction(final Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Returneaza lista de tranzactii ale utilizatorului.
     * @return Lista de tranzactii.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Gaseste un cont pe baza IBAN-ului.
     * @param iban IBAN-ul contului cautat.
     * @return Contul gasit sau null daca nu exista.
     */
    public ClassicAccount getAccountByIban(final String iban) {
        for (ClassicAccount account : accounts) {
            if (account.getIban().equals(iban)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gaseste un cont pe baza numarului de card.
     * @param cardNumber Numarul cardului cautat.
     * @return Contul asociat cardului sau null daca nu exista.
     */
    public ClassicAccount getAccountByCard(final String cardNumber) {
        for (ClassicAccount account : accounts) {
            for (ClassicCard card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gaseste un card pe baza numarului de card.
     * @param cardNumber Numarul cardului cautat.
     * @return Cardul gasit sau null daca nu exista.
     */
    public ClassicCard getCardByNumber(final String cardNumber) {
        for (ClassicAccount account : accounts) {
            for (ClassicCard card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return card;
                }
            }
        }
        return null;
    }

    /**
     * Returneaza lista de conturi ale utilizatorului.
     * @return Lista de conturi.
     */
    public List<ClassicAccount> getAccounts() {
        return accounts;
    }

    /**
     * Adauga un cont nou in lista de conturi ale utilizatorului.
     * @param account Contul de adaugat.
     */
    public void addAccount(final ClassicAccount account) {
        accounts.add(account);
    }

    /**
     * Sterge un cont pe baza IBAN-ului daca nu are fonduri ramase.
     * @param iban IBAN-ul contului de sters.
     * @return True daca stergerea a fost realizata, altfel false.
     */
    public Boolean deleteAccountByIban(final String iban) {
        ClassicAccount account = this.getAccountByIban(iban);
        if (account == null || account.getBalance() > 0) {
            return false;
        }
        accounts.remove(account);
        return true;
    }

    /**
     * Returneaza prenumele utilizatorului.
     * @return Prenumele utilizatorului.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returneaza numele de familie al utilizatorului.
     * @return Numele de familie al utilizatorului.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returneaza adresa de email a utilizatorului.
     * @return Adresa de email a utilizatorului.
     */
    public String getEmail() {
        return email;
    }

}
