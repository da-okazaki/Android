package com.example.username.schedulebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmTestActivity extends AppCompatActivity {

    Realm mRealm;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);

        mRealm = Realm.getDefaultInstance();

        mTextView = (TextView) findViewById(R.id.textView);
        Button create = (Button) findViewById(R.id.create);
        Button read = (Button) findViewById(R.id.read);
        Button update = (Button) findViewById(R.id.update);
        Button delete = (Button) findViewById(R.id.delete);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number max = realm.where(Schedule.class).max("id");
                        long newId = 0;
                        if(max != null) { // nullチェック
                            newId = max.longValue() + 1;
                        }

                        Schedule schedule
                                = realm.createObject(Schedule.class, newId);
                        schedule.date = new Date();
                        schedule.title = "登録テスト";
                        schedule.detail = "スケジュールの詳細情報です";

                        // 保存するスケジュールをTextViewに表示します。
                        mTextView.setText("登録しました\n"
                                + schedule.toString());
                    }
                });
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Schedule> schedules
                                = realm.where(Schedule.class).findAll();

                        mTextView.setText("取得");
                        for (Schedule schedule :
                                schedules) {
                            String text = mTextView.getText() + "\n"
                                    + schedule.toString();
                            mTextView.setText(text);
                        }
                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule schedule = realm.where(Schedule.class)
                                .equalTo("id", 0)
                                .findFirst();
                        schedule.title += "＜更新＞";
                        schedule.detail += "＜更新＞";

                        mTextView.setText("更新しました\n"
                                + schedule.toString());

                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number min = realm.where(Schedule.class).min("id");
                        if (min != null) { // nullチェック

                            Schedule schedule = realm.where(Schedule.class)
                                    .equalTo("id", min.longValue())
                                    .findFirst();

                            schedule.deleteFromRealm();

                            mTextView.setText("削除しました\n"
                                    + schedule.toString());
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}
