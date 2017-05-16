package com.wakemeup.ektoplasma.valou.wakemeup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.CustomAdapterHistory;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Valentin on 23/09/2016.
 */

public class HistoryActivity extends Fragment {

    private View view;
    private ArrayList<String> list  = new ArrayList<>();
    private ArrayList<String> listVoters;
    private ArrayList<String> listLinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listhistoryview = (ListView) view.findViewById(R.id.ListHistory);

        ArrayList<String> historylist = new ArrayList<String>();
        //list = new ArrayAdapter<String>(getActivity(), R.layout.history_row, historylist);

        //TODO remplir la list avec historique
        Caller.getBddHistory();

        if(Caller.getHistoryVoter() != null) listVoters = new ArrayList<String>(Caller.getHistoryVoter());
        else listVoters = new ArrayList<String>();

        if(Caller.getHistoryLink() != null) listLinks = new ArrayList<String>(Caller.getHistoryLink());
        else listLinks = new ArrayList<String>();

        Iterator<String> x = listVoters.iterator();
        Iterator<String> y = listLinks.iterator();

        while (x.hasNext() && y.hasNext())
        {
            String voter = (String) x.next();
            String link = (String) y.next();
            Caller.nameYTvideo(link);
            if(Caller.getCurrentVideoName() != null) {
                list.add( voter + " : " + Caller.getCurrentVideoName() );
            }
        }

        if (list != null)
        {
            CustomAdapterHistory adapter = new CustomAdapterHistory(list, getActivity());
            listhistoryview.setAdapter(adapter);
        }

        return view;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Caller.getBddHistory();

        ListView listhistoryview = (ListView) view.findViewById(R.id.ListHistory);

        if(Caller.getHistoryVoter() != null) listVoters = new ArrayList<String>(Caller.getHistoryVoter());
        else listVoters = new ArrayList<String>();

        if(Caller.getHistoryLink() != null) listLinks = new ArrayList<String>(Caller.getHistoryLink());
        else listLinks = new ArrayList<String>();

        Iterator<String> x = listVoters.iterator();
        Iterator<String> y = listLinks.iterator();

        while (x.hasNext() && y.hasNext())
        {
            String voter = (String) x.next();
            String link = (String) y.next();
            Caller.nameYTvideo(link);
            if(Caller.getCurrentVideoName() != null){
                list.add( voter + " : " + Caller.getCurrentVideoName() );
            }

        }

        if (list != null)
        {
            CustomAdapterHistory adapter = new CustomAdapterHistory(list, getActivity());
            listhistoryview.setAdapter(adapter);
        }

        /*list = new ArrayList<String>();
        String idVideo = "Sw9uicEGjGw";
        Caller.nameYTvideo(idVideo);
        list.add("Bonjour");

        CustomAdapterHistory adapter = new CustomAdapterHistory(list, getActivity());

        ListView lView = (ListView)view.findViewById(R.id.ListHistory);
        lView.setAdapter(adapter);*/
    }

}
