package com.caravan.caravan.DynamoCacheDB.Entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import lombok.Data;

@Data
@Entity(tableName = "user_pairings", primaryKeys = {"blueprint_id", "location_id"})
public class UserBlueprintLocationPairing {
    @NonNull
    private String blueprint_id;
    @NonNull
    private String location_id;

    public UserBlueprintLocationPairing(String blueprint_id, String location_id) {
        this.blueprint_id=blueprint_id;
        this.location_id=location_id;
    }
}
