package com.example.csddm.menu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.example.csddm.ListeningMusicActivity;
import com.example.csddm.LoginActivity;
import com.example.csddm.R;
import com.example.csddm.ToDrawActivity;
import com.example.csddm.drawface.DrawActivity;
import com.example.csddm.drawface.res.MyResourse;
import com.example.csddm.entity.ListenRecord;
import com.example.csddm.entity.User;
import com.example.csddm.operatedb.QueryData;
import com.example.csddm.operatedb.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG_USERACCOUNT ="user_account";
    public static final String TAG_USENAME ="user_name";

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
        username = intent.getStringExtra(TAG_USENAME);
        LayoutInflater inflater = getLayoutInflater();
        NavigationView view = (NavigationView)findViewById(R.id.nav_view);
        View headerLayout = inflater.inflate(R.layout.nav_header_menu,view);
        TextView account = (TextView)headerLayout.findViewById(R.id.account_menu);
        account.setText(username);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
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
            //this.insertBatchSingerData();
            //this.insertBatchSongData();
            //this.insertBatchListenRecordData();
        } else if (id == R.id.nav_choosemusic) {
            Intent intent = new Intent(this, ListeningMusicActivity.class);
            intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
            intent.putExtra(MenuActivity.TAG_USENAME, username);
            startActivity(intent);
        } else if (id == R.id.nav_uploadmusic) {

        } else if (id == R.id.nav_search) {
            SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
            ArrayList<ListenRecord> listenRecord = QueryData.getListenRecordByAccount(useraccount,dbHelper);
        }else if (id == R.id.nav_draw) {
            Intent intent = new Intent(this, ToDrawActivity.class);
            intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
            intent.putExtra(MenuActivity.TAG_USENAME, username);
            startActivity(intent);
        } else if (id == R.id.nav_concert) {

        }else if (id == R.id.nav_singandlisten) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void insertBatchSingerData(){
        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into singer(singerid,name,gender,birth,nation) values(?,?,?,?,?)";
        SQLiteStatement sstatement = db.compileStatement(sql);
        db.beginTransaction();
        String[] singerid={"sger0001","sger0002","sger0003","sger0004","sger0005","sger0006",
                "sger0007","sger0008","sger0009","sger0010","sger0011",
                "sger0012","sger0013","sger0014","sger0015","sger0016","sger0017",
                "sger0018","sger0019","sger0020"};
        String[] name = {"Eric Clapton","Buddy Guy","Easton Corbin","Eric Paslay","Tujamo","Oliver Heldens",
                "vitas","闫学晶","金铭","包圣美","路德维希·凡·贝多芬","小约翰·施特劳斯",
                "彼得·伊里奇·柴可夫斯基","老约翰·施特劳斯","聂耳","蔡依林","河图","薛之谦","Maroon 5","汪峰"};
        String[] gender = {"男","男","男","男","男","男","男","女","女","女","男","男","男","男","男","女","男","男","男","男"};
        String[] birth = {"1945.3.30","1936.7.30","1982.4.12","1983.1.29","1988.1.18","1995.2.1","1981.2.19","1972.2.7","1980","1958",
                "1770.12.16","1825.10.25","1840.5.7","1804.3.14","1912.2.14","1980.9.15","1984.10.31","1983.7.17","1979.3.18","1971.6.29"};
        String[] nation = {"英国","美国","美国","美国","德国","荷兰","俄罗斯","中国","中国","中国台湾","德国","奥地利","俄罗斯","奥地利","中国","中国","中国","中国","美国","中国"};
        long result = 0;
        for(int i=0;i<19;i++){
            sstatement.bindString(1,singerid[i]);
            sstatement.bindString(2,name[i]);
            sstatement.bindString(3,gender[i]);
            sstatement.bindString(4,birth[i]);
            sstatement.bindString(5,nation[i]);
            if(sstatement.executeInsert()==0){
                Log.i("insert", "第"+i+"条插入失败！");
            }else{
                result++;
            }
        }
        Log.i("insert", "已插入数据条目数量"+result+"条");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void insertBatchSongData(){
        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into song(songid,name,pubdate,style,label,album,language,song,lyric,time,singerid) values(?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement sstatement = db.compileStatement(sql);
        db.beginTransaction();
        String[] songid={"song0001","song0002","song0003","song0004","song0005","song0006","song0007","song0008","song0009","song0010",
                "song0011","song0012","song0013","song0014","song0015","song0016","song0017","song0018","song0019","song0020","song0021"};
        String[] name={"groaning the blues","what kind od woman is this","A lot to learn about living","Friday Night","Make U love me","Good life","opera 2","西厢记","小蜜蜂","捉泥鳅",
        "暴风雨","蓝色多瑙河","英雄交响曲","悲怆交响曲","拉德斯基进行曲","义勇军进行曲","日不落","倾尽天下","演员","Sugar","怒放的生命"};
        String[] pubdate={"2009/6/21","2006/1/21","2010/3/2","2014/2/4","2017/2/13","2016/11/11","2012/2/8","2013/1/1","1991/1/29","1978/7/8",
        "1802","1866","1840","1893","1848","1935","2007/9/21","2013/4/12","2015/5/20","2015/1/13","2005/12/28"};
        String[] style={"爵士","爵士","乡村音乐","乡村音乐","舞曲","舞曲","歌剧戏曲二人转","歌剧戏曲二人转","儿童歌谣","儿童歌谣",
        "古典","古典","交响乐","交响乐","进行曲","进行曲","流行","流行","流行","摇滚","摇滚"};
        String[] label={"伤感","柔情甜蜜","励志","清新欢快","激情","励志","伤感","柔情甜蜜","励志","清新欢快",
                "励志,伤感,激情","清新欢快","激情","伤感","激情","激情,励志","柔情甜蜜,清新欢快","古风","伤感","柔情甜蜜,伤感","励志,激情"};
        String[] album={"From the cradle","Bring 'em in","Easton corbin","","Eric Paslay","Make u love me","Good life","philosophy of miracle","闫学晶","小雨点",
                "包圣美之歌","null","null","null","null","null","特务J","河图精选","初学者","v","怒放的生命"};
        String[] language={"英语","英语","英语","英语","英语","英语","俄罗斯","中文","中文","中文",
                "德语","奥地利语","德语","俄语","奥地利语","中文","中文","中文","中文","英语","中文"};
        String[] song = {"null","null","null","null","null","null","null","null","null","null",
                "null","null","null","null","null","null","null","null","null","null","null"};
        String[] lyric={"null","null","null","null","null","null","null","null","null","null",
                "null","null","null","null","null","null","null","null","null","null","null"};
        int[] time={367 ,317 ,226 ,170 ,163 ,260,195,244,163,170,410,604,966,689,311,96,228,260,261,235 ,275};

        String[] singerid={"sger0001","sger0002","sger0003","sger0004","sger0005","sger0006",
                "sger0007","sger0008","sger0009","sger0010","sger0011",
                "sger0012","sger0011","sger0013","sger0014","sger0015","sger0016","sger0017",
                "sger0018","sger0019","sger0020"};

        long result = 0;
        for(int i=19;i<21;i++){
            sstatement.bindString(1,songid[i]);
            sstatement.bindString(2,name[i]);
            sstatement.bindString(3,pubdate[i]);
            sstatement.bindString(4,style[i]);
            sstatement.bindString(5,label[i]);
            sstatement.bindString(6,album[i]);
            sstatement.bindString(7,language[i]);
            sstatement.bindString(8,song[i]);
            sstatement.bindString(9,lyric[i]);
            sstatement.bindLong(10,time[i]);
            sstatement.bindString(11,singerid[i]);

            if(sstatement.executeInsert()==0){
                Log.i("insert", "第"+i+"条插入失败！");
            }else{
                result++;
            }
        }
        Log.i("insert", "已插入数据条目数量"+result+"条");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void insertBatchListenRecordData(){
        SQLiteHelper dbHelper = new SQLiteHelper(this,"csddm",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into listen_record(recordid,account,songid,time) values(?,?,?,?)";
        SQLiteStatement sstatement = db.compileStatement(sql);
        db.beginTransaction();
        String[] recordid = {"reco0001","reco0002","reco0003","reco0004","reco0005","reco0006","reco0007","reco0008","reco0009","reco0010",
                "reco0011","reco0012","reco0013","reco0014","reco0015","reco0016","reco0017","reco0018","reco0019","reco0020"};
        String[] account={"111","111","111","111","111","111","111","111","111","111",
                "222","222","222","222","222","222","222","222","222","222"};
        String[] songid = {"song0001","song0012","song0006","song0008","song0011","song0004","song0017","song0001","song0021","song0003",
        "song0003","song0002","song0005","song0007","song0019","song0020","song0010","song0013","song0014","song0015"};
        int[] time = {400 ,1000,768,3456 ,2222,100 ,30 ,900 ,1300 ,567 ,890 ,130 ,20 ,3000 ,5600 ,4400 ,310,730,2333,6666};

        long result = 0;
        for(int i=0;i<20;i++){
            sstatement.bindString(1,recordid[i]);
            sstatement.bindString(2,account[i]);
            sstatement.bindString(3,songid[i]);
            sstatement.bindLong(4,time[i]);

            if(sstatement.executeInsert()==0){
                Log.i("insert", "第"+i+"条插入失败！");
            }else{
                result++;
            }
        }
        Log.i("insert", "已插入数据条目数量"+result+"条");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
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
                    Intent intent = new Intent(".ListeningMusic");
                    intent.putExtra(ListeningMusicActivity.TAG_MUSICINDEX, position);
                    intent.putExtra(MenuActivity.TAG_USERACCOUNT, useraccount);
                    startActivity(intent);
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
