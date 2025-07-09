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

public class Musica {

    private Clip musicaGioco;
    private static boolean isPaused=false;

    /**
     * Metodo che avvia la riproduzione della musica all'esecuzione del programma.
     * 
     * @param filePath percorso del file audio da riprodurre
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
            musicaGioco.loop(Clip.LOOP_CONTINUOUSLY); // Imposta il loop continuo
            musicaGioco.start(); 
        } catch (UnsupportedAudioFileException | java.io.IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che gestisce lo stop della riproduzione della musica.
     * Ferma e chiude il clip se è in esecuzione.
     */
    public void stopMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            musicaGioco.stop();
            musicaGioco.close();
        }
    }

    /**
     * Mette in pausa la riproduzione della musica corrente.
     * La posizione viene mantenuta per una successiva ripresa.
     */
    public void pausaMusica() {
        if (musicaGioco != null && musicaGioco.isRunning()) {
            // musicaGioco.setMicrosecondPosition(musicaGioco.getMicrosecondPosition());
            musicaGioco.stop();
            isPaused = true;
        }
    }

    /**
     * Riprende la riproduzione della musica dalla posizione di pausa.
     */
    public void riprendiMusica() {
        musicaGioco.start();
        isPaused = false;
    }

    public boolean isInPaused(){
        return isPaused;
    }

    /**
     * Verifica se la musica è attualmente in riproduzione.
     * 
     * @return true se la musica è in riproduzione, false altrimenti
     */
    public boolean isPlaying() {
        return musicaGioco.isRunning();
    }

    /**
     * Metodo che gestisce il volume della musica.
     * 
     * @param volume livello del volume da impostare in decibel
     */
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * Restituisce il volume corrente della musica.
     * 
     * @return il livello corrente del volume in decibel
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) musicaGioco.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    /**
     * Riproduce un effetto sonoro una sola volta.
     * 
     * @param filePath percorso del file audio da riprodurre
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