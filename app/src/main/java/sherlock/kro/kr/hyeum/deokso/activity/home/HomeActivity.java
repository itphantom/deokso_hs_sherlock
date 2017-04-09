package sherlock.kro.kr.hyeum.deokso.activity.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;

import itmir.tistory.com.spreadsheets.GoogleSheetTask;
import sherlock.kro.kr.hyeum.deokso.R;
import sherlock.kro.kr.hyeum.deokso.tool.Database;
import sherlock.kro.kr.hyeum.deokso.tool.TimeTableTool;
import sherlock.kro.kr.hyeum.deokso.tool.Tools;

public class HomeActivity extends AppCompatActivity {
    public static final String HomeDBName = "StudentHome.db";
    public static final String HomeTableName = "HomeInfo";
    String link;
    Intent int1;
    ListView mListView;
    HomeAdapter mAdapter;
    ProgressDialog mDialog;
    AdapterView.OnItemClickListener ListViewExampleClickListener;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


        mListView = (ListView) findViewById(R.id.mListView2);
        mAdapter = new HomeAdapter(this);
        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showHomeData(true);
            }
        });
        int1=new Intent(getApplicationContext(),Homedetail.class);


        showHomeData(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeShowData mData = mAdapter.getItem(position);
               String a= mData.link;
                Log.v("dsww",a);
                int1.putExtra("in1",a);
                startActivity(int1);
            }
        });
    }

    private void showHomeData(boolean forceUpdate) {
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

        if (Tools.isOnline(getApplicationContext())) {
            if (Tools.isWifi(getApplicationContext()) || forceUpdate) {
                getHomeDownloadTask mTask = new getHomeDownloadTask();
                mTask.execute("https://docs.google.com/spreadsheets/d/1jeqHqKJClUrloOXMYME_SFotOz7_o9c1LjaOdCgxSCw/pubhtml?gid=1145669001&single=true");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.no_wifi_title);
                builder.setMessage(R.string.no_wifi_msg);
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        offlineData();
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getHomeDownloadTask mTask = new getHomeDownloadTask();
                        mTask.execute("https://docs.google.com/spreadsheets/d/1jeqHqKJClUrloOXMYME_SFotOz7_o9c1LjaOdCgxSCw/pubhtml?gid=1145669001&single=true");
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            offlineData();
        }
    }

    private void offlineData() {
        if (new File(TimeTableTool.mFilePath + HomeDBName).exists()) {
            showListViewDate();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatErrorAlertDialogStyle);
            builder.setTitle(R.string.no_network_title);
            builder.setMessage(R.string.no_network_msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }

        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

    }

    class getHomeDownloadTask extends GoogleSheetTask {
        private Database mDatabase;
        private String[] columnFirstRow;

        @Override
        public void onPreDownload() {
            mDialog = new ProgressDialog(HomeActivity.this);
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.loading_title));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();

            new File(TimeTableTool.mFilePath + HomeDBName).delete();
            mDatabase = new Database();


        }

        @Override
        public void onFinish(long result) {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

            if (mDatabase != null)
                mDatabase.release();

            showListViewDate();
        }

        @Override
        public void onRow(int startRowNumber, int position, String[] row) {
            if (startRowNumber == position) {
                columnFirstRow = row;

                StringBuilder Column = new StringBuilder();

                // remove deviceId
                for (int i = 0; i < row.length ; i++) {
                    Column.append(row[i]);
                    Column.append(" text, ");
                }

                mDatabase.openOrCreateDatabase(TimeTableTool.mFilePath, HomeDBName, HomeTableName, Column.substring(0, Column.length() - 2));
            } else {
                int length = row.length;
                for (int i = 0; i < length ; i++) {
                    mDatabase.addData(columnFirstRow[i], row[i]);
                }
                mDatabase.commit(HomeTableName);
            }
        }
    }

    private void showListViewDate() {
        Database mDatabase = new Database();
        mDatabase.openDatabase(TimeTableTool.mFilePath, HomeDBName);
        Cursor mCursor = mDatabase.getData(HomeTableName, "*");

        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToNext();

            String date = mCursor.getString(1);
            String title = mCursor.getString(2);
            String message = mCursor.getString(3);
            link = mCursor.getString(4);
            mAdapter.addItem(title, message, date,link);

            int1.putExtra("link",link);

        }

        mAdapter.notifyDataSetChanged();

        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

}
