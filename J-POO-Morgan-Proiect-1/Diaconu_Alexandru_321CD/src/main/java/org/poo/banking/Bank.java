package org.poo.banking;

import java.util.ArrayList;
import java.util.List;

public final class Bank {
    private static Bank instance;
    private List<User> users;

    private Bank() {
        users = new ArrayList<>();
    }

    /**
     * Metoda statica pentru obtinerea instantei clasei Bank.
     * S-a folosit design pattern-ul Singleton pentru a avea
     * o singura instanta a bancii.
     * @return instanta unica a clasei Bank.
     */
    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    /**
     * Gaseste un utilizator pe baza unui IBAN al unui cont.
     *
     * @param iban IBAN-ul contului.
     * @return utilizatorul care detine contul sau null daca nu este gasit.
     */
    public User getUserByAccount(final String iban) {
        for (User user : users) {
            for (ClassicAccount account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Gaseste un cont clasic pe baza IBAN-ului.
     *
     * @param iban IBAN-ul contului.
     * @return contul clasic gasit sau null daca nu este gasit.
     */
    public ClassicAccount getAccountByIban(final String iban) {
        for (User user : users) {
            ClassicAccount account = user.getAccountByIban(iban);
            if (account != null) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gaseste un utilizator pe baza numarului cardului.
     *
     * @param cardNumber numarul cardului.
     * @return utilizatorul care detine cardul sau null daca nu este gasit.
     */
    public User getUserByCardNumber(final String cardNumber) {
        for (User user : users) {
            if (user.getCardByNumber(cardNumber) != null) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gaseste un card clasic pe baza numarului cardului.
     *
     * @param cardNumber numarul cardului.
     * @return cardul clasic gasit sau null daca nu este gasit.
     */
    public ClassicCard getCardByNumber(final String cardNumber) {
        for (User user : users) {
            ClassicCard card = user.getCardByNumber(cardNumber);
            if (card != null) {
                return card;
            }
        }
        return null;
    }

    /**
     * Gaseste un utilizator pe baza adresei de email.
     *
     * @param email adresa de email a utilizatorului.
     * @return utilizatorul gasit sau null daca nu este gasit.
     */
    public User getUserByEmail(final String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Reseteaza lista de utilizatori.
     */
    public void reset() {
        users.clear();
    }

    /**
     * Returneaza lista de utilizatori ai bancii.
     *
     * @return lista de utilizatori.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Adauga un utilizator in lista bancii.
     *
     * @param user utilizatorul de adaugat.
     */
    public void addUser(final User user) {
        users.add(user);
    }
}
