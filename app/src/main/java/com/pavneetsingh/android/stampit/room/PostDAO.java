package com.pavneetsingh.android.stampit.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pavneetsingh.android.stampit.model.PostBean;

import java.util.List;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@Dao
public interface PostDAO {

    String TABLE = "postTable";

    @Query("Select * FROM "+TABLE +" where userId = :uID")
    List<PostBean> gerAll(String uID);

    /*
 * Insert the object in database
 * @param note, object to be inserted
 */
    @Insert
    long insert(PostBean bean);

    /*
    * update the object in database
    * @param note, object to be updated
    */
    @Update
    void update(PostBean bean);

    /*
    * delete the object from database
    * @param note, object to be deleted
    */
    @Delete
    void delete(PostBean bean);

    /*
    * delete list of objects from database
    * @param note, array of objects to be deleted
    */
    @Delete
    void delete(PostBean... note);      // Note... is varargs, here note is an array

}
