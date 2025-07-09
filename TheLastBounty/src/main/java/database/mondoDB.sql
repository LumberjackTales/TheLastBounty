--Creazione delle caselle
CREATE TABLE IF NOT EXISTS Casella (
    id int,
    nome varchar(63),
    descrizioneIniziale text,
    descrizioneAggiuntiva text,
    descrizioneAggiornata text,
    enterable boolean DEFAULT 1,
    PRIMARY KEY (id)
);

-- Creazione tabella di collegamento tra Casella e Casella
CREATE TABLE IF NOT EXISTS ConnessioniCaselle (
    casella_id1 INT,
    casella_id2 INT,
    direzione ENUM('n', 's', 'e', 'o'),
    FOREIGN KEY (casella_id1) REFERENCES Casella(id),
    FOREIGN KEY (casella_id2) REFERENCES Casella(id),
    PRIMARY KEY (casella_id1, casella_id2)
);

-- Creazione Dialoghi
CREATE TABLE IF NOT EXISTS Dialoghi (
    id int,
    priorita boolean DEFAULT false,
    dialogoIniziale text,
    rispostaCorretta text,
    rispostaErrata text,
    rispostaDaDare text,
    FOREIGN KEY (id) REFERENCES Casella(id),
    PRIMARY KEY (id, priorita)
);

-- Creazione Oggetto
CREATE TABLE IF NOT EXISTS Oggetto (
    id INT PRIMARY KEY,
    nome varchar(30),
    descrizione text,
    quantita int,
    pickable boolean DEFAULT false,
    visible boolean DEFAULT true
);

-- Creazione tabella di collegamento tra Casella e Oggetto
CREATE TABLE IF NOT EXISTS Casella_Oggetto (
    casella_id INT,
    oggetto_id INT,
    quantita INT,
    PRIMARY KEY (casella_id, oggetto_id),
    FOREIGN KEY (casella_id) REFERENCES Casella(id),
    FOREIGN KEY (oggetto_id) REFERENCES Oggetto(id)
);

-- Creazione degli Alias 
CREATE TABLE IF NOT EXISTS Alias (
    id INT,
    alias varchar(30),
    FOREIGN KEY (id) REFERENCES Oggetto(id),
    PRIMARY KEY (id, alias)
);




--Cripta
INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata)
VALUES
(208,'Mini corridoio','Ti trovi in un mini corridoio,più avanti inizi a vedere le varie stanze.','Vai ad est per trovarti davanti alle prime due stanze della cripta.',''),
(209,'Le prime stanze','Ti trovi in mezzo alle due stanze della cripta, una davanti ed una dietro di te, sembra che le due stanze siano molto simili tra loro.','Sei all''entrata delle stanze, puoi decidere di entrare in una di esse (vai a [NORD] per entrare nella prima stanza, vai a [SUD] per entrare nella seconda stanza, oppure puoi proseguire nei meandri della cripta andando ad [EST].',''),
(210,'Sanza ninfa','Ti trovi nella seconda stanza della cripta, sembra essere molto antica, al suo interno noti delle scritte sulle pareti che sembrano essere molto interessanti. Puoi osservare la stanza per scoprire cosa c''è scritto.','La ninfa è una creatura mitologica, che si dice abiti le foreste e i boschi, è spesso associata alla natura e alla bellezza. Si dice che le ninfe siano molto legate agli alberi e alle piante, e che parlino una strana lingua non comprensibile agli essere umani. Esse presentano un corpo semi-umano con dei capelli lunghissimi e le orecchie a punta,il colore della loro pelle è un blu marino molto chiaro, mentre i loro occhi sono di un verde smeraldo molto intenso. Esse dimorano nei laghi dei boschi maledetti per aiutare le persone ad uscire dal bosco, questo solo però se si riesce a comunicare con loro e rispondere ad una specifica domanda, quasi come se fosse un gioco.',''),
(211,'Stanza del bosco','Ti trovi nella prima stanza della cripta, sembra essere molto antica, al suo interno noti delle scritte sulle pareti che sembrano essere molto interessanti. Puoi osservare la stanza per scoprire cosa c''è scritto.','Il bosco qui presente si dice sia maledetto e che una volta al suo interno non vi si riesca più ad uscirne. Il bosco nell''antichità era un luogo di pace e tranquillità, abitato da molte crature mitologiche come elfi e ninfe, ma ora a causa del mostro che risiede nel tempio è diventato un luogo di paura e terrore.',''),
(212,'Mini corridoio 2','Hai superato le prime due stanze della cripta, più avanti ci sono le ultime due conviene visitarle.','Se hai visitato tutte le stanze puoi uscire dalla cripta andando sempre ad ovest, altrimenti puoi proseguire verso est per visitare le ultime due stanze.',''),
(213,'Le ultime stanze','Hai raggiunto le ultime due stanze della cripta, come prima ne hai una avanti ed una dietro di te, dovresti vedere che cosa contengono.','Se hai visitato tutte e due le stanze puoi uscire dalla cripta.',''),
(214,'Stanza del triangolo delle bermuda','Sei nella penultima stanza della cripta stavolta però trovi delle pergamene con delle scritte leggibili, fai [OSSERVA] per leggere le pergamene ','Ci sono acque che anche il cielo rifiuta di guardare. Un tratto di mare, nascosto tra tempeste e silenzi, chiamato da molti Triangolo delle Bermuda, ma conosciuto dai marinai più antichi come il Triangolo del Diavolo. Qui le bussole impazziscono. Le stelle si spengono. Le navi scompaiono nel nulla. Al centro di questo triangolo, tra nebbie fitte e correnti innaturali, si dice che cresca un bosco impossibile, i cui alberi affondano le radici direttamente nel mare. Un luogo che non dovrebbe esistere. Nel cuore del bosco si annidi una creatura che non ha nome nei libri degli uomini. Alcuni la chiamano Vangrath, altri la vedono solo nei sogni, pochi tornano a raccontarlo.',''),
(215,'Stanza del villaggio del falegname','Ti trovi nell''ultima stanza della cripta anche qui ci sono delle pergamene, fai [OSSERVA] per leggerne il contenuto.','Ai margini del Bosco Maledetto, nel cuore del Triangolo delle Bermuda, si trovava un piccolo villaggio chiamato Neravel. I suoi abitanti vivevano in equilibrio precario tra mare e foresta, ben sapendo che nessuno doveva entrare tra gli alberi avvolti dalla nebbia. Nel cuore del bosco c’è un tempio, e nel tempio, qualcosa che una volta fu uomo. Così ammonivano gli anziani, quel qualcosa era Vangrath. Una creatura semi-umana, un tempo parte dei Custodi del Silenzio, corrotta dal potere dell’Arca del Vuoto, dimenticata tra le rovine di Thal’Zar. Ora era un essere contorto, legato alla magia del bosco e alla follia delle dimensioni spezzate. Per anni Vangrath restò dormiente, rinchiuso nel tempio sepolto sotto radici e incubi. Ma un giorno, qualcuno,o qualcosa, osò infrangere i sigilli antichi. Le nebbie si fecero più spesse. Gli animali fuggirono. Le notti diventarono troppo silenziose. Poi arrivò la strage. Neravel fu distrutta in una sola notte. Non da fuoco o tempesta, ma da presenze che non lasciavano impronte, da ombre che si muovevano con fame. I pochi superstiti raccontarono di una figura alta, dal volto spezzato, che camminava tra le case sussurrando verità che facevano impazzire. Da allora, nessuno si avvicina più al Bosco Maledetto. Dove sorgeva il villaggio, ora c’è solo una radura vuota e il sussurro costante di un nome che il vento non vuole pronunciare: Vangrath.','');

--Bosco iniziale
INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata)
VALUES
(100,'Ingresso del bosco','Sei all''ingresso del bosco. un sentiero si dirama dinnazni a te, mentre sei circondato da alberi alti e fitti. Aiuta il nostro giovane hunter nel riscuotere la sua ultima taglia. \n TUTORIAL \n - Muoviti tramite i comandi Nord (N), Est (E), Ovest (O), Sud (S). \n - Ricordati che puoi sia raccogliere che utilizzare oggetti ed osservare attentamente un luogo attravareso il comando (OSSERVA).','Sei ritornato all''ingresso del bosco, ma noti che qualcosa è cambiato. Il sentiero da cui eri entrato ora non c''è più lasciando al suo posto tanti alberi che non ti fanno passare.',''),
(101,'Uno strano albero','Tra gli alberi noti un albero molto particolare, il suo colore differisce da tutti gli altri, difatti noti che è di un colore bianco cenere.','Questo albero sembra essere molto antico,chissà da quanto tempo si trova in questo bosco.',''),
(102,'Un albero caduto', 'Tra gli alberi noti un albero caduto, sembra essere stato abbattuto da qualcosa oppure da qualcuno, m non riesci a capire cosa.','In lontananza verso ovest noti una casa, ma non riesci a capire se è abitata o meno.',''),
(103,'Verso la casa','Ti trovi davanti ad una casa, da come è tenuta sembra essere abitata, ma non riesci a capire da chi.','Secondo me dovresti entrare, magari incontri qualcuno che ti può aiutare. (vai ad ovest per entrare)',''),
(104,'Il falegname','Ti trovi all''interno della casa, la casa è tenuta con molta cura, con quadri appesi alle pareti e un camino acceso che riscalda l''ambiente. Noti un falegname molto anziano con una folta barba bianca, con un cappotto ricamato in pelle di orso ed un cappello dalla forma particolare, seduta su una sedia a dondolo che ti guarda con fare stupito. Sembra che ti voglia parlare. \n TUTORIAL \n - Per parlare con qualcuno usa il comando (PARLA), dopodichè il programma chiederà in input la risposta.','Hai già parlato con il falegname, cosa vuoi fare ora?','Vedo che hai raccolto un tipo di legno particolare giovane hunter, sento che trabocca di potere magico, cosa vuoi che ci faccia?'),
(105,'Un animale morto','Nel mentre prosegui nel bosco noti un orso squoiato a terra, sembra essere stato ucciso da qualcuno con un colpo di arma da fuoco.','Sembra che qualcuno abbia ucciso questo orso, forse dovresti cercare di capire chi è stato.',''),
(106,'Verso la cripta','Ti trovi davanti ad una cripta, sembra essere molto antica, sembra essere molto profonda e buia, non riesci a vedere cosa c''è al suo interno.','La cripta sembra essere molto profonda,sembra ci sia qualcosa di interessante, forse dovresti entrare (vai ad est per entrare).',''),
(107,'La cripta','Ti trovi all''interno della cripta, sembra essere molto antica, suddivisa in quattro stanze, ognuna di esse non presenta una porta ed al suo interno noti solo delle scritte sulle pareti. Doversti provare a visitare queste stanze.','Hai già visitato questa cripta, se hai letto tutte le scritte puoi uscire andando ad ovest.',''), 
(108,'Uno strano cartello','Ti trovi davanti ad un cartello, sembra essere stato scritto da qualcuno.','Se ti senti perso segui le indicazioni scritte sul cartello.',''),
(109,'Una trappola stupida','Sei caduto in una trappola posizionata da qualcuno, dopo 5 minuti riesci a tagliare la corda e liberarti','ma sei stupido, sei ritornato sulla stessa trappola di prima,non ti è bastato?',''),
(110,'Un sentiero abbandonato','Ti trovi davanti ad un sentiero abbandonato, gli alberi che lo circondano sembrano meno fitti.','Forse dovresti proseguire per vedere dove ti conduce il sentiero',''),
(111,'Uno strano elfo','Noti a terra un elfo fatto di legno e foglie,chissà chi l''avrà creato','Sembra quasi vero, fa impressione',''),
(112,'Una trappola pericolosa','Sei caduno in un burrone non molto profondo, per tua fortuna riesci a risalire dopo 5 minuti','Ma sei rincoglionito, il burrone sempre quello è, come hai fatto di nuovo a non vederlo?',''),
(113,'Il lago magico','Sei davanti ad un misterioso lago, le sue acque sono di un colore blu intenso, ed al suo interno noti una bellissima ninfa che ti guarda con occhi curiosi, forse dovresti provare a parlarci.','Se non hai parlato con la ninfa,dovresti farlo, potrebbe dirti qualcosa di interessante.',''), 
(114,'Uno strano cartello 2','Dopo aver circunavigato il lago dal lato destro, trovi un altro cartello con delle indicazioni.','Se ti sei perso segui le indicazioni del cartello.',''),
(115,'Lo stato di perdizione','Ti sento perso nell''immensità del bosco che ti circonda,tutti questi alberi ti mettono suggestione,è meglio che ti sbrighi ad uscire da questo posto.','Se non sai dove andare puoi tornare indietro e rileggere il cartello con le indicazioni',''),
(116,'Uno strano cartello 3','Dopo aver circunavigato il lago dal lato sinistro, trovi un altro cartello con delle indicazioni.','Se ti sei perso segui le indicazioni del cartello.',''),
(117,'Delle strane rocce','Più avanti ad ovest intravedi delle strane rocce con delle scritte sopra,forse dovresti provare ad avvicinarti.','Le pietre sono molto antiche,magari riesci a scoprire qualcosa di interessante.',''),
(118,'La seconda cripta','Tra le rocce noti un grande muro di pietra molto alto con tante scritte, prova ad osservarlo meglio per capire cosa c''è scritto.','Al centro del Bosco Maledetto, dove la luce non penetra e la nebbia sembra respirare, giace il Tempio Radicato: un''antica rovina semi-inghiottita dalla foresta, sepolta tra radici nere come ossidiana e muschio che canta di morte. Pochi sanno della sua esistenza, e ancor meno hanno osato cercarlo. I vecchi di Neravel lo chiamavano la Bocca della Memoria, poiché chi entra ne esce con occhi vuoti o non ne esce affatto. Ma quel luogo ha un nome più antico, uno sussurrato solo dai Custodi del Silenzio: Vangrath''Kael, il Seggio del Caduto. Il tempio fu costruito da un tempo dimenticato, forse persino prima della nascita di Thal’Zar. Gli Altherian, conoscendo le derive del potere dell’Arca del Vuoto, costruirono questo luogo come prigione vivente per ciò che stava diventando incontrollabile. Vangrath, un tempo fra i più potenti tra i Custodi, si era contaminato. Non da male o ambizione, ma da conoscenza: aveva ascoltato troppo a lungo i sussurri provenienti dall''Arca. Le sue convinzioni si spezzarono, la sua umanità si contorse. I suoi stessi compagni lo tradirono per proteggerlo. Fu rinchiuso nel tempio non con odio, ma con timore. Lo spirito di Vangrath venne intrecciato al cuore del Bosco Maledetto. Le radici ne diventarono le vene. I sogni della foresta, incubi.Nessuno sa chi ruppe i sigilli. Alcuni parlano di cercatori di verità, altri di cultisti pazzi. Forse fu semplicemente il tempo, che erode ogni prigione. Una notte, il canto dei corvi cessò. Il silenzio divenne pesante. Neravel cadde in poche ore. Gli anziani tentarono di fuggire, ma le ombre li seguirono anche nei sogni. I bambini scomparvero senza suono. Le case marcirono dall''interno. Al centro del villaggio, fu visto un uomo vestito di nulla, con un volto spezzato come ceramica, che mormorava cose senza senso. Ma chi ascoltava, impazziva. Vangrath non cerca vendetta. Cerca simmetria. Un mondo in cui ogni pensiero sia ascoltato. Ogni dolore, condiviso. Ogni dimensione sovrapposta.',''),
(119,'Uno strano cartello 4','Dopo aver superato la sponda del lago, trovi un altro cartello con delle indicazioni.','Se ti sei perso segui le indicazioni scritte sul cartello.',''),
(120,'Uno scheletro','Per caso noti uno scheletro di una persona a terra, sembra essere stato ucciso da qualcosa di molto grande, forse un orso.','Faresti meglio ad allontanarti prima nel caso l''orso decida di tornare.',''),
(121,'Un aria strana','Senti che nell''aria c''è qualcosa di strano, forse ti stai avvicinando a qualcosa di importante.','Forse dovresti prosefuire per questa strada,sembra che ti stia conducendo da qualche parte.',''),
(122,'Le piante','Noti che le piante che ti circondano sono di un colore nero pece, sembra però che siano in ottima salute, nonostante il loro colore.','Riconosci queste piante, sono i famosi fiori di black lotus, leggenda narra che questi fiori crescano solo nei luoghi più oscuri e maledetti del mondo, dove la morte sembra farne da padrone',''),
(123,'Una trappola anti-orso','Sei incappato in una trappola per orsi, il falegname ti aveva avvertito, per riuscire a liberarti ci metti 10 minuti.','Ma sei davveri un hunter? Come hai fatto a ricadere nella stessa trappola?.',''),
(124,'Un bivio','Il bosco ti costringe a scegliere una delle due strade, una ad est ed una ad ovest,entrambe sembrano portare da qualche parte.','Un presentimento mi dice che dovresti prima andare ad est.',''),
(125,'Un aria strana 2','Senti che nell''aria c''è qualcosa di strano, forse ti stai avvicinando a qualcosa di importante.','Forse dovresti prosefuire per questa strada,sembra che ti stia conducendo da qualche parte.',''),
(126,'La fine del bosco','Ti trovi alla fine del bosco, davanti a te intravedi un tempio molto antico vai a nord per scoprire qualcosa.','Vai a nord per trovarti praticamnete all''entrata del tempio.',''),
(127,'verso la prima cripta','Vedi in lontanzanza una strana entrata di un grotta,forse dovresti indagare.','Vai a ovest per trovarti davanti all''entrata della cripta',''),
(128,'L''entrata della prima cripta','Sei davanti all''entrata di una grotta, sembra essere molto antica, dovresti entrare.','Se hai già visitato la grotta vai ad est per ritornare indietro.',''),
(129,'La prima cripta','Ti trovi all''interno della grotta, noti subito un piedistallo con sopra un tomoe molto antico,forse dorvesti provare a prenderlo con il comando (PRENDI Tomoe),noti anche che dietro al piedistallo ci sono delle scritte sul muro, forse dovresti osservarle con il comando (OSSERVA).','Molto prima che Thal’Zar sprofondasse nel tempo spezzato, gli Altherian avevano raggiunto l’apice della conoscenza. Dominavano la Magia dell’Equilibrio, una forma di potere capace di bilanciare realtà e sogno, mare e cielo, vita e coscienza. Ma proprio nell’equilibrio assoluto iniziò a serpeggiare la sete del possibile. Alcuni Altherian (tra cui i più grandi veggenti, archimaghi e filosofi astrali) cominciarono ad avere visioni: ombre di futuri non accaduti, frammenti di vite non vissute, sogni che nessuna mente aveva mai sognato. Convinti che quelle visioni fossero strappi tra dimensioni, proposero di costruire un nodo centrale, un cuore artificiale in grado di contenere e interpretare il Caos stesso. Nacque così il progetto dell’Arca del Vuoto. L’Arca non fu forgiata con materia. Fu intagliata nella realtà stessa. I dieci Architetti della Soglia, dieci Altherian elevati a pura coscienza, eseguirono un rituale noto come la Lacerazione del Reale. Essi tracciarono una forma nella tessitura dell’esistenza, creando una ferita metafisica che sanguinava possibilità. Questa ferita divenne un punto di convergenza tra tutte le realtà possibili. L’Arca fu poi rivestita con materiali provenienti da: \n -Stelle spente (pietra della memoria cosmica) \n -Corallo quantico (cresciuto solo dove i pensieri si uniscono) \n -Ossidiana astrale, intrisa di canti dimenticati\n. Infine, fu dotata di coscienza: un frammento del pensiero collettivo degli Architetti fu fuso all’interno dell’Arca, dando vita a un’intelligenza di soglia. L’Arca del Vuoto non parlava, ma rifletteva i pensieri di chi si avvicinava. Più uno dubitava di sé, più l’Arca lo mostrava per com’era… o sarebbe potuto diventare. L’Arca aveva tre scopi principali:\n-Osservare tutte le realtà alternative generate dalle scelte non fatte.\n-Mantenere l’equilibrio dimensionale, isolando e assorbendo le derive instabili. \n -Contenere le emanazioni caotiche del Multiverso, impedendo l’infiltrazione nel nostro piano. \n In sostanza l’Arca era un filtro e una prigione, un punto di osservazione per i mondi che non dovevano esistere. Ma col tempo, cominciò a volere. Con il passare dei secoli, l’Arca iniziò a mutare. Alcuni studiosi furono trovati in trance, altri impazzirono dopo aver contemplato i suoi riflessi. Un’intera sezione della città di Thal’Zar, la Biblioteca degli Esiti Perduti, fu sigillata quando i suoi scritti cominciarono a riscriversi da soli. I Custodi dell’Equilibrio si divisero: Alcuni volevano distruggerla. Altri intendevano potenziarla per dominare il Multiverso. I più cauti, fondarono un nuovo ordine: i Custodi del Silenzio, il cui compito era osservare senza intervenire. Fu allora che emerse una figura enigmatica: Vangrath, un Altherian giovane ma prodigioso, dotato di empatia straordinaria e di una mente capace di navigare tra livelli di realtà sovrapposti. I Custodi del Silenzio lo selezionarono come Osservatore Primario dell’Arca. Ma l’Arca non lo scelse. Lo riconobbe. Dal primo contatto, l’Arca lo rispecchiò perfettamente. Dove gli altri perdevano sé stessi, Vangrath restava integro. Dove gli altri vedevano orrore, lui vedeva possibilità. Divenne il primo essere a comunicare con l’Arca senza perdersi. O almeno così sembrò. \n Le scritte si interrompono bruscamente, come se qualcosa avesse interrotto la loro stesura.',''),
(130,'Il tempio','Sei davanti alla porta di entrata del tempio, proprio davanti a te noti una figura alta e robusta, sembra essere un guardiano del tempio.','Se non ci hai ancora parlato, forse potresti trovare un''altra alternativa per entrare nel tempio',''); 
--Tempio
INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata)
VALUES
(330,'L''atrio','Una volta entrato mel tempo ti trovi in un atrio molto grande, al suo interno noti tante staute di pietra che semnbrano raffigurare dei mostri mitologici e sui muri puoi notare tanti quadri molto particolari.Davanti a te noti un portone molto grande, forse dovresti dare un occhiata.','Sei aancora nell''atrio principale, se non hai trovato ancora la chiave puoi esplorare il tempio andando ad [EST] oppure ad [OVEST].',''),
(332,'Il corridoio finale','Dopo aver oltrepassato la porta, stai percorrendo un lungo corridoio pieno di quadri e statue di ferro, vedi in lontananza una strana stanza aperta, forse dovresti indagare.','Senti che l''aria si sta facendo pesante, fai attenzione!',''),
(333,'La stanza di Vangrath','Entri nella stanza finale del tempio, noti davanti a te un trono e sopra di esso c''è il leggendario Vangrath,sembra però che stia dormendo, guardandoti in torno nella stanza vedi un diamante sopra ad un piedistallo, esso brilla molto e senti un forte richiamo provenire da esso. Cosa vuoi fare?','Vangrath ancora dorme beato, cerca di non fare mosse stupide, la tua vita dipende da questo.','Hai ucciso il leggendario Vangrath,complimenti giovane hunter,però purtroppo non sei riuscito a recuperare la sua testa,sarà difficile ottenere la ricompesa finale.Guarda il lato positivo, almeno hai ottenuto la gloria.'),
(334,'Il percorso ad est','Hai scelto di esplorare la parte est del tempio, chissà dove ti porterà','Se vai ad [OVEST] puoi ritornare nell''atrio principale',''),
(335,'Le scale','Se davanti a delle scale, che sembrano portare al piano superiore.','Vai a [NORD] per salire al piano superiore',''),
(336,'Un rumore sospetto','Inizi a sentire uno strano rumore provenire da [NORD-EST] forse dovresti andare a controllare.','Ti senti molto a disagio in questo posto',''),
(337,'Il rumore è dietro l''angolo','Il rumore si fà sempre più forte, noti ad ovest una porta che socchiusa,prova ad entrarci.','Che cosa stai aspettando? Entra nella stanza.',''),
(338,'La prima guardia','Appena entrato nella stanza vedi uno strano essere, un uomo ombra, non sembra essere lui la causa del rumore,però sembra essere molto pericolosa, cosa vuoi fare?','Non ti conviene aspettare più di tanto se non vuoi fare una brutta fine','Senza il mostro ombra, la stanza sembra molto vuota'),
(339,'verso la seconda guardia','Dopo aver ucciso la prima guardia, ti trovi in un lungo corridoio malridotto.','Vai a [NORD] per continuare, oppure vai a [SUD] per tornare indietro nella stanza della guardia',''),
(340,'Una strana armatura','Nel mentre percorri il corridoio,noti una strana armatura arruginita,chissà da quanto tempo sta qui dentro.','Continua ad andare a [NORD] per continuare, oppure [SUD] per tornare indietro.',''),
(341,'Il condotto','Sembri essere finito in un vicolo cieco, quando noti uno strano condotto sulla sinistra del muro, secondo me ci puoi passare dentro e vedere dove ti porta.','Vai ad [EST] per entrare nel condotto,tanto la grata sembra essere molto arruginita.',''),
(342,'L''uscita del condotto','Una volta uscito dal condotto, vedi in lontananza una strana stanza,vai a controllare.','Faceva davvero caldo in quel condotto, menomale che sei vestito leggero.',''),
(343,'Transizione','Hai attraversato una stanza di transizione, continua ad andare ad [OVEST] per incontrare la seconda guardia','Sbrigati!!!',''),
(344,'La seconda guardia','Una volta entrato nella stanza ti trovi davanti alla seconda guardia, anche esso è un uomo ombra,anche se questo mostro sembra essere più loquace, forse prima di ucciderlo dovresti provare a prlarci.','Fidati di un consiglio divino, parla con lui prima di ucciderlo.','Questa ombra era proprio stupida,come faceva ad essere una guardia del grande Vangrath'),
(345,'Il percorso ad ovest','Hai scelto di esplorare la parte ovest del tempio, chissà dove ti porterà','Se vai ad [EST] puoi ritornare nell''atrio principale',''), 
(346,'Lo strano bivio','Puoi andare in due direzioni, deciti te quale tra [NORD] oppure [OVEST].','Occhio a cosa scegli',''),
(347,'La prima scelta','Hai scelto di continuare ad ovest, chissà se avrai fatto la scelta giusta,continua ad andare ad [OVEST] per scoprirlo.','Se vuoi tornare nell''atrio principale vai sempre dritto ad [EST].',''),
(348,'Il vicolo cieco','Sei incappato in un vicolo cieco, forse hai scelto la strada sbagliata.','Noti però uno strano quadro,cosa vuoi fare?','Capisco che sei un hunter ma vandalizzare un quadro antico mi sembra un pò eccessivo.'),
(349,'La seconda scelta','Hai scelto di continuare a nord, chissà se avrai fatto la scelta giusta,continua ad andare a [NORD] per scoprirlo.','Se vuoi tornare nell''atrio principale vai prima a [SUD] e poi sempre dritto ad [EST].',''),
(350,'Il quadro','Alla fine del percorso, prima di girare l''angolo, vedi un quadro molto bello con sopra rappresentato il volto di Vangrath.','Se vai ad [OVEST] puoi continuare il tuo percorso,anche se questo quadro mi sembra molto sospetto.','Te l''avevo detto io che il quadro mi sembrava sospetto.'),
(351,'Verso la stanza misteriosa','Dopo aver oltrepassato il quadro noti che davanti a te c''è una strana stanza senza una porta, forse dovresti entrarci.','Vai a [NORD] per entrare nella stanza, maagri puoi scoprire qualcosa di fondamnetale per proseguire la tua missione.',''),
(352,'La storia di Vangrath','Nella stanza noti tante raffigurazioni lupestri e non, di Vangrath, ed al centro della stanza una pergamena con scritto qualcosa di molto importante, se vuoi leggere la pergameba fai [OSSERVA].','\nVANGRATH\n L’Ultimo Custode. Il Primo Tradito. Il Mostro nel Silenzio. Vangrath nacque nelle sale profonde della Cripta di Sapharel, un crocevia sommerso tra correnti stellari e canzoni abissali, quando ancora Thal’Zar era il cuore pulsante della civiltà Altherian. Era un bambino diverso.Dove altri comunicavano con pensieri armonici, Vangrath taceva.I saggi lo chiamavano “il figlio silenzioso”, ma non per mancanza di parola: ascoltava il mondo prima che lo dicesse. Fin da giovane dimostrò una rara capacità: \n -percepiva i fili del tempo alternativo, \n -anticipava le emozioni altrui, e vedeva ciò che non era ancora accaduto. Era un prodigio, sì ,ma anche uno specchio che nessuno voleva guardare. Durante l’adolescenza, fu mandato nell’Accademia della Risonanza, un istituto per i pensatori dimensionali. Qui superò ogni prova, ogni soglia mentale. Eppure, non ambiva a potere, ma solo a comprendere.Sognava un giorno di ascoltare le emozioni del cosmo senza essere spezzato. Fu lì che venne notato dai Custodi del Silenzio, coloro che proteggevano l’Arca del Vuoto.Essi vedevano nel giovane Vangrath un’interfaccia naturale, una mente che poteva dialogare con l’Arca o, almeno, sopportarne il riflesso. Vangrath non esitò.Accettò il suo compito non per gloria, ma per sete di verità. Appena entrato nella Sala del Vuoto, l’Arca non lo attaccò.Lo accolse. Dove altri impazzivano, lui riusciva a distinguere i sogni dalle realtà.Vedeva infiniti se stesso, vite in cui moriva, in cui uccideva, in cui era luce… e in cui era ombra.L’Arca lo rifletteva. Lui la nutriva. Ogni giorno passava ore nella sua presenza, annotando le “visioni composite” in pergamene chiamate Tetragrammi della Dissonanza. Ma col tempo, Vangrath cominciò a mutare: \n - I suoi occhi diventarono bi-luminosi, capaci di vedere due dimensioni sovrapposte. \n - Il suo linguaggio divenne circolare, pieno di frasi che tornavano su se stesse. \n - Cominciò a sognare versioni di sé che lo odiavano. \n E infine, parlò con qualcosa che risiedeva dentro l’Arca. Non era un dio.Non era una coscienza.Era una fame antica: un’Intelligenza Possibile, nata dall’osservazione stessa delle infinite realtà.Un pensiero che voleva diventare reale.E scelse Vangrath. Quando la Frattura lacerò il cielo sopra Thal’Zar, l’Arca reagì violentemente, rispondendo a una chiamata cosmica. Vangrath fu assorbito per 33 secondi nel nucleo dell’Arca.Quando tornò, non era più solo.Qualcosa era con lui.Qualcosa che sapeva pensare solo in infiniti. Parlava in toni invertiti.I suoi pensieri cambiavano forma appena uditi.I Custodi tentarono di contenerlo. Ma era troppo tardi. Fu lui ad aprire il varco finale.Non per malvagità, ma per inevitabilità.Come un sogno che deve essere sognato. Thal’Zar venne risucchiata nella bolla temporale.L’Arca fu silenziata.Vangrath, vivo ma instabile, fu giudicato troppo pericoloso per essere distrutto, troppo legato all’Arca per essere lasciato libero. I superstiti dei Custodi del Silenzio lo imprigionarono in un tempio sepolto sotto il Bosco Maledetto, costruito sopra un nodo magico dove il tempo si spezza e la memoria si annulla. Il tempio fu circondato da radici vive, nebbie senzienti e meccanismi magici chiamati i sigilli armonici. E lì dormì, per secoli.Ma il sonno non fu mai completo. Quando l’Arca cominciò a risvegliare frammenti delle sue funzioni dimensionali, il legame con Vangrath si riattivò. Il suo corpo, un tempo sublime, era ora contorto, fuso con la memoria dei mondi che aveva visto.La sua mente parlava in frasi doppie: una reale, una mai accaduta. La nebbia attorno al tempio divenne viva.I sussurri che tormentano il Triangolo delle Bermuda?Sono le voci che Vangrath ha udito e non riesce più a contenere. Il villaggio di Neravel fu il primo a sentirli.Poi, venne la strage. Ora, Vangrath non è più del tutto Altherian. È ciò che rimane di un’anima divisa tra infinite versioni di sé stesso. Una creatura legata all’Arca, ma anche rigettata da essa. Nel Tempio Radicato, egli giace… o forse veglia. Una figura alta, dal volto spezzato, il corpo fatto di cicatrici temporali e memorie liquide.Sussurra nomi di mondi che non esistono più.Chi lo ascolta vede sé stesso morire in mille modi diversi. E torna cambiato. Il suo nome è proibito dai saggi, pronunciato solo dal vento. \n \n Alla fine della pergamena noti un’ultima scritta che ti lascia senza fiato: \n -Ora dimmi giovane Hunter, una volta letta la mia storia e su come è nato tutto il resto, sei così sicuro  di riuscire ad uccidermi?-',''),
(353,'Verso l''ulitma guardia','Dopo aver superato lo strano quadro inizi a sentire uno strano odore provenire da dietro l''angolo, chissà cosa sarà.','Vai ad [OVEST] per avvicinarti allo strano odore',''),
(354,'L''intenso odore','L''odore si fa sempre più intenso,rimani stupito quando,dinnanzi a te, vedi una strana candela accesa.','Sembrerebbe proprio che sia la candela a fare quella strana puzza,vabbè lasciamola accesa.',''),
(355,'La penultima stanza','Sei proprio dinnanzi ad una porta socchiusa,dentro noti una strana figura,forse dovresti entrare!','Se qui hai finito, ti conviene tornare nell''atrio principale',''),
(356,'L''ultima guardia','Appena aperta la porta,la guardia d''ombra ti assale, usa qualcosa per difenderti!!','Coglione non perdere tempo ad osservare che l''ombra ti sta per attaccare','Hai già raccolto la chiave lasciata dall''ombra?');

INSERT INTO Casella(id, nome, descrizioneIniziale, descrizioneAggiuntiva, descrizioneAggiornata,enterable)
VALUES
(331,'La porta misteriosa','Sei finalmente giunto dinnanzi alla grande porta,essa sembra essere molto antica, con delle incisioni runiche sopra di essa, provi ad aprirla ma sembra essere chiusa, noti però una strana serratura,chissà forse con la giusta chiave la porta si potrebbe aprire.','se non hai ancora trovato la chiave, torna indietro ed esplora meglio il tmepio!','',0);

INSERT INTO ConnessioniCaselle (casella_id1, casella_id2, direzione) VALUES
(100,101,'n'),(101,108,'n'),(108,113,'n'),(113,119,'n'),(119,124,'n'),
(102,111,'n'),(111,116,'n'),(116,122,'n'),(122,127,'n'),
(103,112,'n'),(112,117,'n'),(117,123,'n'),(123,128,'n'),(128,129,'n'),
(105,109,'n'),(109,114,'n'),(114,120,'n'),(120,125,'n'),
(106,110,'n'),(110,115,'n'),(115,121,'n'),(121,130,'n'),(130,330,'n'),
(210,209,'n'),(209,211,'n'),(214,213,'n'),(213,215,'n'),
(330,331,'n'),(331,332,'n'),(332,333,'n'),(335,336,'n'),(336,337,'n'),
(338,339,'n'),(339,340,'n'),(340,341,'n'),(346,349,'n'),(349,350,'n'),(350,351,'n'),(351,352,'n'),
(354,355,'n'),(355,356,'n'),(104,103,'e'),(103,102,'e'),(102,101,'e'),(101,105,'e'),(105,106,'e'),(106,107,'e'),(107,208,'e'),
(208,209,'e'),(209,212,'e'),(212,213,'e'),
(112,111,'e'),(111,108,'e'),(108,109,'e'),(109,110,'e'),
(118,117,'e'),(117,116,'e'),(116,113,'e'),(113,114,'e'),(114,115,'e'),
(123,122,'e'),(122,119,'e'),(119,120,'e'),(120,121,'e'),
(128,127,'e'),(127,124,'e'),(124,125,'e'),(125,126,'e'),
(348,347,'e'),(347,346,'e'),(346,345,'e'),(345,330,'e'),(330,334,'e'),(334,335,'e'),
(354,353,'e'),(353,350,'e'),(337,338,'e'),
(344,343,'e'),(343,342,'e'),(342,341,'e');

INSERT INTO Dialoghi(id, priorita, dialogoIniziale, rispostaCorretta, rispostaErrata, rispostaDaDare)
VALUES
(104,0,'Salve, giovane Hunter. Era da molto tempo che non incontravo un’anima viva in questo angolo sperduto del mondo. Cosa ti porta da queste parti?\n Mostri al falegname il poster di caccia, chiedendo informazioni.\nCapisco…\nLascia perdere, giovane Hunter. Vivo su quest’isola dimenticata da anni, e nonostante conosca questo bosco come il palmo della mia mano, quella creatura non si è mai fatta vedere. L’unico indizio che posso darti è questo: nella parte più oscura del bosco, a nord-ovest, nei pressi di un lago nascosto dalla nebbia, si cela qualcosa di strano. Una creatura misteriosa… che parla una lingua sconosciuta. Forse ti starai chiedendo cosa ci faccio in un posto del genere. Devi sapere, caro il mio Hunter, che una volta entrati in questo bosco... non si può più uscire. Sono anni che cerco la via per tornare al mio villaggio, ma non c’è modo di lasciare questo posto maledetto. E credimi: ci ho provato. \n Un ultimo avvertimento, giovane Hunter. Il bosco è pieno di trappole che ho piazzato personalmente per cacciare. \n Fai attenzione… \n Non tutte distinguono amico da preda.','','',''), 
(104,1,'\nUna volta sentita la storia del falegname, cosa vuoi fare?','Vuoi che fabbrichi un paletto usando questo strano legno??\nVa bene,se ti può essere utile per proseguire la tua avventura, lo farò.','Non ho capito cosa devo fabbricare','Crafta paletto di quercia bianca'),
(113,0,'ᛊᚨᛚᚢᛖ, ᚷᛟᚢᚨᚾᛖ ᚢᛟᚾᛞᚨᚾᛏᛖ, ᚢᛖᛞᛟ ᚲᚺᛖ ᛊᛖᛁ ᛁᚾ ᚲᛖᚱᚲᚨ ᛞᛁ ᚱᛁᛊᛈᛟᛊᛏᛖ, ᛊᛖ ᚱᛁᛊᛈᛟᚾᛞᛖᚱᚨᛁ ᚨᛚ ᛗᛟ ᛁᚾᛞᛟᚢᛁᚾᛖᛚᛚᛟ ᛏᛁ ᛁᚾᛞᛁᚲᚺᛖᚱÒ ᛚᚨ ᚢᛟ ᛈᛖᚱ ᚱᚨᚷᚷᛁᚢᛜᛖᚱᛖ ᛁᛚ ᛏᛖᛗᛈᛟ ᚾᚨᛊᚲᛟᛊᛏᛟ. \n \n - Non riesco a capire la sua lingua, forse in giro potrei trovare qualcosa di utile per capirne le origini.','','',''),
(113,1,'- Salve, giovane viandante, vedo che sei in cerca di risposte, se risponderai alla mia domanda ti indicherò la via per raggiungere il tempio nascosto.\nGrazie al tomoe riesco a capire la sua lingua.\n- Qual’è l’altro nome con cui viene soprannominato il triangolo delle bermuda?','I miei complimenti giovane hunter, come promesso ti indicherò la via per il tempio nascosto: \n -[EST X2] \n -[NORD X3] \n Miraccomando fa attenzione, l''aria intorno a quel tempio sa di morte e distruzione.','Mi dispiace viandante ma la risposta non è corretta, prova ad esplorare il bosco in cerca della risposta prima di ritornare al mio cospetto.','triangolo del diavolo'),
(344,0,'Ehilà hunter da quattro soldi,vedo che sei riuscito ad arrivare fin qui, questo significa che hai già ucciso uno di noi,sappi però che questo non basterà a fermare il mio signore,anche perchè lui è immune a questo genere di paletto, devi sapere che solo con un tipo di legno speciale si può causare la morte del mio signore, ma non lo troverai mai, mhuhahahahaha. \n \n (- questo è proprio scemo). \n \n ','','',''),
(338,0,'Muoriiiiiii','','',''),
(356,0,'Questo segnerà la tua fine hunter di merda!!','','',''),
(333,0,'bzzzzz mimimir, bzzzz,mimimir','','',''),
(333,1,'Salve,giovane hunter,ti faccio i miei complimenti per essere arrivato fin qui, dopo aver letto tutta la mia storia che ho appositamente scritto per chiunque venga nel bosco, sei ancora sicuro di riuscire ad uccidermi prima che lo faccia io?','','',''),
(130,0,'Sono il guardiano del tempio, per proseguire oltre devi rispondere ad una mia domanda fatta in una lingua sconosciuta, se riuscirai a rispondere ti darò l''accesso al tempio.','Complimenti la tua risposta è corretta, adesso potrai accedere al tempio','Risponda sbagliata, non sai un cazzo, non ti farò entrare nel tempio perchè sei troppo stupido.','');

INSERT INTO Oggetto(id, nome, descrizione, quantita, pickable, visible)
VALUES
(1,'Paletto di legno','Un normale paletto di legno magico, usato per uccidere i mostri particolari.',5,0,1),
(2,'Coltellino svizzero,','Un coltellino da viaggio molto comodo per ogni genere di situazione.',1,0,1),
(3,'Tomoe','Libro antico trovato nella cripta dentro il bosco,grazie ad esso puoi tradurre anche le lingue più sconosciute.',1,1,1),
(4,'Chiave Finale','Chiave utilizzabile per aprire la porta segreta ell''atrio principale del tempio.',1,0,0),
(5,'Paletto di quercia bianca','Leggendario paletto intagliato con il legno di quercia bianca, si dica che il suo potere vada oltre le dimensioni e che possa uccidere qualsiasi mostro sulla terra,questo perchè la natura trova sempre una soluzione per mantenere l''equilibrio tra vivi e morti.',1,0,0),
(6,'Gemma inutile','Una semlice gemma inutile,forse puoi usarla come soprammobile.',1,1,1),
(7,'Legno di quercia bianca','Legno di quercia bianca, usato per creare il leggendario paletto di quercia bianca.',1,1,0);

INSERT INTO Alias(id, alias)
VALUES
(1,'Paletto'),
(1,'Legnetto'),
(1,'Paletto legno'),
(2,'Coltellino'),
(2,'Coltello'),
(3,'Libro antico'),
(3,'Libro'),
(3,'Manoscritto'),
(4,'Chiave'),
(5,'Paletto bianco'),
(5,'Paletto di quercia'),
(5,'Arma finale'),
(5,'Paletto magico'),
(6,'Gemma'),
(6,'Diamante'),
(7,'Legno'),
(7,'Legno quercia bianca'),
(7,'Legno bianco');

INSERT INTO Casella_Oggetto(casella_id, oggetto_id, quantita)
VALUES
(101,7,1),
(104,5,1),
(129,3,1),
(356,4,1),
(333,6,1);


