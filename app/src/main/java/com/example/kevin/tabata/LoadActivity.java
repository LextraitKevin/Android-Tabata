package com.example.kevin.tabata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.tabata.data.Seance;
import com.orm.SugarRecord;

import java.util.List;

public class LoadActivity extends AppCompatActivity {

    public static final String LOAD_ACTIVITY_IDENTIFIANT = "Identifiant";

    LinearLayout scrollBarre;
    LinearLayout footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        List<Seance> mySc = SugarRecord.listAll(Seance.class);


        final int N = mySc.size(); // total number of textviews to add

        final TextView[] myTextViews = new TextView[N]; // create an empty array;
        scrollBarre = (LinearLayout) findViewById(R.id.LinearScroll);
        footer =(LinearLayout) findViewById(R.id.footer);


        final RadioGroup rg= new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);

        //Boucle d'affichage des séances
        for (int i = 0; i < N; i++) {

            Seance sc = mySc.get(i);
            // create a new textview
            final TextView rowTextView = new TextView(this);
            final RadioButton rbutton = new RadioButton(this);

            // set some properties of rowTextView or something
            rbutton.setId(sc.getIdSeance());
            rbutton.setText(sc.getName());

            // add the textview to the linearlayout
            scrollBarre.addView(rowTextView);
            rg.addView(rbutton);

        }

        scrollBarre.addView(rg);

        Button valid= new Button(this);
        valid.setTag("valid_btn");
        valid.setText("Charger");
        Button retour = new Button(this);
        retour.setTag("return_btn");
        retour.setText("Retour");

        footer.addView(retour);
        footer.addView(valid);

        //Listner du bouton valider qui envoie l'id en extra
        valid.findViewWithTag(valid.getTag()).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Log.d("ID",(Integer.toString(rg.getCheckedRadioButtonId())));

                intent.putExtra(LOAD_ACTIVITY_IDENTIFIANT, rg.getCheckedRadioButtonId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                LoadActivity.super.finish();

            }
        });

        //Listner du bouton retour
        retour.findViewWithTag(retour.getTag()).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadActivity.super.finish();

            }
        });


    }




}
