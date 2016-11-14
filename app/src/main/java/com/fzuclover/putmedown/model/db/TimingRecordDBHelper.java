package com.fzuclover.putmedown.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lkl on 2016/11/14.
 */

public class TimingRecordDBHelper extends SQLiteOpenHelper {

    public static final String CREATE_TIMING_RECORD = "create table timing_record ("
            + "id integer primary key autoincrement, "
            + "start_time text not null, "
            + "total_time integer not null, "
            + "timing_comments text, "
            + "is_success integer, "
            + "fail_comments text, "
            + "end_time text)";

    public TimingRecordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TIMING_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
