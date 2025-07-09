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


public class QuizAPI {

	/**
	 * Classe interna che rappresenta la struttura della risposta API.
	 */
	private class ResponseAPI {
		private float response_code;
		List<Results> results = new ArrayList<>();

		/**
		 * @return il codice di risposta dell'API
		 */
		public float getResponse_code() {
			return response_code;
		}

		/**
		 * @return la lista dei risultati ottenuti dall'API
		 */
		public List<Results> getResults() {
			return results;
		}
	}

	/**
	 * Classe che rappresenta la struttura di un singolo risultato quiz.
	 */
	public class Results {
		private String type;
		private String difficulty;
		private String category;
		private String question;
		private String correct_answer;
		private List<String> incorrect_answers;

		/**
		 * @return la lista delle risposte errate
		 */
		public List<String> getIncorrect_answers() {
			return incorrect_answers;
		}

		/**
		 * @param incorrect_answers la lista delle risposte errate da impostare
		 */
		public void setIncorrect_answers(List<String> incorrect_answers) {
			this.incorrect_answers = incorrect_answers;
		}

		/**
		 * @return il tipo di domanda
		 */
		public String getType() {
			return type;
		}

		/**
		 * @return la difficoltà della domanda
		 */
		public String getDifficulty() {
			return difficulty;
		}

		/**
		 * @return la categoria della domanda
		 */
		public String getCategory() {
			return category;
		}

		/**
		 * @return il testo della domanda
		 */
		public String getQuestion() {
			return question;
		}

		/**
		 * @return la risposta corretta
		 */
		public String getCorrect_answer() {
			return correct_answer;
		}

		/**
		 * @param type il tipo di domanda da impostare
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @param difficulty la difficoltà da impostare
		 */
		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}

		/**
		 * @param category la categoria da impostare
		 */
		public void setCategory(String category) {
			this.category = category;
		}

		/**
		 * @param question la domanda da impostare
		 */
		public void setQuestion(String question) {
			this.question = question;
		}

		/**
		 * @param correct_answer la risposta corretta da impostare
		 */
		public void setCorrect_answer(String correct_answer) {
			this.correct_answer = correct_answer;
		}
	}

	/**
	 * Effettua una chiamata API a OpenTDB per ottenere una domanda quiz.
	 * @return l'oggetto ResponseAPI contenente la risposta dell'API
	 */
	private static ResponseAPI getResponseAPI() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://opentdb.com/api.php");
		Response resp = target.queryParam("amount", 1)
				.queryParam("category", 18)
				.queryParam("type", "multiple")
				.request(MediaType.APPLICATION_JSON).get();
		String jsonString = resp.readEntity(String.class);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, ResponseAPI.class);
	}

	/**
	 * Ottiene una domanda quiz e la formatta nel dialogo del gioco.
	 * @param dialogo l'oggetto Dialogo da popolare con la domanda quiz
	 * @return l'oggetto Dialogo aggiornato con la domanda quiz
	 */
	public static Dialogo getQuiz(Dialogo dialogo) {
		ResponseAPI r;
		int maxRequest = 5;
		int totalRequest = 0;

		do {
			r = getResponseAPI();
			if (r.getResponse_code() == 0) {
				/*
				 * Formatter
				 * &quot; -> "
				 * &#039; -> '
				 */
				String s = "\"Sono il guardiano del tempio, per proseguire oltre devi rispondere ad una mia domanda fatta in una lingua sconosciuta, se riuscirai a rispondere ti darò l'accesso al tempio.\",\"Complimenti la tua risposta è corretta, adesso potrai accedere al tempio.\""
						+ "\n"
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
}
