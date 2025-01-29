package org.poo.banking;

import java.util.ArrayList;
import java.util.List;

public class ClassicAccount {
    private String iban;
    private double balance;
    private double minimumBalance;
    private Currency currency;
    private List<ClassicCard> cards;


    public ClassicAccount(final String iban, final Currency currency) {
        this.iban = iban;
        this.balance = 0;
        this.currency = currency;
        cards = new ArrayList<>();
    }

    /**
     * Adauga o suma la soldul contului.
     * @param amount Suma de adaugat.
     */
    public void addFunds(final double amount) {
        balance += amount;
    }

    /**
     * Returneaza IBAN-ul contului sub forma de String.
     * @return IBAN-ul contului.
     */
    public String toString() {
        return iban;
    }

    //TODO - pentru deleteCardByNumber
    /**
     * Gaseste un card dupa numarul acestuia.
     * @param cardNumber Numarul cardului.
     * @return Cardul gasit sau null daca nu este gasit.
     */
    public ClassicCard getCardByNumber(final String cardNumber) {
        for (ClassicCard card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Sterge un card din lista dupa numarul acestuia.
     * @param cardNumber Numarul cardului de sters.
     * @return True daca cardul a fost sters, false daca nu a fost gasit.
     */
    public Boolean deleteCardByNumber(final String cardNumber) {
        ClassicCard card = this.getCardByNumber(cardNumber);
        if (card == null) {
            return false;
        }
        cards.remove(card);
        return true;
    }

    /**
     * Adauga un card in lista de carduri asociate contului.
     * @param card Cardul de adaugat.
     */
    public void addCard(final ClassicCard card) {
        cards.add(card);
    }

    /**
     * Returneaza tipul contului.
     * @return "classic" pentru contul clasic.
     */
    public String getAccountType() {
        return "classic";
    }

    /**
     * Returneaza IBAN-ul contului.
     * @return IBAN-ul sub forma de String.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Returneaza soldul curent al contului.
     * @return Soldul contului.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Seteaza soldul minim permis pentru cont.
     * @param minimumBalance Noua valoare a soldului minim.
     */
    public void setMinimumBalance(final double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /**
     * Returneaza soldul minim al contului.
     * @return Soldul minim.
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Returneaza moneda asociata contului.
     * @return Moneda contului.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Returneaza lista de carduri asociate contului.
     * @return Lista de carduri.
     */
    public List<ClassicCard> getCards() {
        return cards;
    }

    /**
     * Verifica daca acest cont este un cont de economii.
     * @return False, deoarece acesta este un cont clasic.
     */
    public boolean isSavingsAccount() {
        return false;
    }

}
