package com.pavneetsingh.android.stampit.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pavneetsingh.android.stampit.model.PostBean;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Database(entities = { PostBean.class }, version = 1)
public abstract class PostDB extends RoomDatabase{
    public abstract PostDAO getNoteDao();


    private static PostDB noteDB;

//    public static PostDB getInstance(Context context) {
//        if (null == noteDB) {
//            noteDB = buildDatabaseInstance(context);
//        }
//        return noteDB;
//    }

//    private static NoteDatabase buildDatabaseInstance(Context context) {
//        return Room.databaseBuilder(context,
//                NoteDatabase.class,
//                Constants.DB_NAME)
//                .allowMainThreadQueries().build();
//    }

    public  void cleanUp(){
        noteDB = null;
    }


}
