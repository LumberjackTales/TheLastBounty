package classifica;

import java.io.Serializable;

public class Record implements Serializable{
    
    private final String nome;
    private final long timeTaken;
    private final String data;

    
    public Record(String nome, long timeTaken, String data) {
        this.nome = nome;
        this.timeTaken = timeTaken;
        this.data = data;
    }

    
    public String getNome() {
        return nome;
    }

   
    public long getTimeTaken() {
        return timeTaken;
    }

   
    public String getData() {
        return data;
    }

  
    public String timeFormatted() {
        long second = (timeTaken / 1000) % 60;
        long minute = (timeTaken / (1000 * 60)) % 60;
        long hour = (timeTaken / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

   
    @Override
    public String toString() {
        return "Record{" +
                "nome ='" + nome + '\'' +
                ", tempo =" + timeFormatted() +
                ", data = " + data +
                '}';
    }
}
