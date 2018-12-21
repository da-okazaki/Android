package com.example.username.schedulebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class InputActivity extends AppCompatActivity {

    private Realm mRealm;
    private Long mId;
    private TextView mDate;
    private EditText mTitle;
    private EditText mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mRealm = Realm.getDefaultInstance();
        mRealm = Realm.getDefaultInstance();
        mDate = (TextView) findViewById(R.id.date);
        mTitle = (EditText) findViewById(R.id.title);
        mDetail = (EditText) findViewById(R.id.detail);

        if (getIntent() != null) {
            // マル４，遷移先でgetなになにExtraメソッドによりデータを取り出す
            mId = getIntent().getLongExtra("ID", -1);

            // 取り出すだけなら、TransactionはなくてもOKです。
            Schedule schedule= mRealm.where(Schedule.class)
                    .equalTo("id",mId)
                    .findFirst();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.date);
            mDate.setText(formatDate);
            mTitle.setText(schedule.title);
            mDetail.setText(schedule.detail);
        }

        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id", mId)
                                .findFirst();
                        diary.title = editable.toString();
                    }
                });
            }
        });

        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id", mId)
                                .findFirst();
                        diary.detail = editable.toString();
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
