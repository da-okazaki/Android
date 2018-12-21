package com.example.username.schedulebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();
        mListView = (ListView) findViewById(R.id.listView);

        RealmResults<Schedule> schedules
                = mRealm.where(Schedule.class).findAll();
        ScheduleAdapter adapter = new ScheduleAdapter(schedules);

        mListView.setAdapter(adapter);

        Button dbTest = (Button) findViewById(R.id.db_test_button);
        dbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RealmTestActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long[] newId = new long[1];
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number max = realm.where(Schedule.class).max("id");
                        newId[0] = 0;
                        if (max != null) { // nullチェック
                            newId[0] = max.longValue() + 1;
                        }

                        Schedule schedule
                                = realm.createObject(Schedule.class, newId[0]);
                        schedule.date = new Date();
                        schedule.title = "";
                        schedule.detail = "";

                    }
                });

                Intent intent = new Intent(MainActivity.this,InputActivity.class);
                // "ID"という名前で受け渡しする
                intent.putExtra("ID", newId[0]);
                startActivity(intent);

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScheduleAdapter adapter = (ScheduleAdapter) mListView.getAdapter();
                Schedule schedule = adapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("ID",schedule.id);
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ScheduleAdapter adapter = (ScheduleAdapter) mListView.getAdapter();
                final Schedule schedule = adapter.getItem(i);
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        schedule.deleteFromRealm();
                    }
                });
                Snackbar.make(view, "削除しました", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar
                                        .make(view, "OKが押されたときの処理",Snackbar.LENGTH_SHORT)
                                        .show();

                            }
                        })
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
