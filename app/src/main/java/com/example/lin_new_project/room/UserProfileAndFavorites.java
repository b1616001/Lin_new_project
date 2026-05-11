package com.example.lin_new_project.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.lin_new_project.room.entity.FavoritesEntity;
import com.example.lin_new_project.room.entity.UserProfileEntity;

import java.util.List;

public class UserProfileAndFavorites {
    @Embedded
    public UserProfileEntity userProfileEntity;

    @Relation(parentColumn = "uid",
            entityColumn = "favoriteParentUid",
            entity = FavoritesEntity.class
    )
    public List<FavoritesEntity> favoritesEntityList;
}
