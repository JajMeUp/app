package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.adaptaters.PageAdapter;
import com.wakemeup.ektoplasma.valou.wakemeup.drawables.NotificationDrawable;
import com.wakemeup.ektoplasma.valou.wakemeup.fragments.ClockActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.fragments.DialogFragmentMessageReveil;
import com.wakemeup.ektoplasma.valou.wakemeup.receivers.AlarmReceiver;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int ACCESS_REQUEST_CODE = 101;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static Menu menu;
    private static MainActivity inst;
    public ClockActivity ClockObject = new ClockActivity();
    protected Context ctx = this;
    TabLayout tabLayout;
    TabLayout.Tab tabreveil, tablist, tabhistory;
    private boolean waitingMsg = false;

    private static final int NETWORK_ERROR = 1;
    private static final int YOUTUBE_ERROR = 2;

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Caller.getNotif();
        }
    };

    Timer timer = new Timer();

    public static MainActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (mIntent == null) {
            displayAlert(YOUTUBE_ERROR);
            Intent intent = new Intent("youtube.error.message",null);
            Caller.getCtx().sendBroadcast(intent);
        }

        Caller.storePersistantCookieString();
        if(Caller.getCookieInstance() == null)
        {
            Intent signIntent = new Intent(ctx, SignActivity.class);
            ctx.startActivity(signIntent);
        }
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("ACCESS PERMISSION", "Permission to access denied");
            makeRequest();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("ekto.valou.badgebroadcast");
        IntentFilter ytfilter = new IntentFilter();
        ytfilter.addAction("ekto.valou.ytbroadcast");
        IntentFilter volleyerror = new IntentFilter();
        volleyerror.addAction("volley.error.message");
        registerReceiver(receiver, filter);
        registerReceiver(ytreceiver, ytfilter);
        registerReceiver(volleyerrorreceiver, volleyerror);

        setContentView(R.layout.activity_main);
        Caller.setCtx(ctx.getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timer.schedule(task, 4000, 20000);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabreveil = tabLayout.newTab().setText("Mon réveil");
        tablist = tabLayout.newTab().setText("Liste");
        tabhistory = tabLayout.newTab().setText("Historique");
        tabLayout.addTab(tabhistory);
        tabLayout.addTab(tabreveil);
        tabLayout.addTab(tablist);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);//Choix du tab que l'on veut voir au lancement de l'app
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /*****************************************
         * Gestion alarm
         ****************************************/

       // setContentView(R.layout.fragment_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (sharedText != null) {
            String currentLink = sharedText.split(".be/")[1];
            assert(currentLink != null);
            Caller.setCurrentLink(currentLink);

            viewPager.setCurrentItem(1);
        }



        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                ACCESS_REQUEST_CODE);
    }

    @Override
    public void onResume(){
        super.onResume();

        String valueAutorisation = PreferenceManager.getDefaultSharedPreferences(this).getString("prefWhoWakeMe", null);
        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);


        if(valueAutorisation != null)
        {
            if(valueAutorisation.equals("Seulement moi") && tablist.getText() != null)
            {
                tabLayout.removeTab(tablist);
            }
            else if(tablist.getText() == null)
            {
                tablist = tabLayout.newTab().setText("Liste");
                tabLayout.addTab(tablist, 2, false);
            }

            if(!myPreference.getBoolean("prefHistory", false))
            {
                tabLayout.removeTab(tabhistory);
            }
            else if(tabhistory.getText() == null)
            {
                tabhistory = tabLayout.newTab().setText("Historique");
                tabLayout.addTab(tabhistory, 0, false);
            }
        }

        if(waitingMsg == true)
        {
            DialogFragmentMessageReveil dialog = DialogFragmentMessageReveil.newInstance();
            dialog.show(getSupportFragmentManager(),"fragmentDialog");
            waitingMsg = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(ytreceiver);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Caller.nameYTvideo("5Fp1viiRJnw");
            Calendar calendar = Calendar.getInstance();
            ClockObject.setAlarmText("YOLOPPOELDORK");
            Log.d("MyActivity", "Alarm heure : "+ClockObject.GetHours()+":"+ClockObject.GetMinutes());
            calendar.set(Calendar.HOUR_OF_DAY, ClockObject.GetHours());
            calendar.set(Calendar.MINUTE, ClockObject.GetMinutes());
            Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            ClockObject.setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MainActivity.menu = menu;

        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*SearchActivity search = new SearchActivity();
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(search);*/

        setBadgeCount(this, "0", "friend");

        setBadgeCount(this, "0", "message");


        return true;
    }

    public static void setBadgeCount(Context context, String count, String badge_a_modifier) {//Change badge count

        NotificationDrawable badge;
        Drawable reuse = null;
        MenuItem item;
        LayerDrawable icon = null;

        if(badge_a_modifier.equals("friend"))
        {
            item = menu.findItem(R.id.action_friends);
            icon = (LayerDrawable) item.getIcon();
            reuse = icon.findDrawableByLayerId(R.id.ic_badge_friend);
        }
        else if (badge_a_modifier.equals("message"))
        {
            item = menu.findItem(R.id.action_message);
            icon = (LayerDrawable) item.getIcon();
            reuse = icon.findDrawableByLayerId(R.id.ic_badge_message);
        }

        if (reuse != null && reuse instanceof NotificationDrawable) {
            badge = (NotificationDrawable) reuse;
        } else {
            badge = new NotificationDrawable(context);
        }

        badge.setCount(count);
        assert icon != null;
        icon.mutate();

        if(badge_a_modifier.equals("friend"))
        {
            icon.setDrawableByLayerId(R.id.ic_badge_friend, badge);
        }
        else if (badge_a_modifier.equals("message"))
        {
            icon.setDrawableByLayerId(R.id.ic_badge_message, badge);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        switch (id){
            case R.id.action_parametre:
                i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, 1);
                return true;
            case R.id.action_friends:
                i = new Intent(this, DemandeAmiActivity.class);
                startActivity(i);
                return true;
            case R.id.action_message:
                i = new Intent(this, MessageActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extra = intent.getExtras();
            if(extra != null)
            {
                String count = extra.getString("COUNT");
                String type = extra.getString("TYPE");
                setBadgeCount(ctx, count, type);
            }
        }
    };

    private BroadcastReceiver ytreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MainActivity", "Broadcast recu YT");
            //TODO afficher le message
            if(!Caller.getCurrentMessage().equals("")) waitingMsg = true;

        }
    };
    private BroadcastReceiver volleyerrorreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            displayAlert(NETWORK_ERROR);
        }
    };
    private void displayAlert(int errorID)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(errorID == NETWORK_ERROR)
        {
            builder.setMessage("Erreur réseau detectée.").setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }
        if(errorID == YOUTUBE_ERROR)
        {
            builder.setMessage("Erreur : L'application Youtube n'est pas installée.").setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }

        AlertDialog alert = builder.create();
        alert.show();
    }
}
