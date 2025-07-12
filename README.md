Descrizione dell’avventura

The Last Bounty è un'avventura testuale. l'utente giocherà nei panni di un Cacciatore di taglie, il cui ultimo incarico è quello di liberare 
Descrivere brevemente l’avventura testuale/grafica realizzata.

Progettazione
Fornire dettagli sulla progettazione. Come sono state individuate le classi, quali sono le competenze di ogni classe, come sono state organizzate le classi in package.

Diagramma delle classi
Inserire una diagramma delle classi di una porzione significativa del progetto e commentare il diagramma fornendo dettagli sui principi della programmazione ad oggetti che sono stati utilizzati (ereditarietà, interfacce, classi astratte, composizione, …)

CLASSI

CLASSIFICA

	1.	classificaClientRecordServer = Gestisce i dati del tempo e dell’utente quando viene una partita attraverso l’utilizzo del Client e del Server
	2.	Client = gestisce l’utente che sta giocando la partita
	3.	Record = Crea il record con nome utente, tempo impiegato per finire il gioco e data
	4.	Server. = avvia un server che aspetta che venga aggiunto il record


COMANDI

	1.	Command = attraverso l’implementazione di serializable gestisce i comandi che l’utente scrive
	2.	CommandType = Lista dei comandi disponibili all’interno del gioco

COMPONENTI AGGIUNTIVI

	1.	Chrono = gestisce il tempo
	2.	GameDescription = gestisce le informazioni del gioco
	3.	GameObservable = gestisce l’osservazione del gioco ( quando non riceve input lo stato del gioco è in osservazione idle
	4.	GameObserver = gestisce gli observer del gioco
	5.	Musica = gestisce tutti i metodo della musica
	6.	StampaTesto = attraverso l’utilizzo dei thread gestisce come il thread viene stampato a video
	7.	Utils = legge i comandi ed elimina le stopwords

DATABASE

	1.	Casella = gestisce la casella le descrizioni e le coordinate
	2.	GestioneDB = carica il DB in GameDescription e si occupa della connessione tra le caselle
	3.	MondoDB.sql = database del gioco

ECCEZIONI

	1.	GameFileException = si occupa di lanciare un’eccezione se il file di salvataggio da caricare non esiste o è corrotto

	2.	GameNotAvailableException = stampa eccezione se c’è un errore nell’inizializzazione del gioco
	3.	GetClassificaException. = stampa eccezione se non riesce a salvare i dati nella classifica
	4.	SendRecordException = Stampa l’eccezione se non tra un server in ascolto oppure se il record inviato non arriva al server (gestisce l’invio del record al server)

GIOCATORE

	1.	Dialogo = gestisce i dialoghi del gioco con gli NPC
	2.	Inventario = gestisce l’inventario del giocatore
	3.	Item = gestisce il tipo di item presente nel database

GRAFICA

	1.	InterfacciaClassifica = gestisce l’interfaccia della classifica
	2.	InterfacciaFineGioco = gestisce l’interfaccia di Fine Gioco
	3.	InterfacciaGioco. = Gestisce l’interfaccia di gioco
	4.	InterfacciaImpostazioni =Gestisce l’interfaccia delle impostazioni
	5.	InterfacciaInventario = gestisce l’interfaccia dell’inventario
	6.	InterfacciaIniziale = gestisce l’interfaccia della schermata iniziale
	7.	InterfacciaRiconoscimenti = gestisce l’interfaccia dei riconoscimenti
	8.	InterfacciaMorte = gestisce l’interfaccia di morte


OBSERVER

	1.	CreaObserver = Gestisce il comando crea
	2.	HelpObserver = Gestisce il comando Help/Aiuto
	3.	MorteObserver = Gestisce il comando Muori o se il giocatore muore all’interno di una partita
	4.	MuoviObserver = gestisce il movimento del giocatore
	5.	OsservaObserver = gestisce il comando osserva che il giocatore può fare in una stanza o su un oggetto
	6.	ParlaObserver = gestisce le interazioni con gli NPC
	7.	PrendiObserver = gestisce il comando prendi degli ITEM
	8.	SalvaObserver = gestisce il comando slava per i salvataggi del gioco
	9.	UsaObserver = gestisce il comando usa per gli oggetti del gioco
	10.	TheLastBounty = gestisce l’aggiunta e la creazione di tutti gli observer con i vari comandi

PARSER

	1.	Parser = gestisce l’input dell’utente e lo trasforma in eventuali comandi
	2.	ParserOutput = una volta gestito l’input dell’utente mostra a video il risultato

REST

	1.	QuizAPI = gestisce il dialogo speciale grazie all’utilizzo delle RESTFul per prendere da un sito esterno domande sulla mitologia ponendole al giocatore come fosse un quiz

thelastbounty

	1.	TheLastBounty = gestisce l’avvio del programma


Specifica algebrica


## 4 - Specifiche Algebriche
Due delle strutture dati più utilizzate nel nostro progetto sono la **Mappa** e la **Lista**, in questa sezione verranno presentate le specifiche algebriche di entrambe.

### 4.1 - Specifica algebrica della Lista
La lista è una struttura dati che permette di memorizzare e recuperare informazioni sfruttando l'indice di posizione degli elementi contenuti.
### Specifica sintattica
<table>
    <thead>
        <tr>
            <th colspan="2">Tipi</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td colspan="2"><code>List</code>, <code>Item</code>, <code>Integer</code>, <code>Boolean</code></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><strong>Operatori</strong></td>
        </tr>
        <tr>
            <td><code>newList() -> List</code></td>
            <td>Crea una nuova lista vuota</td>
        </tr>
        <tr>
            <td><code>add(List, Item, Integer) -> List</code></td>
            <td>Aggiunge un elemento alla lista nella posizione specificata</td>
        </tr>
        <tr>
            <td><code>isEmpty(List) -> Boolean</code></td>
            <td>Restituisce <code>true</code> se la lista è vuota altrimenti <code>false</code></td>
          </tr>
            <tr>
                <td><code>getLastIndex(List) -> Integer</code></td>
                <td>Restituisce l'ultima posizione occupata da un elemento</td>
            </tr> 
            <tr>
                <td><code>getIndex(List, Item) -> Integer</code></td>
                <td>Restituisce la posizione dell'elemento specificato</td>
            </tr> 
            <tr>
                <td><code>getItem(List, Integer) -> Item</code></td>
                <td>Restituisce l'elemento nella posizione specificata</td> 
            </tr> 
            <tr>
                <td><code>remove(List, Integer) -> List</code></td>
                <td>Rimuove dalla lista l'elemento nella posizione specificata</td>  
            </tr>
            <tr>
                <td><code>contains(List, Item) -> Boolean</code></td>
                <td>Restituisce <code>true</code> se l'elemento specificato è contenuto nella lista</td>
            </tr>
    </tbody>
</table>
Si noti come <code>Item</code> è un tipo generico, che può essere sostituito con qualsiasi altro tipo di dato.

<code>Interger</code> e <code>Boolean</code> invece, sono tipi ausiliari alla definizione della specifica algebrica della lista.

### Osservazioni e Costruttori

<table>
  <thead>
    <tr>
      <th></th>
      <th colspan="2">Costruttori di l'</th>
    </tr>
  </thead>
  <tbody align="center">
    <tr>
      <td><strong>Osservazioni</strong></td>
      <td><code>newList</code></td>
      <td><code>add(l, it, id)</code></td>
    </tr>
    <tr>
      <td><code>isEmpty(l')</code></td>
      <td><code>true</code></td>
      <td><code>false</code></td>
    </tr>
    <tr>
      <td><code>getLastIndex(l')</code></td>
      <td><code>error</code></td>
      <td>if <code>isEmpty(l)</code> then <code>1</code> else <code>getLastIndex(l) + 1</code></td>
    </tr>
    <tr>
      <td><code>getIndex(l', it')</code></td>
      <td><code>error</code></td>
      <td>if <code>it = it'</code> then <code>id</code> else <code>getIndex(l, it')</code></td>
    </tr>
    <tr>
      <td><code>getItem(l', id')</code></td>
      <td><code>error</code></td>
      <td>if <code>id = id'</code> then <code>it</code> else <code>getItem(l, id')</code></td>
    </tr>
    <tr>
      <td><code>remove(l', id')</code></td>
      <td><code>error</code></td>
      <td>if <code>id = id'</code> then <code>l</code> else <code>add(remove(l, id'), it)</code></td>
    </tr>
    <tr>
      <td><code>contains(l', it')</code></td>
      <td><code>false</code></td>
      <td>if <code>it = it'</code> then <code>true</code> else <code>contains(l, it')</code></td>
    </tr>
  </tbody>
</table>

### Specifica semantica
- **DECLARE**
  - <code>l</code>, <code>l'</code>: <code>List</code>
  - <code>it</code>, <code>it'</code>: <code>Item</code>
  - <code>id</code>, <code>id'</code>: <code>Integer</code>

Fornire una specifica algebrica di una struttura dati a scelta tra quelle utilizzate nel progetto. Deve essere fornita una specifica algebrica non assiomatica!

Dettagli implementativi
Per ciascun argomento del corso spiegare se e come è stato utilizzato all’interno del progetto.
Programmazione generica
bla bla bla…
File
bla bla bla…
Database (JDBC)
bla bla bla…
Lamba Expression (compresi stream e pipeline)
bla bla bla…
SWING
bla bla bla…
Thread e programmazione concorrente
bla bla bla…
Socket e/o REST
bla bla bla…

La struttura del documento è solo un suggerimento. È possibile modificarla purché il documento contenga le informazioni richieste. È possibile inserire altre sezioni, ad esempio: soluzione del gioco, dettagli sull’organizzazione del lavoro di gruppo, ecc…
Note sulla valutazione

Il caso di studio verrà valutato in una scala da 0 a 50. Il voto finale verrà rapportato in trentesimi.

Il voto è determinato da 10 criteri, ognuno dei quali può avere un voto tra 0 e 5. I criteri sono:
qualità dell’avventura
qualità della programmazione ad oggetti
utilizzo dei file
utilizzo di database/JDBC
utilizzo dei thread e della programmazione concorrente
utilizzo delle socket e/o delle REST
utilizzo delle SWING
utilizzo delle lambda expression, stream e pipeline
qualità della documentazione (documentazione progetto + documentazione codice)
punteggio bonus che tiene conto della complessità del progetto rapportata anche al numero dei componenti del gruppo

Tutto il materiale deve essere consegnato 5 giorni prima della prova orale. Deve essere consegnato tramite mail allegando uno zip o un link per il download. Il testo della mail deve riportare in modo chiaro tutti i membri del gruppo.
Svolgimento della prova orale
Ogni gruppo presenterà il caso di studio e una demo live dell’avventura realizzata per un tempo massimo di 20 minuti. È possibile preparare delle slide, ma non è obbligatorio. Al termine della presentazione, ogni membro del gruppo sarà interrogato su tutti gli argomenti del corso.

Il voto finale terrà conto sia della valutazione del caso di studio sia della qualità della prova orale.

Le note sulla valutazione e lo svolgimento della prova vanno eliminate dal documento che verrà consegnato.

