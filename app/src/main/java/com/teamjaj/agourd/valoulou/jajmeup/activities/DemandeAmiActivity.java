package com.teamjaj.agourd.valoulou.jajmeup.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.CustomAdapterDemandeAmi;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import com.teamjaj.agourd.valoulou.jajmeup.R;

import java.util.ArrayList;

public class DemandeAmiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande_ami);

        ArrayList<String> list;

        if(Caller.getNewAmi() != null)
            list = new ArrayList<String>(Caller.getNewAmi());
        else list = new ArrayList<String>();

        CustomAdapterDemandeAmi adapter = new CustomAdapterDemandeAmi(list, this);

        ListView lView = (ListView)findViewById(R.id.listDemandeAmi);
        lView.setAdapter(adapter);
    }
}
