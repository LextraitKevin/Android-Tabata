package com.example.kevin.tabata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kevin.tabata.data.Seance;

import java.util.List;

public class SeancesActivity extends AppCompatActivity {

    private int prepa;
    private int effort;
    private int recup;
    private int rep;
    private int serie;
    private String name;

    private TextView nameSeanceView;
    private TextView prepaView;
    private TextView effortView;
    private TextView recupView;
    private TextView repView;
    private TextView serieView;

    static final String STATE_NAME = "NomActivité";
    static final String STATE_PREPA = "IntPrepa";
    static final String STATE_EFFORT = "IntEffort";
    static final String STATE_RECUP = "IntRecup";
    static final String STATE_REP = "IntRep";
    static final String STATE_SERIE = "IntSerie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);


        //Initialisation des données
        prepa=0;
        effort=0;
        recup=0;
        rep=0;
        serie=0;
        name="Nouvelle activité";


        nameSeanceView = (EditText) findViewById(R.id.nameSeance);
        prepaView =(TextView) findViewById(R.id.Timer_prepa);
        effortView =(TextView) findViewById(R.id.Timer_effort);
        recupView =(TextView) findViewById(R.id.Timer_recup);
        repView = (TextView) findViewById(R.id.Timer_rep);
        serieView = (TextView) findViewById(R.id.Timer_serie);


        MaJ();


    }

    //insertion d'une séance dans la base de données
    public void creerSeance(View view){

        String name = nameSeanceView.getText().toString();

        Seance newSeance = new Seance(name,prepa,effort,recup,serie,rep);

        newSeance.save();

        super.finish();

    }

    private String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    //Mise à jour graphique
    private void MaJ(){
        prepaView.setText(secondsToString(prepa));
        effortView.setText(secondsToString(effort));
        recupView.setText(secondsToString(recup));
        repView.setText(Integer.toString(rep));
        serieView.setText(Integer.toString(serie));
        nameSeanceView.setText(name);

    }

    public void changePrepa(View view){

        // Modification de la DATA
        if (view.getId() == R.id.lessPrepa && prepa >=5) {
            prepa-=5;

        } else if (view.getId() == R.id.addPrepa) {
            prepa+=5;
        }


        MaJ();
    }


    public void changeEffort(View view){

        // Modification de la DATA
        if (view.getId() == R.id.lessEffort && effort>=5) {
            effort-=5;

        } else if (view.getId() == R.id.addEffort) {
            effort+=5;
        }

        MaJ();

    }


    public void changeRecup(View view){
        // Modification de la DATA
        if (view.getId() == R.id.lessRecup && recup >=5) {
            recup-=5;

        } else if (view.getId() == R.id.addRecup) {
            recup+=5;
        }

        MaJ();

    }

    public void changeRep(View view){

        // Modification de la DATA
        if (view.getId() == R.id.lessRep && rep >=1) {
            rep--;

        } else if (view.getId() == R.id.addRep) {
            rep++;
        }

        MaJ();

    }

    public void changeSerie(View view){

        // Modification de la DATA
        if (view.getId() == R.id.lessSerie && serie >=1) {
            serie--;

        } else if (view.getId() == R.id.addSerie) {
            serie++;
        }

        MaJ();

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        name = savedInstanceState.getString(STATE_NAME);
        effort = savedInstanceState.getInt(STATE_EFFORT);
        prepa = savedInstanceState.getInt(STATE_PREPA);
        recup = savedInstanceState.getInt(STATE_RECUP);
        serie = savedInstanceState.getInt(STATE_SERIE);
        rep = savedInstanceState.getInt(STATE_REP);

        MaJ();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_NAME, name);
        savedInstanceState.putInt(STATE_EFFORT, effort);
        savedInstanceState.putInt(STATE_PREPA, prepa);
        savedInstanceState.putInt(STATE_RECUP, recup);
        savedInstanceState.putInt(STATE_REP, rep);
        savedInstanceState.putInt(STATE_SERIE, serie);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }








}
