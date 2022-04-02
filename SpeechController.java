package com.example.projectz;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SpeechController {

    private float pitch;
    private float rate;
    private float volume;

    public SpeechController(){
        pitch = 130;
        rate = 120;
        volume = 1;
    }

    public SpeechController(float voicePitch, float voiceRate, float voiceVolume){
        this.pitch = voicePitch;
        this.rate = voiceRate;
        this.volume = voiceVolume;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRate() {
        return rate;
    }

    public float getVolume() {
        return volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public static void SpeakAloud(String text){

        SpeechController speechController = new SpeechController();

        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            Voice voice;

            voice = VoiceManager.getInstance().getVoice("kevin");

            if (voice != null){
                voice.allocate();
            }

            try {
                voice.setRate(speechController.getRate());
                voice.setPitch(speechController.getPitch());
                voice.setVolume(speechController.getVolume());
                voice.speak(text);
                voice.deallocate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println("An error occurred "+e);
        }
    }
}