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

import java.util.List;

/**
 * Created by meaghan on 4/22/18.
 */

public class saveLocDialog extends DialogFragment {
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
                        // The 'which' argument contains the index position
                        // of the selected item'
                        if (which == 0) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                ft.remove(prev);
                            }
                            ft.addToBackStack(null);

                            // Create and show the dialog.
                            createGuideDialog create = createGuideDialog.newInstance(m_loc);
                            create.show(ft, "dialog");

                        }
                        else {
                            m_db.addLocationToBlueprint(items[which], m_loc);
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private String[] getItems() {
        List<String> userBP = m_db.getAllUserBlueprints();
        String[] items = new String[userBP.size() + 1];
        items[0] = "Create New Blueprint";
        for (int i = 1; i < userBP.size(); i++) {
            items[i] = userBP.get(i);
        }
        return items;
    }
}
