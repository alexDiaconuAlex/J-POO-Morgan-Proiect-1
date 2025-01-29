package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.banking.ClassicCard;
import org.poo.banking.Bank;
import org.poo.banking.ClassicAccount;
import org.poo.banking.CardPaymentTransaction;
import org.poo.banking.CardFrozenTransaction;
import org.poo.banking.InsufficientFundsTransaction;
import org.poo.banking.CardCreatedTransaction;
import org.poo.banking.OneTimeCard;
import org.poo.banking.CardDestroyedTransaction;
import org.poo.banking.Currency;
import org.poo.banking.Graph;
import org.poo.banking.User;
import org.poo.utils.Utils;
import java.util.ArrayList;

public class PayOnlineCommand implements Command {
    private String cardNumber;
    private double amountToDeduct;
    private String currencyStr;
    private String description;
    private String commerciant;
    private String email;
    private Graph<Currency> currencyGraph;

    public PayOnlineCommand(final String cardNumber, final double amountToDeduct,
                            final String currencyStr, final String description,
                            final String commerciant, final String email,
                            final Graph<Currency> currencyGraph) {
        this.cardNumber = cardNumber;
        this.amountToDeduct = amountToDeduct;
        this.currencyStr = currencyStr;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
        this.currencyGraph = currencyGraph;
    }

    /**
     * Executa comanda pentru efectuarea platii online.
     *
     * @param bank      Instanta bancii care contine informatii despre utilizatori si conturi.
     * @param output    Nodul JSON in care se adauga rezultatele comenzii.
     * @param mapper    Obiectul Jackson pentru manipularea JSON-ului.
     * @param timestamp Timpul la care este executata comanda.
     */
    @Override
    public void execute(final Bank bank, final ArrayNode output,
                        final ObjectMapper mapper, final int timestamp) {
        User user = bank.getUserByEmail(email);

        Currency targetCurrency = Currency.valueOf(currencyStr);
        ClassicCard card = bank.getCardByNumber(cardNumber);

        if (card != null && card.getStatus()) {
            ClassicAccount account = null;
            account = user.getAccountByCard(cardNumber);

            //Pentru conversie

            //Scad suma platita online
            if (account != null) {
                //TODO
                Currency accountCurrency = account.getCurrency();
                double convertedAmount = amountToDeduct;

                //Daca monedele sunt diferite, efectuez conversia folosind graful
                if (!accountCurrency.equals(targetCurrency)) {
                    ArrayList<Graph<Currency>.Edge> path =
                            currencyGraph.getPath(targetCurrency, accountCurrency);

                    if (path != null) {
                        double totalRate = 1.0;
                        for (Graph<Currency>.Edge edge : path) {
                            totalRate *= edge.getCost();
                        }
                        convertedAmount = amountToDeduct * totalRate;
                    }
                }

                if (account.getBalance() >= convertedAmount) {
                    account.addFunds(-convertedAmount);
                    user.addTransaction(new CardPaymentTransaction(
                            timestamp,
                            account,
                            commerciant,
                            convertedAmount,
                            accountCurrency
                    ));

                    if (card.isOneTimeCard()) {
                        user.addTransaction(new CardDestroyedTransaction(
                                timestamp,
                                email,
                                cardNumber,
                                account.getIban()
                        ));

                        OneTimeCard oneTimeCard = (OneTimeCard) card;
                        String newCardNumber = Utils.generateCardNumber();
                        oneTimeCard.setCardNumber(newCardNumber);

                        user.addTransaction(new CardCreatedTransaction(
                                timestamp,
                                email,
                                newCardNumber,
                                account.getIban()
                        ));
                    }
                } else {
                    user.addTransaction(new InsufficientFundsTransaction(timestamp));
                }
            }
        } else if (card == null) {
            ObjectNode commandOutput = mapper.createObjectNode();
            commandOutput.put("command", "payOnline");
            ObjectNode outputNode = mapper.createObjectNode();
            outputNode.put("timestamp", timestamp);
            outputNode.put("description", "Card not found");
            commandOutput.put("output", outputNode);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
        } else if (!card.getStatus()) {
            user.addTransaction(new CardFrozenTransaction(timestamp));
        }
    }
}
