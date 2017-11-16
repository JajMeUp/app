package com.teamjaj.jajmeup.activities;

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

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.adaptaters.PageAdapter;
import com.teamjaj.jajmeup.drawables.NotificationDrawable;
import com.teamjaj.jajmeup.fragments.ClockActivity;
import com.teamjaj.jajmeup.fragments.DialogFragmentMessageReveil;
import com.teamjaj.jajmeup.receivers.AlarmReceiver;
import com.teamjaj.jajmeup.services.FriendshipService;
import com.teamjaj.jajmeup.services.tasks.DataFetchingTask;
import com.teamjaj.jajmeup.utilities.Caller;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private static final int ACCESS_REQUEST_CODE = 101;

    private AlarmManager alarmManager;
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

    public static MainActivity instance() {
        return inst;
    }

    private FriendshipService friendshipService;
    private TimerTask task;
    private Timer timer;

    private String SharedLink;

    public void setSharedLink(String url){
        this.SharedLink = url;
    }
    public String getSharedLink(){
        return SharedLink;
    }
    public AlarmManager getAlarmManager(){
        return alarmManager;
    }
    public void setAlarmManager(AlarmManager al){
        this.alarmManager = al;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendshipService = new FriendshipService();
        task = new DataFetchingTask(getApplicationContext());
        timer = new Timer();
        timer.schedule(task, 4000, 20000);

        Intent mIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (mIntent == null) {
            displayAlert(YOUTUBE_ERROR);
            Intent intent = new Intent("youtube.error.message", null);
            sendBroadcast(intent);
        }

        if (!Caller.isTokenPresent(this)) {
            Intent signIntent = new Intent(ctx, SignActivity.class);
            ctx.startActivity(signIntent);
        }

        // TODO: Do we still really need this ?
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("ACCESS PERMISSION", "Permission to access denied");
            makeRequest();
        }

        registerReceiver(
                pendingFriendshipReceiver,
                new IntentFilter(FriendshipService.BROADCAST_REFRESH_PENDING_FRIENDSHIP_REQUEST)
        );
        IntentFilter ytfilter = new IntentFilter();
        ytfilter.addAction("jajmeup.messagereveil");
        IntentFilter volleyerror = new IntentFilter();
        volleyerror.addAction("volley.error.message");
        registerReceiver(ytreceiver, ytfilter);
        registerReceiver(volleyerrorreceiver, volleyerror);

        setContentView(R.layout.activity_main);
        Caller.setCtx(ctx.getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        setAlarmManager((AlarmManager) getSystemService(ALARM_SERVICE));

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                ACCESS_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();

        String valueAutorisation = PreferenceManager.getDefaultSharedPreferences(this).getString("prefWhoWakeMe", null);
        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);

        if (valueAutorisation != null) {
            if (valueAutorisation.equals("Seulement moi") && tablist.getText() != null) {
                tabLayout.removeTab(tablist);
            } else if (tablist.getText() == null) {
                tablist = tabLayout.newTab().setText("Liste");
                tabLayout.addTab(tablist, 2, false);
            }

          /*  if(!myPreference.getBoolean("prefHistory", false))
            {
                tabLayout.removeTab(tabhistory);
            }
            else if(tabhistory.getText() == null)
            {
                tabhistory = tabLayout.newTab().setText("Historique");
                tabLayout.addTab(tabhistory, 0, false);
            }*/
        }

        if (waitingMsg == true) {
            DialogFragmentMessageReveil dialog = DialogFragmentMessageReveil.newInstance();
            dialog.show(getSupportFragmentManager(), "fragmentDialog");
            waitingMsg = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pendingFriendshipReceiver);
        unregisterReceiver(ytreceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("MainActivity", "onNewIntent()...");
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                setSharedLink(sharedText);
                viewPager.setCurrentItem(1);
                Log.d("MainActivity", sharedText);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            Log.d("MyActivity", "Alarm heure : " + ClockObject.GetHours() + ":" + ClockObject.GetMinutes());
            calendar.set(Calendar.HOUR_OF_DAY, ClockObject.GetHours());
            calendar.set(Calendar.MINUTE, ClockObject.GetMinutes());
            Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
            getAlarmManager().set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            getAlarmManager().cancel(pendingIntent);
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

        setBadgeCount(this, 0, "FRIEND");
        setBadgeCount(this, 0, "MESSAGE");


        return true;
    }

    public void setBadgeCount(Context context, int count, String badgeName) {
        NotificationDrawable badge;
        Drawable reuse;
        MenuItem item;
        LayerDrawable icon;
        int itemId;
        int badgeId;

        switch (badgeName) {
            case "FRIEND":
                itemId = R.id.action_friends;
                badgeId = R.id.ic_badge_friend;
                break;
            case "MESSAGE":
                itemId = R.id.action_message;
                badgeId = R.id.ic_badge_message;
                break;
            default:
                return;
        }

        item = menu.findItem(itemId);
        icon = (LayerDrawable) item.getIcon();
        reuse = icon.findDrawableByLayerId(badgeId);

        if (reuse != null && reuse instanceof NotificationDrawable) {
            badge = (NotificationDrawable) reuse;
        } else {
            badge = new NotificationDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(badgeId, badge);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        switch (id) {
            case R.id.action_parametre:
                i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, 1);
                return true;
            case R.id.action_friends:
                i = new Intent(this, FriendshipActivity.class);
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

    private BroadcastReceiver pendingFriendshipReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int pendingFriendshipCount = friendshipService.getPendingFriendships().size();
            setBadgeCount(context, pendingFriendshipCount, "FRIEND");
        }
    };

    private BroadcastReceiver ytreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MainActivity", "Broadcast recu YT");
            ToggleButton tog = (ToggleButton)findViewById(R.id.SetAlarmButton);
            tog.setChecked(false);
        }
    };
    private BroadcastReceiver volleyerrorreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            displayAlert(NETWORK_ERROR);
        }
    };

    private void displayAlert(int errorID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (errorID == NETWORK_ERROR) {
            builder.setMessage("Erreur réseau detectée.").setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }
        if (errorID == YOUTUBE_ERROR) {
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
