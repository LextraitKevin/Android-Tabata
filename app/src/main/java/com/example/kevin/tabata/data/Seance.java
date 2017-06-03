package com.example.kevin.tabata.data;

import com.orm.SugarRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by kevin on 07/10/2016.
 */

public class Seance extends SugarRecord {

    private int idSeance;
    private String name="";
    private int prepareSeconde;
    private int workSeconde;
    private int restSeconde;
    private int nbCycles;
    private int nbRepetition;


    //Constructeur par défault
    public Seance(){

    }



    public  Seance(String name,int pS, int wS,int rS,int nbC,int nbRep  ){



        this.setIdSeance(checkId());
        this.setName(name);
        this.setPrepareSeconde(pS);
        this.setWorkSeconde(wS);
        this.setRestSeconde(rS);
        this.setNbCycles(nbC);
        this.setNbRepetition(nbRep);
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

    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public int checkId(){
        int id=0;
        List<Seance> mySc = SugarRecord.listAll(Seance.class);

        if(mySc.size()==0){
            id=0;
        }
        else{
            Seance s1 = mySc.get(mySc.size()-1);
            id = s1.getIdSeance();

        }

        return id+1;

    }
}
