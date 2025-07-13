package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import giocatore.Dialogo;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class QuizAPI {

	
	private class ResponseAPI {
		private float response_code;
		List<Results> results = new ArrayList<>();

		
		public float getResponse_code() {
			return response_code;
		}

		
		public List<Results> getResults() {
			return results;
		}
	}

    /**
     * *classe interna che rappresenta i risultati della risposta dell'API 
     */
    public class Results {
		private String type;
		private String difficulty;
		private String category;
		private String question;
		private String correct_answer;
		private List<String> incorrect_answers;

            /**
             * * Restituisce le risposte errate.
             * @return
             */
            public List<String> getIncorrect_answers() {
			return incorrect_answers;
		}

            /**
             *
             * @param incorrect_answers
             */
            public void setIncorrect_answers(List<String> incorrect_answers) {
			this.incorrect_answers = incorrect_answers;
		}

            /**
             *
             * @return
             */
            public String getType() {
			return type;
		}

            /**
             *
             * @return
             */
            public String getDifficulty() {
			return difficulty;
		}

            /**
             *
             * @return
             */
            public String getCategory() {
			return category;
		}

            /**
             *
             * @return
             */
            public String getQuestion() {
			return question;
		}

            /**
             *
             * @return
             */
            public String getCorrect_answer() {
			return correct_answer;
		}

            /**
             *
             * @param type
             */
            public void setType(String type) {
			this.type = type;
		}

            /**
             *
             * @param difficulty
             */
            public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}

            /**
             *
             * @param category
             */
            public void setCategory(String category) {
			this.category = category;
		}

            /**
             *
             * @param question
             */
            public void setQuestion(String question) {
			this.question = question;
		}

            /**
             *
             * @param correct_answer
             */
            public void setCorrect_answer(String correct_answer) {
			this.correct_answer = correct_answer;
		}
	}

	//si occupa di fare una richiesta all'API per ottenere una domanda di quiz
	//restituisce la risposta 
	private static ResponseAPI getResponseAPI() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://opentdb.com/api.php");
		Response resp = target.queryParam("amount", 1)
				.queryParam("category", 20)
				.queryParam("type", "multiple")
				.request(MediaType.APPLICATION_JSON).get();
		String jsonString = resp.readEntity(String.class);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, ResponseAPI.class);
	}

    /**
     * * Restituisce un dialogo con una domanda di quiz.
     * @param dialogo
     * @return
     */
    public static Dialogo getQuiz(Dialogo dialogo) {
		ResponseAPI r;
		int maxRequest = 5;
		int totalRequest = 0;

		do {
			r = getResponseAPI();
			if (r.getResponse_code() == 0) {
				
				String s = "\"Sono il guardiano del tempio, per proseguire oltre devi rispondere ad una mia domanda fatta in una lingua sconosciuta, se riuscirai a rispondere ti darò l'accesso al tempio."
						+ "\n" + Tutorial() 
						+ r.getResults().get(0).getQuestion().replaceAll("&quot;", "\"").replaceAll("&#039;", "'");
				s += "\n";
				List<String> allAnswer = new ArrayList<>();
				allAnswer.add(
						r.getResults().get(0).getCorrect_answer().replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));

				for (String answer : r.getResults().get(0).getIncorrect_answers()) {
					allAnswer.add(answer.replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));
				}

				allAnswer.sort((a, b) -> a.length() - b.length());
				for (String answer : allAnswer) {
					s += "\t" + answer + "\n";
				}
				dialogo.setTestoDialogo(s);
				dialogo.setRisposta(r.getResults().get(0).getCorrect_answer());
				System.out.println(r.getResults().get(0).getCorrect_answer());

			} else {
				totalRequest++;
			}
		} while (r.getResponse_code() != 0 && totalRequest < maxRequest);

		return dialogo;
	}

    /**
     * tutorial quiz
     * @return
     */
    public static String Tutorial(){

		String tutorial = " \n \t \t TUTORIAL SUL QUIZ \n \n Ti verra proposta una domanda a tema mitologia antica ,con 4 risposte possibili, di cui una sola è corretta. \n Essendo che la domanda vieni generata casualmente da un sito esterno di API, la domanda verrà posta in inglese, se non sai l'inglese faresti bene ad impararlo dato che siamo nel 2025 e non nel 1940!! \n Ma non preoccuparti, basta rispondere con il nome della risposta che secondo te è corretta, ad esempio se la risposta corretta è [Cerbero] tra le 4 opzioni , basta rispondere con il nome [Cerbero] e il gioco proseguirà con il dialogo del guardiano. Se sbagli a rispondere alla domanda, puoi sempre riprovare rifacendo il comando [PARLA], oppure puoi trovare un altra soluzione.... \n \n Buona fortuna!!. \n \n";
		return tutorial;
	}
}
