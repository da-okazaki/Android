package com.example.username.schedulebook;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user.name on 2017/10/22.
 */

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;     // 予定を見分けるためのIDが必要
    public Date date;   //予定の日付
    public String title;    // 予定のタイトル
    public String detail;   // 予定の詳細
}
