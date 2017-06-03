package com.example.kevin.tabata.data;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.kevin.tabata.MainActivity;

import static com.example.kevin.tabata.MainActivity.PREPARE;
import static com.example.kevin.tabata.MainActivity.WORK;
import static com.example.kevin.tabata.MainActivity.FINISH;
import static com.example.kevin.tabata.MainActivity.REST;
import static com.example.kevin.tabata.MainActivity.WORKSONG;
import static com.example.kevin.tabata.MainActivity.anim;

/**
 * Created by kevin on 07/11/2016.
 */

public class classSeance {

    private String name;
    private int prepareSeconde;
    private int workSeconde;
    private int restSeconde;
    private int nbCycles;
    private int nbRepetition;
    private String etatTravail;
    private int nbRepCycle;

    public classSeance(String n, int ps, int ws,int rs, int nbC, int nbR,String etat){
        name=n;
        prepareSeconde=ps;
        workSeconde=ws;
        restSeconde=rs;
        nbCycles=nbC;
        nbRepetition=nbR;
        nbRepCycle=nbR;
        etatTravail=etat;

        if(etatTravail==null) {
            if (ps > 0) {
                etatTravail = PREPARE;
            } else {
                etatTravail = WORK;
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepareSeconde() {
        return prepareSeconde;
    }

    public void setPrepareSeconde(int prepareSeconde) {
        this.prepareSeconde = prepareSeconde;
    }

    public int getWorkSeconde() {
        return workSeconde;
    }

    public void setWorkSeconde(int workSeconde) {
        this.workSeconde = workSeconde;
    }

    public int getRestSeconde() {
        return restSeconde;
    }

    public void setRestSeconde(int restSeconde) {
        this.restSeconde = restSeconde;
    }

    public int getNbCycles() {
        return nbCycles;
    }

    public void setNbCycles(int nbCycles) {
        this.nbCycles = nbCycles;
    }

    public int getNbRepetition() {
        return nbRepetition;
    }

    public void setNbRepetition(int nbRepetition) {
        this.nbRepetition = nbRepetition;
    }

    public String getEtat() {
        return etatTravail;
    }

    public void setEtat(String etat) {
        this.etatTravail = etat;
    }

    //Fonction pour modifier l'état
    public void ChangeState(RelativeLayout l){


        if(etatTravail==PREPARE){
            etatTravail=WORK;
            l.setBackgroundColor(Color.RED);
            l.startAnimation(anim);
        }else if(etatTravail==WORK){
            etatTravail=REST;
            l.setBackgroundColor(Color.GREEN);
            l.clearAnimation();
        }else if (etatTravail==REST && nbCycles>0 && nbRepetition>1){
            nbRepetition--;
            etatTravail=WORK;
            l.setBackgroundColor(Color.RED);
            l.startAnimation(anim);

        }
        else if(etatTravail==REST && nbRepetition==1 && nbCycles>0){
            nbCycles--;
            nbRepetition=nbRepCycle;
            etatTravail=WORK;
            l.setBackgroundColor(Color.RED);
            l.startAnimation(anim);
        }
        else {
            etatTravail=FINISH;
            l.setBackgroundColor(Color.BLUE);
            l.clearAnimation();
        }

    }
}
