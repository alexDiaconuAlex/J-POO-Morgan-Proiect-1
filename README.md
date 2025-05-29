# Proiect: Sistem de Management Bancar

## Descriere
Acesta este un sistem bancar implementat in Java. Acesta are urmatoarele functionalitati, legate de utilizatori, crearea de conturi, emiterea de carduri, tranzactii si schimburi valutare. Proiectul utilizeaza principii de programare orientata pe obiect si design pattern-ul Visitor pentru a gestiona diferite tipuri de tranzactii. Mai utilizeaza si design pattern-ul Singleton pentru a exista doar o singura instanta de pentru clasa Bank si mai utilizeaza si design pattern-ul CommandFactory pentru a crea si returna instante ale comenzilor corespunzatoare.

## Cuprins
1. [Descriere](#descriere)
2. [Prezentare-generala-a-claselor](#prezentare-generala-a-claselor)
  - [Main](#main)
  - [Bank](#bank)
  - [User](#user)
  - [ClassicAccount](#classicaccount)
  - [SavingsAccount](#savingsaccount)
  - [ClassicCard](#classiccard)
  - [OneTimeCard](#onetimecard)
  - [Currency](#currency)
  - [Graph](#graph)
  - [Visitor](#visitor)
  - [Visitable](#visitable)
  - [AccountCreatedTransaction](#accountcreatedtransaction)
  - [CardCreatedTransaction](#cardcreatedtransaction)
  - [CardDestroyedTransaction](#carddestroyedtransaction)
  - [CardFrozenTransaction](#cardfrozentransaction)
  - [CardPaymentTransaction](#cardpaymenttransaction)
  - [ChangeInterestRateTransaction](#changeinterestratetransaction)
  - [DeleteAccountTransaction](#deleteaccounttransaction)
  - [FreezeCardTransaction](#freezecardtransaction)
  - [InsufficientFundsTransaction](#insufficientfundstransaction)
  - [SplitPaymentTransaction](#splitpaymenttransaction)
  - [TransferTransaction](#transfertransaction)
  - [Transaction](#transaction)
  - [TransactionPrinter](#transactionprinter)
3. [Design Patterns](#design-patterns)
4. [Interfata Visitor si Visitable](#interfata-visitor-si-visitable)

---

## Prezentare Generala a Claselor

### Main
- **Functionalitate**:
  - Creeaza utilizatori si proceseaza comenzile din fisierele de input.
- **Metode Principale**:
  - `main(String[] args)`: Metoda principala care initializeaza testele si gestioneaza output-ul.
  - `createUsers(UserInput[] users)`: Creeaza utilizatori pe baza datelor de intrare.
  - `executeCommands(CommandInput[] commands, ExchangeInput[] exchangeRates, ArrayNode output, ObjectMapper mapper)`: Proceseaza comenzile primite si genereaza output-ul corespunzator.
  - `action(String filePath1, String filePath2)`: Executa testele si scrie rezultatele in fisierele de output.

### Bank
- **Scop**: Foloseste design pattern-ul Singleton care gestioneaza utilizatorii si conturile lor.
- **Functionalitate**:
  - Permite adaugarea si gasirea utilizatorilor.
  - Ofera metode pentru gestionarea conturilor si cardurilor asociate utilizatorilor.
- **Metode**:
  - `getInstance()`: Returneaza instanta Singleton a bancii.
  - `addUser(User user)`: Adauga un utilizator nou.
  - `getUserByEmail(String email)`: Gaseste un utilizator dupa email.
  - `getAccountByIban(String iban)`: Gaseste un cont dupa IBAN.
  - `reset()`: Reseteaza lista de utilizatori.

### User
- **Functionalitate**:
  - Gestioneaza conturile si tranzactiile unui utilizator.
  - Permite adaugarea si stergerea conturilor, folosind diverse metode(cu iban, cu cardNumber)
- **Metode**:
  - `addAccount(ClassicAccount account)`: Adauga un cont nou utilizatorului.
  - `getAccountByIban(String iban)`: Returneaza un cont pe baza IBAN-ului.
  - `addTransaction(Transaction transaction)`: Inregistreaza o tranzactie pentru utilizator.
  - `deleteAccountByIban(String iban)`: Sterge un cont daca soldul este zero.
  - `getCardByNumber(String cardNumber)`: Gaseste un card dupa numarul acestuia.
  - `addTransaction(Transaction transaction)` : Adauga o tranzactie.
  - `addTransaction(Transaction transaction)` : Returneaza o tranzactie.
  - `getAccountByCard(String cardNumber)` : Returneaza un cont pe baza card number-ului.
### ClassicAccount
- **Scop**: Reprezinta un cont bancar standard.
- **Functionalitate**:
  - Permite gestionarea fondurilor si a cardurilor asociate contului.
  - Permite setarea unui sold minim.
- **Metode Principale**:
  - `addFunds(double amount)`: Adauga fonduri in cont.
  - `addCard(ClassicCard card)`: Asociaza un card cu contul.
  - `deleteCardByNumber(String cardNumber)`: Sterge un card dupa numarul acestuia.
  - `getBalance()`: Returneaza soldul contului.

### SavingsAccount
- **Scop**: Reprezinta un cont de economii care include o dobanda specifica.
- **Functionalitate**:
  - Extinde clasa `ClassicAccount` si adauga functionalitati specifice conturilor de economii.
  - Permite setarea si obtinerea ratei dobanzii.
- **Metode Principale**:
  - `getAccountType()`: Returneaza tipul contului (`"savings"`).
  - `setInterestRate(double interestRate)`: Seteaza dobanda pentru contul de economii.
  - `getInterestRate()`: Returneaza dobanda curenta a contului.
  - `isSavingsAccount()`: Returneaza `true`, indicand ca acest cont este unul de economii.

### ClassicCard
- **Scop**: Reprezinta un card clasic asociat unui cont bancar.
- **Functionalitate**:
  - Stocheaza numarul cardului si statusul acestuia (activ sau blocat).
  - Permite activarea, blocarea si modificarea numarului cardului.
- **Metode Principale**:
  - `getCardNumber()`: Returneaza numarul cardului.
  - `setCardNumber(String cardNumber)`: Seteaza un nou numar pentru card.
  - `getStatus()`: Returneaza statusul cardului (`true` pentru activ, `false` pentru blocat).
  - `setStatus(boolean status)`: Modifica statusul cardului.
  - `isOneTimeCard()`: Returneaza `false`, indicand ca este un card clasic reutilizabil.

### OneTimeCard
- **Scop**: Reprezinta un card de unica folosinta asociat unui cont bancar.
- **Functionalitate**:
  - Extinde clasa `ClassicCard` si redefineste metoda `isOneTimeCard()`.
  - Cardurile de unica folosinta sunt invalide dupa prima utilizare.
- **Metode Principale**:
  - `isOneTimeCard()`: Returneaza `true`, indicand ca acest card este de unica folosinta.

### Currency
- **Scop**: Enum care reprezinta diferite tipuri de monede disponibile in sistemul bancar.
- **Valori Disponibile**:
  - `USD`: Dolar american
  - `EUR`: Euro
  - `RON`: Leu romanesc
  - `INR`: Rupie indiana
  - `NZD`: Dolar neozeelandez
  - `CHF`: Franc elvetian
  - `GBP`: Lira sterlina
  - `CAD`: Dolar canadian
  - `MXN`: Peso mexican
  - `AUD`: Dolar australian
  - `CNY`: Yuan chinezesc

### Graph
- **Scop**: Reprezinta o structura de date graf folosita pentru conversia valutara intre monede diferite.
- **Functionalitate**:
  - Permite adaugarea de muchii intre noduri cu costuri asociate.
  - Gaseste calea cea mai scurta intre doua noduri pentru a calcula ratele de conversie intre valute.
- **Clase Interne**:
  - `Edge`: Reprezinta o muchie intre doua noduri, cu un cost specific.
- **Metode Principale**:
  - `addEdge(T source, T destination, double cost)`: Adauga o muchie intre doua noduri cu un cost specificat.
  - `getPath(T start, T end)`: Gaseste calea intre doua noduri si returneaza lista de muchii corespunzatoare.

### Visitor
- **Scop**: Interfata care defineste metode pentru a vizita diferite tipuri de tranzactii.
- **Functionalitate**:
  - Permite separarea logicii de procesare a tranzactiilor de clasele propriu-zise de tranzactii.
- **Metode Principale**:
  - `visit(AccountCreatedTransaction accountCreated)`: Proceseaza tranzactia de creare a unui cont.
  - `visit(InsufficientFundsTransaction insufficientFunds)`: Proceseaza tranzactia de fonduri insuficiente.
  - `visit(TransferTransaction transferTransaction)`: Proceseaza tranzactia de transfer de bani.
  - `visit(CardCreatedTransaction cardCreated)`: Proceseaza tranzactia de creare a unui card.
  - `visit(CardPaymentTransaction cardPaymentTransaction)`: Proceseaza tranzactia de plata cu cardul.
  - `visit(CardDestroyedTransaction cardDestroyedTransaction)`: Proceseaza tranzactia de distrugere a unui card.
  - `visit(FreezeCardTransaction freezeCardTransaction)`: Proceseaza tranzactia de inghetare a unui card.
  - `visit(CardFrozenTransaction cardFrozenTransaction)`: Proceseaza tranzactia cand un card este blocat.
  - `visit(SplitPaymentTransaction splitPaymentTransaction)`: Proceseaza tranzactia de plata impartita.
  - `visit(DeleteAccountTransaction deleteAccountTransaction)`: Proceseaza tranzactia de stergere a unui cont.
  - `visit(ChangeInterestRateTransaction changeInterestRateTransaction)`: Proceseaza tranzactia de modificare a ratei dobanzii.

### Visitable
- **Scop**: Interfata pentru clasele care pot fi vizitate de un `Visitor`.
- **Functionalitate**:
  - Ofera o metoda pentru a accepta vizitatori si a le permite sa proceseze obiectele curente.
- **Metoda Principala**:
  - `void accept(Visitor v)`: Permite unui vizitator sa proceseze obiectul curent.

### AccountCreatedTransaction
- **Scop**: Reprezinta o tranzactie de creare a unui cont nou.
- **Functionalitate**:
  - Extinde clasa `Transaction` si utilizeaza design pattern-ul Visitor pentru a procesa aceasta tranzactie.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite unui vizitator sa proceseze tranzactia de creare a unui cont.

### CardCreatedTransaction
- **Scop**: Reprezinta o tranzactie de creare a unui card nou.
- **Functionalitate**:
  - Contine informatii despre numarul cardului, IBAN-ul contului asociat si email-ul utilizatorului.
  - Extinde clasa `Transaction` si utilizeaza design pattern-ul Visitor.
- **Metode Principale**:
  - `getCardNumber()`: Returneaza numarul cardului.
  - `getEmail()`: Returneaza email-ul utilizatorului.
  - `getAccountIban()`: Returneaza IBAN-ul contului asociat.
  - `accept(Visitor v)`: Permite unui vizitator sa proceseze tranzactia de creare a unui card.

### CardDestroyedTransaction
- **Scop**: Reprezinta o tranzactie de distrugere a unui card.
- **Functionalitate**:
  - Contine informatii despre numarul cardului, IBAN-ul contului asociat si email-ul utilizatorului.
  - Extinde clasa `Transaction` si utilizeaza design pattern-ul Visitor.
- **Metode Principale**:
  - `getCardNumber()`: Returneaza numarul cardului distrus.
  - `getEmail()`: Returneaza email-ul utilizatorului.
  - `getAccountIban()`: Returneaza IBAN-ul contului asociat.
  - `accept(Visitor v)`: Permite unui vizitator sa proceseze tranzactia de distrugere a unui card.

### CardFrozenTransaction
- **Scop**: Reprezinta o tranzactie care marcheaza un card ca fiind blocat.
- **Functionalitate**:
  - Se foloseste atunci cand un card este blocat automat din cauza atingerii limitei minime de fonduri.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### CardPaymentTransaction
- **Scop**: Reprezinta o tranzactie de plata efectuata cu un card.
- **Functionalitate**:
  - Stocheaza informatii despre comerciant, suma platita, moneda si contul asociat.
  - Permite identificarea tranzactiilor de tip plata cu cardul.
- **Metode Principale**:
  - `getCommerciant()`: Returneaza numele comerciantului.
  - `getAmount()`: Returneaza suma platita.
  - `getCurrency()`: Returneaza moneda utilizata pentru plata.
  - `getAccount()`: Returneaza contul asociat tranzactiei.
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.
  - `isCardPayment()`: Returneaza `true`, indicand ca este o tranzactie de plata cu cardul.

### ChangeInterestRateTransaction
- **Scop**: Reprezinta o tranzactie de modificare a ratei dobanzii pentru un cont de economii.
- **Functionalitate**:
  - Stocheaza noua valoare a ratei dobanzii aplicata contului.
- **Metode Principale**:
  - `getInterestRate()`: Returneaza noua rata a dobanzii.
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### DeleteAccountTransaction
- **Scop**: Reprezinta o tranzactie de stergere a unui cont bancar.
- **Functionalitate**:
  - Stocheaza mesajul de eroare daca stergerea contului nu este posibila.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### FreezeCardTransaction
- **Scop**: Reprezinta o tranzactie care marcheaza un card ca fiind blocat din cauza atingerii limitei minime de fonduri.
- **Functionalitate**:
  - Se foloseste pentru a indica blocarea unui card cand fondurile din cont scad sub limita minima.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### InsufficientFundsTransaction
- **Scop**: Reprezinta o tranzactie care indica fonduri insuficiente pentru efectuarea unei operatiuni.
- **Functionalitate**:
  - Se utilizeaza atunci cand un utilizator incearca sa efectueze o tranzactie, dar nu are fonduri suficiente in cont.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### CardFrozenTransaction
- **Scop**: Reprezinta o tranzactie care marcheaza un card ca fiind blocat.
- **Functionalitate**:
  - Se foloseste atunci cand un card este blocat automat din cauza atingerii limitei minime de fonduri.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### CardPaymentTransaction
- **Scop**: Reprezinta o tranzactie de plata efectuata cu un card.
- **Functionalitate**:
  - Stocheaza informatii despre comerciant, suma platita, moneda si contul asociat.
  - Permite identificarea tranzactiilor de tip plata cu cardul.
- **Metode Principale**:
  - `getCommerciant()`: Returneaza numele comerciantului.
  - `getAmount()`: Returneaza suma platita.
  - `getCurrency()`: Returneaza moneda utilizata pentru plata.
  - `getAccount()`: Returneaza contul asociat tranzactiei.
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.
  - `isCardPayment()`: Returneaza `true`, indicand ca este o tranzactie de plata cu cardul.

### ChangeInterestRateTransaction
- **Scop**: Reprezinta o tranzactie de modificare a ratei dobanzii pentru un cont de economii.
- **Functionalitate**:
  - Stocheaza noua valoare a ratei dobanzii aplicata contului.
- **Metode Principale**:
  - `getInterestRate()`: Returneaza noua rata a dobanzii.
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### DeleteAccountTransaction
- **Scop**: Reprezinta o tranzactie de stergere a unui cont bancar.
- **Functionalitate**:
  - Stocheaza mesajul de eroare daca stergerea contului nu este posibila.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### FreezeCardTransaction
- **Scop**: Reprezinta o tranzactie care marcheaza un card ca fiind blocat din cauza atingerii limitei minime de fonduri.
- **Functionalitate**:
  - Se foloseste pentru a indica blocarea unui card cand fondurile din cont scad sub limita minima.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### InsufficientFundsTransaction
- **Scop**: Reprezinta o tranzactie care indica fonduri insuficiente pentru efectuarea unei operatiuni.
- **Functionalitate**:
  - Se utilizeaza atunci cand un utilizator incearca sa efectueze o tranzactie, dar nu are fonduri suficiente in cont.
- **Metode Principale**:
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### SplitPaymentTransaction
- **Scop**: Reprezinta o tranzactie de impartire a unei plati intre mai multe conturi.
- **Functionalitate**:
  - Stocheaza informatii despre suma totala, suma impartita, moneda si conturile implicate.
  - Permite gestionarea platilor impartite si identificarea erorilor in cazul fondurilor insuficiente.
- **Metode Principale**:
  - `getCurrency()`: Returneaza moneda tranzactiei.
  - `getTotalAmount()`: Returneaza suma totala.
  - `getSplitAmount()`: Returneaza suma impartita per cont.
  - `getInvolvedAccounts()`: Returneaza lista conturilor implicate.
  - `setError(String error)`: Seteaza mesajul de eroare.
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### TransferTransaction
- **Scop**: Reprezinta o tranzactie de transfer de bani intre doua conturi.
- **Functionalitate**:
  - Stocheaza informatii despre IBAN-ul expeditorului, IBAN-ul destinatarului, suma transferata si tipul de transfer.
- **Metode Principale**:
  - `getSenderIBAN()`: Returneaza IBAN-ul expeditorului.
  - `getReceiverIBAN()`: Returneaza IBAN-ul destinatarului.
  - `getAmount()`: Returneaza suma transferata.
  - `getType()`: Returneaza tipul de moneda utilizat.
  - `getTransferType()`: Returneaza tipul transferului (`sent` sau `received`).
  - `accept(Visitor v)`: Permite aplicarea design pattern-ului Visitor pentru a procesa tranzactia.

### Transaction
- **Scop**: Clasa abstracta de baza pentru toate tipurile de tranzactii.
- **Functionalitate**:
  - Contine informatii despre timestamp si descrierea tranzactiei.
- **Metode Principale**:
  - `getTimestamp()`: Returneaza timestamp-ul tranzactiei.
  - `getDescription()`: Returneaza descrierea tranzactiei.
  - `accept(Visitor visitor)`: Metoda abstracta pentru aplicarea design pattern-ului Visitor.
  - `isCardPayment()`: Returneaza `false` implicit.

### TransactionPrinter
- **Scop**: Implementeaza interfata `Visitor` pentru a vizita si converti tranzactiile in format JSON.
- **Functionalitate**:
  - Proceseaza fiecare tip de tranzactie si adauga informatiile corespunzatoare intr-un `ArrayNode` JSON.
- **Metode Principale**:
  - `visit(AccountCreatedTransaction)`, `visit(CardPaymentTransaction)`, `visit(SplitPaymentTransaction)`, etc.: Metode pentru procesarea fiecarui tip de tranzactie si adaugarea detaliilor in JSON.

### Clasele Command
- **Scop**: Le-am implementat pentru a elimina posibiltatea de a avea o functie executeCommands in main foarte mare.
- **Functionalitate**:
- Clasele Command encapsuleaza actiuni specifice, executandu-le asupra datelor aplicatiei.

## Design Patterns
- **Singleton**: Folosit in clasa `Bank` pentru a asigura existenta unei singure instante a bancii.
- **Visitor**: Aplicat pentru a gestiona diferite tipuri de tranzactii fara a modifica clasele de tranzactii.
- **Command Factory**: Aplicat pentru a crea si returna instante ale comenzilor corespunzatoare.

## Interfata Visitor si Visitable

### Visitor
- **Scop**: Defineste metode de vizitare pentru fiecare tip de tranzactie.

### Visitable
- **Scop**: Interfata pentru clasele care accepta vizitatori.
- **Metoda**: `void accept(Visitor v)`.
