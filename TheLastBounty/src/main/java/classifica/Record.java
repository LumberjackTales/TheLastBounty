package classifica;

import java.io.Serializable;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class Record implements Serializable{
    
    private final String nome;
    private final long timeTaken;
    private final String data;

    /**
     *
     * @param nome
     * @param timeTaken
     * @param data
     */
    public Record(String nome, long timeTaken, String data) {
        this.nome = nome;
        this.timeTaken = timeTaken;
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @return
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @return
     */
    public String timeFormatted() {
        long second = (timeTaken / 1000) % 60;
        long minute = (timeTaken / (1000 * 60)) % 60;
        long hour = (timeTaken / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Record{" +
                "nome ='" + nome + '\'' +
                ", tempo =" + timeFormatted() +
                ", data = " + data +
                '}';
    }
}
