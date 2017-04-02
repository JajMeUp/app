package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

/**
 * Created by Valentin on 29/08/2016.
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
    System.out.println("On ecrit");
        return false;
    }

}
