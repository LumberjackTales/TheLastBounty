package componentiaggiuntivi;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class Musica {

    private Clip musicaGioco;
    private static boolean isPaused=false;

    /**
     *
     * @param pathAudio
     */
    public void playMusic(String pathAudio) {
        try {
            java.net.URL audioUrl = getClass().getResource(pathAudio);
            if (audioUrl == null) {
                System.err.println("Audio non trovato: " + pathAudio);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioUrl);
            musicaGioco = AudioSystem.getClip();
            musicaGioco.open(audioStream);
            musicaGioco.loop(Clip.LOOP_CONTINUOUSLY); 
            musicaGioco.start(); 
        } catch (UnsupportedAudioFileException | java.io.IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void stopMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            musicaGioco.stop();
            musicaGioco.close();
        }
    }

    /**
     *
     */
    public void pausaMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            musicaGioco.stop();
            isPaused = true;
        }
    }

    /**
     *
     */
    public void riprendiMusica() {
        musicaGioco.start();
        isPaused = false;
    }

    /**
     *
     * @return
     */
    public boolean isInPaused(){
        return isPaused;
    }

    /**
     *
     * @return
     */
    public boolean isPlaying() {
        return musicaGioco.isRunning();
    }

    /**
     *
     * @param volume
     */
    public void setVolume(float volume) {
        volume = (float) Math.floor(volume * 100);
        if (volume % 10 != 0)
            volume = (volume + 10 - volume % 10);
        volume /= 100;
        if (!(volume < 0f || volume > 1f)){
            FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);        
            gainControl.setValue(20f * (float) Math.log10(volume));
            System.out.println(getVolume());
        }
    }

    /**
     *
     * @return
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);        
        float volume = (float) Math.floor((float) Math.pow(10f, gainControl.getValue() / 20f) * 100);
        if (volume % 10 != 0)
            volume = (volume + 10 - volume % 10);
        volume /= 100;
        return volume;
    }

    /**
     *
     * @param filePath
     */
    public void riproduciClip(String filePath) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, e);
        }
    }
 

}