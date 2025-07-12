package componentiaggiuntivi;
import java.io.Serializable;


public class Chrono implements Serializable {
    private Long startTime;
    private Long endTime;
    private Long elapsedTime;
    private boolean isRunning;

    
    public Chrono() {
        reset();
    }

    
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    public void addMinute(int tempo){
        elapsedTime += Math.abs(tempo) * 60 * 1000;
    }

    
    public void startAgain(long elapsedTime) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            this.elapsedTime = elapsedTime;
            isRunning = true;
        }
    }

    
    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            elapsedTime = elapsedTime + endTime - startTime;
            isRunning = false;
        }
    }

   
    public void reset() {
        startTime = 0L;
        endTime = 0L;
        elapsedTime = 0L;
        isRunning = false;
    }

  
    public long getElapsedTime() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime + elapsedTime;
        }
        return elapsedTime;
    }

    
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

   

    public boolean isRunning() {
        return isRunning;
    }

 
    public long getStartTime() {
        return startTime;
    }

    
    public String getTimeFormatted() {
        try {
            Thread.sleep(1000);
            long time = getElapsedTime();
            long second = (time / 1000) % 60;
            long minute = (time / (1000 * 60)) % 60;
            long hour = (time / (1000 * 60 * 60)) % 24;
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } catch (InterruptedException e) {
            return "00:00:00";
        }
    }
}
