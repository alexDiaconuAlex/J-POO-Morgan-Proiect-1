package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.banking.Bank;
import org.poo.banking.ClassicAccount;

public class AddFundsCommand implements Command {
    private String iban;
    private double amount;

    public AddFundsCommand(final String iban, final double amount) {
        this.iban = iban;
        this.amount = amount;
    }

    /**
     * Executa comanda pentru adaugarea fondurilor intr-un cont.
     * Nu se face nicio operatiune daca IBAN-ul nu este valid sau contul nu exista.
     *
     * @param bank      Instanta bancii care contine informatii despre utilizatori si conturi.
     * @param output    Nodul JSON in care se adauga rezultatele comenzii (neutilizat aici).
     * @param mapper    Obiectul Jackson pentru manipularea JSON-ului (neutilizat aici).
     * @param timestamp Timpul la care este executata comanda (neutilizat aici).
     */
    @Override
    public void execute(final Bank bank, final ArrayNode output,
                        final ObjectMapper mapper, final int timestamp) {
        ClassicAccount account = bank.getAccountByIban(iban);
        if (account != null) {
            account.addFunds(amount);
        }
    }
}
