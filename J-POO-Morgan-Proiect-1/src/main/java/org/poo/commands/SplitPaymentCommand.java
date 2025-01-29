package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.banking.SplitPaymentTransaction;
import org.poo.banking.Bank;
import org.poo.banking.User;
import org.poo.banking.ClassicAccount;
import org.poo.banking.Currency;
import org.poo.banking.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitPaymentCommand implements Command {
    private List<String> accountsForSplit;
    private double totalAmount;
    private String currency;
    private Graph<Currency> currencyGraph;

    public SplitPaymentCommand(final List<String> accountsForSplit,
                                   final double totalAmount,
                                   final String currency,
                                   final Graph<Currency> currencyGraph) {
        this.accountsForSplit = accountsForSplit;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.currencyGraph = currencyGraph;
    }

    /**
     * Executa comanda de split payment intre conturile specificate.
     *
     * @param bank      Instanta bancii care contine toate conturile si utilizatorii.
     * @param output    Nodul JSON in care se adauga rezultatele comenzii.
     * @param mapper    Obiectul Jackson pentru manipularea JSON-ului.
     * @param timestamp Timpul la care este executata comanda.
     */
    @Override
    public void execute(final Bank bank, final ArrayNode output,
                        final ObjectMapper mapper, final int timestamp) {
        double splitAmount = totalAmount / accountsForSplit.size();
        Currency targetCurrency = Currency.valueOf(currency);
        boolean canPay = true;
        Map<ClassicAccount, Double> convertedAmounts = new HashMap<>();
        String errorMessage = null;
        String insufficientFundsIban = null;

        for (String iban : accountsForSplit) {
            ClassicAccount account = bank.getAccountByIban(iban);

            if (account != null) {
                Currency currentCurrency = account.getCurrency();
                double convertedAmount = splitAmount;

                if (!currentCurrency.equals(targetCurrency)) {
                    ArrayList<Graph<Currency>.Edge> path =
                            currencyGraph.getPath(targetCurrency, currentCurrency);

                    if (path != null) {
                        double totalRate = 1.0;
                        for (Graph<Currency>.Edge edge : path) {
                            totalRate *= edge.getCost();
                        }
                        convertedAmount = splitAmount * totalRate;
                    } else {
                        canPay = false;
                        break;
                    }
                }

                if (account.getBalance() < convertedAmount) {
                    canPay = false;
                    insufficientFundsIban = iban;
                    errorMessage = "Account " + insufficientFundsIban
                            + " has insufficient funds for a split payment.";
                }

                convertedAmounts.put(account, convertedAmount);
            } else {
                canPay = false;
                break;
            }
        }



        if (canPay) {
            for (Map.Entry<ClassicAccount, Double> entry : convertedAmounts.entrySet()) {
                ClassicAccount account = entry.getKey();
                double amountToDeduct = entry.getValue();
                account.addFunds(-amountToDeduct);
            }

            for (String iban : accountsForSplit) {
                ClassicAccount account = bank.getAccountByIban(iban);
                if (account != null) {
                    for (User user : bank.getUsers()) {
                        if (user.getAccounts().contains(account)) {
                            SplitPaymentTransaction splitPaymentTransaction = new SplitPaymentTransaction(
                                    timestamp,
                                    totalAmount,
                                    splitAmount,
                                    targetCurrency,
                                    accountsForSplit,
                                    iban
                            );
                            user.addTransaction(splitPaymentTransaction);
                        }
                    }
                }
            }
        } else {
            if (timestamp == 370) {
                System.out.println(canPay);
            }
            //AICI NU SE AFISEAZA SPLITPAYMENT CU EROARE
            for (String iban : accountsForSplit) {
                ClassicAccount account = bank.getAccountByIban(iban);
                if (account != null) {
                    for (User user : bank.getUsers()) {
                        if (user.getAccounts().contains(account)) {
                            SplitPaymentTransaction splitPaymentTransaction = new SplitPaymentTransaction(
                                    timestamp,
                                    totalAmount,
                                    splitAmount,
                                    targetCurrency,
                                    accountsForSplit,
                                    iban
                            );
                            if (insufficientFundsIban != null) {
                                splitPaymentTransaction.setError(errorMessage);
                            }
                            user.addTransaction(splitPaymentTransaction);
                            if (timestamp == 370) {
                                System.out.println(iban + " " + user.getEmail());
                            }
                        }
                    }
                }
            }
        }
    }
}
