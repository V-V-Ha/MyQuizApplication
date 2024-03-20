package com.example.prm392myquizapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.prm392myquizapplication.data.Subject;

import java.util.List;

@Dao
public interface SubjectDao {



    @Insert(onConflict = OnConflictStrategy.ABORT)
    void  insertSubject(Subject subject);

    @Update
    void updateSubject(Subject subject);

    @Delete
    void deleteSubject(Subject subject);

    @Transaction
    @Query("SELECT * FROM subject")
    List<Subject> getAllSubjects();

    @Transaction
    @Query("SELECT COUNT(*) FROM quiz WHERE SubjectID = :SubjectID")
    int countQuizBySubject(int SubjectID);
}