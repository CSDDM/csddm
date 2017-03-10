package com.example.csddm.menu;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csddm.ListeningMusicActivity;
import com.example.csddm.LoginActivity;
import com.example.csddm.R;
import com.example.csddm.RecordActivity;
import com.example.csddm.ToDrawActivity;
import com.example.csddm.drawface.DrawActivity;
import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.entity.ListenRecord;
import com.example.csddm.entity.User;
import com.example.csddm.operatedb.QueryData;
import com.example.csddm.operatedb.SQLiteHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG_USERACCOUNT ="user_account";
    public static final String TAG_USERNAME ="user_name";
    public static final String ISTOURSIT ="游客身份";
    public static MediaPlayer mp3;
    public static Timer mTimer;
    public static TimerTask mTimerTask;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private String username;
    private String useraccount="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        updateUserName();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Darling你好哇~祝美好的一天", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //排行榜
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }
    //获取来自login/register的用户信息,更新界面
    private void updateUserName(){
        Intent intent = getIntent();
        useraccount = intent.getStringExtra(TAG_USERACCOUNT);
        username = intent.getStringExtra(TAG_USERNAME);
        LayoutInflater inflater = getLayoutInflater();
        NavigationView view = (NavigationView)findViewById(R.id.nav_view);
        View headerLayout = inflater.inflate(R.layout.nav_header_menu,view);
        TextView account = (TextView)headerLayout.findViewById(R.id.account_menu);
        account.setText(username);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(new DummyContent().getItems()));
        Log.i("MenuActivity","finish initial recycle view");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_takephoto) {
            // Handle the camera action
        } else if (id == R.id.nav_community) {

        } else if (id == R.id.nav_choosemusic) {
            Intent intent = new Intent(this, ListeningMusicActivity.class);
            intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
            intent.putExtra(MenuActivity.TAG_USERNAME, username);
            startActivity(intent);
        } else if (id == R.id.nav_uploadmusic) {

        } else if (id == R.id.nav_search) {

        }else if (id == R.id.nav_draw) {
            if(username.equals(MenuActivity.ISTOURSIT)){
                Toast.makeText(this, "客官，您现在还是名游客，得先登陆哟~", Toast.LENGTH_SHORT)
                        .show();
            }else {
                Intent intent = new Intent(this, ToDrawActivity.class);
                intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
                intent.putExtra(MenuActivity.TAG_USERNAME, username);
                startActivity(intent);
            }
        } else if (id == R.id.nav_concert) {

        }else if (id == R.id.nav_showrecord) {
            if(username.equals(MenuActivity.ISTOURSIT)){
                Toast.makeText(this, "客官，您现在还是名游客，得先登陆哟~", Toast.LENGTH_SHORT)
                        .show();
            }else {
                Intent intent = new Intent(MenuActivity.this, RecordActivity.class);
                intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
                intent.putExtra(MenuActivity.TAG_USERNAME, username);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content_menu, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(Integer.parseInt(mValues.get(position).id)+1+"");
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        //替换成听歌页面
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }*/
                    Intent intent = new Intent(MenuActivity.this,ListeningMusicActivity.class);
                    intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, position);
                    intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
                    intent.putExtra(MenuActivity.TAG_USERNAME, username);
                    startActivity(intent);
                    //finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
