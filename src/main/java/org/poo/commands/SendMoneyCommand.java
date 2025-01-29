package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.banking.Currency;
import org.poo.banking.Graph;
import org.poo.banking.InsufficientFundsTransaction;
import org.poo.banking.TransferTransaction;
import org.poo.banking.ClassicAccount;
import org.poo.banking.Bank;
import org.poo.banking.User;

import java.util.ArrayList;

public class SendMoneyCommand implements Command {
    private String email;
    private String currentIban;
    private String receiverIban;
    private  String description;
    private double amount;
    private Graph<Currency> currencyGraph;

    public SendMoneyCommand(final String email, final String currentIban,
                            final String receiverIban, final String description,
                            final double amount, final Graph<Currency> currencyGraph) {
        this.email = email;
        this.currentIban = currentIban;
        this.receiverIban = receiverIban;
        this.description = description;
        this.amount = amount;
        this.currencyGraph = currencyGraph;
    }

    /**
     * Executa comanda pentru trimiterea de bani intre doua conturi.
     *
     * @param bank      Instanta bancii care contine toate conturile si utilizatorii.
     * @param output    Nodul JSON in care se adauga rezultatele comenzii.
     * @param mapper    Obiectul Jackson pentru manipularea JSON-ului.
     * @param timestamp Timpul la care este executata comanda.
     */
    @Override
    public void execute(final Bank bank, final ArrayNode output,
                        final ObjectMapper mapper, final int timestamp) {
        User user = bank.getUserByEmail(email);

        ClassicAccount currentAccount = bank.getAccountByIban(currentIban);
        ClassicAccount receiverAccount = bank.getAccountByIban(receiverIban);

        if (currentAccount != null && receiverAccount != null
                && !currentAccount.equals(receiverAccount)) {
            Currency currentAccountCurrency = currentAccount.getCurrency();
            Currency receiverAccountCurrency = receiverAccount.getCurrency();
            double convertedAmount = amount;

            if (currentAccount.getBalance() >= amount) {
                if (!currentAccountCurrency.equals(receiverAccountCurrency)) {
                    ArrayList<Graph<Currency>.Edge> path =
                            currencyGraph.getPath(currentAccountCurrency,
                                    receiverAccountCurrency);

                    if (path != null) {
                        double totalRate = 1.0;
                        for (Graph<Currency>.Edge edge : path) {
                            totalRate *= edge.getCost();
                        }
                        convertedAmount = amount * totalRate;
                    }
                }
                currentAccount.addFunds(-amount);
                receiverAccount.addFunds(convertedAmount);

                user.addTransaction(new TransferTransaction(
                        timestamp,
                        description,
                        currentIban,
                        receiverIban,
                        amount,
                        currentAccountCurrency,
                        "sent",
                        currentIban
                ));

                for (User recipient : bank.getUsers()) {
                    if (recipient.getAccounts().contains(receiverAccount)) {
                        recipient.addTransaction(new TransferTransaction(
                                timestamp,
                                description,
                                currentIban,
                                receiverIban,
                                convertedAmount,
                                receiverAccountCurrency,
                                "received",
                                receiverIban
                        ));
                        break;
                    }
                }
            } else {
                user.addTransaction(new InsufficientFundsTransaction(timestamp));
            }
        }
    }
}
