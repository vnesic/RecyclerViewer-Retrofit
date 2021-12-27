package com.vnesic.htetest.cache.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> getAll();

    @Query("SELECT * FROM post WHERE userId IN (:userIds)")
    List<Post> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM post WHERE id LIKE :first AND " +
            "userId LIKE :last LIMIT 1")
    Post findByName(String first, String last);

    @Query("DELETE FROM post WHERE id = :postId")
    void deleteSpecificPost(String postId);

    @Query("SELECT userId FROM post")
    List<Integer> getAllUserIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    int delete(Post post);

    @Query("DELETE FROM post")
    int  palpatin_doIt();
}
