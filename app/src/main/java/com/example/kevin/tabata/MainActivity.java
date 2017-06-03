package com.example.kevin.tabata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kevin.tabata.data.Seance;
import com.example.kevin.tabata.data.classSeance;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String REST= "rest";
    public final static String WORK= "work";
    public final static String FINISH= "finish";
    public final static String PREPARE= "prepare";
    public final static  int PREPARESONG=R.raw.bip;
    public final static  int BRAVOSONG=R.raw.bravo;
    public final static  int WORKSONG=R.raw.work;
    public final static  int RESTSONG=R.raw.rest;

    static final String STATE_NAME = "NomActivité";
    static final String STATE_PREPA = "IntPrepa";
    static final String STATE_EFFORT = "IntEffort";
    static final String STATE_RECUP = "IntRecup";
    static final String STATE_REP = "IntRep";
    static final String STATE_SERIE = "IntSerie";
    static  final  String STATE_TIME="IntTemps";
    static final String STATE_CHRONO="EtatChrono";
    static final String STATE="Etat";

    TextView nomSeance;
    Button startButton;
    Button pauseButton;
    TextView timerValue;
    TextView state;
    TextView cycle;
    TextView repet;
    RelativeLayout l;

    public final static int LOAD_ACTIVITY_REQUEST = 3;

    private int totTimer;

    private classSeance training;

    private CountDownTimer timer;
    private long updatedTime;
    private int ID;
    private int songID;
    private int LancerTimer=0;
    private String etat="";

    public MediaPlayer mPlayer = null;

    public Context c;
    public final static Animation anim = new AlphaAnimation(0.0f,1.0f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c= this.getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nomSeance = (TextView) findViewById(R.id.nomSeance);
        timerValue = (TextView) findViewById(R.id.chrono);
        cycle = (TextView) findViewById(R.id.cycle);
        repet=(TextView) findViewById(R.id.repetitions);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        state=(TextView) findViewById(R.id.state);
        l=(RelativeLayout) findViewById(R.id.content_main);

        anim.setDuration(200);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        if(etat==null || etat=="") {
            songID=PREPARESONG;
            mPlayer=MediaPlayer.create(this,songID);
            mPlayer.setLooping(true);
        }


        ID = getIntent().getIntExtra(LoadActivity.LOAD_ACTIVITY_IDENTIFIANT,-1);

        //Si on a chargé une activité alors ID !=- et on lance la méthode loadData()
        if(ID!=-1){
            loadData(ID);
            miseAJour();
        }


    }

    //Pour lancer le timer
    public void startTimer(View view) {

        if(ID!=-1) {
            cdTimer(updatedTime);
            mPlayer.start();
            mPlayer.setLooping(true);
            LancerTimer = 1;
        }



    }

    //Foncionnement du timer
    public void cdTimer(long time){

        timer = new CountDownTimer(time, 10) {

            public void onTick(long millisUntilFinished) {
                updatedTime = millisUntilFinished;
                miseAJour();

            }

            public void onFinish() {
                updatedTime = 0;
                training.ChangeState(l);
                miseAJour();

                if(training.getEtat()!=FINISH) {
                    if(training.getEtat()==WORK){
                        updatedTime=training.getWorkSeconde();
                        songID=WORKSONG;
                        playSound(songID);

                    }else{
                        updatedTime=training.getRestSeconde();
                        songID=RESTSONG;
                        playSound(songID);
                    }
                    cdTimer(updatedTime*1000);
                }else{
                    songID=BRAVOSONG;
                    playSound(songID);
                    l.clearAnimation();
                    miseAJour();
                }

            }
        }.start();

    }


    //Mettre en pause le timer
    public void pauseTimer(View view) {

        if(ID!=-1) {
            if (timer != null) {
                l.clearAnimation();
                timer.cancel();
            }

            int currentSong = songID;
            mPlayer.stop();
            LancerTimer = 0;
            mPlayer.reset();
            mPlayer = MediaPlayer.create(this, currentSong);
        }

    }


    // Mise à jour graphique
    private void miseAJour() {

        // Décompositions en secondes et minutes
        int secs = (int) (updatedTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updatedTime % 1000);

        // Affichage du "timer"
        timerValue.setText("" + mins + ":"
                + String.format("%02d", secs) + ":"
                + String.format("%03d", milliseconds));

        cycle.setText("Cycles : " + Integer.toString(training.getNbCycles()));

        repet.setText("Répétitions : "+ Integer.toString(training.getNbRepetition()));
        state.setText(training.getEtat());


    }

    //Remise à zéro du timer et avec les valeurs de départ
    public void Reset(View v){
        if(ID!=-1) {
            loadData(ID);
            miseAJour();
            mPlayer.stop();
            mPlayer.reset();
            mPlayer = MediaPlayer.create(this, PREPARESONG);
        }
    }

    //Fonction pour réchercher les données d'une activité
    public void loadData(int id){

        List<Seance> mySeance= Seance.findWithQuery(Seance.class,"SELECT * FROM Seance WHERE id = ? ",Integer.toString(id));
        Seance laSeance= mySeance.get(0);
        training = new classSeance(laSeance.getName(),laSeance.getPrepareSeconde(),laSeance.getWorkSeconde(),laSeance.getRestSeconde(),laSeance.getNbCycles(),laSeance.getNbRepetition(),null);
        nomSeance.setText("Nom de ma séance : " + training.getName());
        totTimer=laSeance.getPrepareSeconde();
        updatedTime= totTimer*1000;
        l.setBackgroundColor(Color.WHITE);

    }


    public void load(MenuItem item){

        // Création d'une intention
        Intent intent = new Intent(this, LoadActivity.class);

        // Lancement de la demande de changement d'activité
        startActivity(intent);
    }

    public void AddSeance(View v){
        Intent intent = new Intent(this,SeancesActivity.class);
        startActivityForResult(intent,LOAD_ACTIVITY_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("code retour", Integer.toString(resultCode));

        if(requestCode==LOAD_ACTIVITY_REQUEST){

            Log.d("code retour", Integer.toString(resultCode));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.load_activity) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Fonction pour jouer un son (et l'arreter si un est en cours)
    public void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    //Restauration des données si l'on a tourné l'écran
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        String name = savedInstanceState.getString(STATE_NAME);
        int effort = savedInstanceState.getInt(STATE_EFFORT);
        int prepa = savedInstanceState.getInt(STATE_PREPA);
        int recup = savedInstanceState.getInt(STATE_RECUP);
        int serie = savedInstanceState.getInt(STATE_SERIE);
        int rep = savedInstanceState.getInt(STATE_REP);
        updatedTime =savedInstanceState.getLong(STATE_TIME);
        int startChrono = savedInstanceState.getInt(STATE_CHRONO);
        etat = savedInstanceState.getString(STATE);

        training = new classSeance(name,prepa,effort,recup,serie,rep,etat);
        nomSeance.setText("Nom de ma séance : " + training.getName());

        if(startChrono==1){
            startTimer(findViewById(R.id.content_main));

        }else{
            pauseTimer(findViewById(R.id.content_main));

        }

        switch (etat){

            case WORK:
                playSound(WORKSONG);
                l.setBackgroundColor(Color.RED);
                l.startAnimation(anim);
                break;

            case REST:
               playSound(RESTSONG);
                l.setBackgroundColor(Color.GREEN);
                l.clearAnimation();
                break;

            case PREPARE:
                playSound(PREPARESONG);
                break;

            case FINISH:
                l.setBackgroundColor(Color.BLUE);
                l.clearAnimation();
                break;

        }

        miseAJour();
    }

    @Override
    //Sauvegarde des données lorsqu'on tourne l'écran
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state

        if(training!=null) {
            savedInstanceState.putString(STATE_NAME, training.getName());
            savedInstanceState.putInt(STATE_EFFORT, training.getWorkSeconde());
            savedInstanceState.putInt(STATE_PREPA, training.getPrepareSeconde());
            savedInstanceState.putInt(STATE_RECUP, training.getRestSeconde());
            savedInstanceState.putInt(STATE_REP, training.getNbRepetition());
            savedInstanceState.putInt(STATE_SERIE, training.getNbCycles());
            savedInstanceState.putLong(STATE_TIME, updatedTime);
            savedInstanceState.putInt(STATE_CHRONO,LancerTimer);
            savedInstanceState.putString(STATE,training.getEtat());

        }

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }

}
