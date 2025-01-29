package org.poo.banking;

import java.util.List;

public class SplitPaymentTransaction extends Transaction {
    private Currency currency;
    private double totalAmount;
    private double splitAmount;
    private List<String> involvedAccounts;
    private String error;

    public SplitPaymentTransaction(final int timestamp, final double totalAmount,
                                   final double splitAmount, final Currency currency,
                                   final List<String> involvedAccounts) {
        super(timestamp, "Split payment of "
                + String.format("%.2f", totalAmount) + " " + currency.toString());
        this.currency = currency;
        this.splitAmount = splitAmount;
        this.totalAmount = totalAmount;
        this.involvedAccounts = involvedAccounts;
        error = null;
    }

    /**
     * Seteaza mesajul de eroare pentru tranzactia de plata.
     * @param error Mesajul de eroare ca String.
     */
    public void setError(final String error) {
        this.error = error;
    }


    /**
     * Returneaza mesajul de eroare al tranzactiei.
     * @return Mesajul de eroare ca String.
     */
    public String getError() {
        return error;
    }

    /**
     * Returneaza moneda utilizata in tranzactie.
     * @return Moneda sub forma de Currency.
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Returneaza suma totala a tranzactiei de plata impartita.
     * @return Suma totala sub forma de double.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Returneaza ''suma impartita'' pentru fiecare cont.
     * @return Suma impartita sub forma de double.
     */
    public double getSplitAmount() {
        return splitAmount;
    }

    /**
     * Returneaza lista conturilor implicate in tranzactie.
     *
     * @return Lista IBAN-urilor conturilor implicate.
     */
    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    /**
     * Aceasta metoda permite aplicarea design pattern-ului Visitor.
     * Ofera vizitatorului posibilitatea de a efectua operatia de splitPayment.
     */
    @Override
    public void accept(final Visitor v) {
        v.visit(this);
    }
}
