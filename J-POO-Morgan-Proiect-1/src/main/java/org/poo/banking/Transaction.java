package org.poo.banking;

public abstract class Transaction implements Visitable {
    private int timestamp;
    private String description;
    private String iban;

    /**
     * Returneaza timestamp-ul tranzactiei.
     * @return Timestamp-ul tranzactiei sub forma de int.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returneaza descrierea tranzactiei.
     * @return Descrierea tranzactiei sub forma de String.
     */
    public String getDescription() {
        return description;
    }

    public String getIban() { return iban; }


    public Transaction(final int timestamp, final String description, final String iban) {
        this.timestamp = timestamp;
        this.description = description;
        this.iban = iban;
    }

    /**
     * Verifica daca tranzactia este o plata cu cardul.
     * @return False implicit, poate fi suprascris in clasele derivate.
     */
    public boolean isCardPayment() {
        return false;
    }

    /**
     * Metoda abstracta pentru aplicarea design pattern-ului Visitor.
     * Permite vizitatorului sa proceseze tranzactia curenta.
     * @param visitor Vizitatorul care proceseaza tranzactia.
     */
    public abstract void accept(Visitor visitor);
}
