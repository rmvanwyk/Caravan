package com.caravan.caravan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by meaghan on 4/22/18.
 */

public class ExistingBlueprintDialog extends DialogFragment {
    private DatabaseAccess m_db;
    private String m_loc;
    static saveLocDialog newInstance(String loc) {
        saveLocDialog d= new saveLocDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("loc", loc);
        d.setArguments(args);

        return d;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        m_loc = getArguments().getString("loc");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //List<String> userBP = _db.getAllUserBlueprints();
        String[] items = getItems();
        builder.setTitle("Choose a Blueprint")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            try {
                                UserDO blueprint = (UserDO) m_db.getItem(items[which], "blueprint","user");
                                m_db.addLocationToBlueprint(blueprint, m_loc);
                            }catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                });
         return builder.create();
    }


    private String[] getItems() {
        List<UserDO> userBP = m_db.getAllUserBlueprints();
        String[] items = new String[userBP.size() + 1];
        items[0] = "Create New Blueprint";
        for (int i = 1; i < userBP.size(); i++) {
            items[i] = userBP.get(i).getName();
        }
        return items;
    }
}
