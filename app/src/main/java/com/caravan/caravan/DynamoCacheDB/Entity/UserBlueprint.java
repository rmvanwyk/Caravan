package com.caravan.caravan.DynamoCacheDB.Entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.caravan.caravan.DynamoDB.UserDO;

import lombok.Data;

@Data
@Entity(tableName = "user_blueprints")
public class UserBlueprint {
    @PrimaryKey
    @NonNull
    private String id;
    @Embedded
    private UserDO userDO;

    public UserBlueprint(String id, UserDO userDO) {
        this.id=id;
        this.userDO=userDO;
    }
}
