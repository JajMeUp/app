package com.wakemeup.ektoplasma.valou.wakemeup.activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterDemandeAmi;
import com.wakemeup.ektoplasma.valou.wakemeup.R;

import java.util.ArrayList;

/**
 * Created by Valentin on 30/08/2016.
 */
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
