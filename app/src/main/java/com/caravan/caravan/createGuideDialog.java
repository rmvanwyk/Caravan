package com.caravan.caravan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.caravan.caravan.DynamoCacheDB.DynamoCacheDAO;
import com.caravan.caravan.DynamoCacheDB.DynamoCacheDatabase;
import com.caravan.caravan.DynamoCacheDB.Entity.BlueprintLocation;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.CuratedBlueprintLocationPairing;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprint;
import com.caravan.caravan.DynamoCacheDB.Entity.UserBlueprintLocationPairing;
import com.caravan.caravan.DynamoDB.CuratedDO;
import com.caravan.caravan.DynamoDB.DatabaseAccess;
import com.caravan.caravan.DynamoDB.UserDO;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by meaghan on 4/22/18.
 */

public class createGuideDialog extends DialogFragment {
        private DatabaseAccess m_db;
        private String m_name;
        private String m_loc;
        static createGuideDialog newInstance(String loc) {
            createGuideDialog d= new createGuideDialog();

        // Supply num input as an argument.
             Bundle args = new Bundle();
             args.putString("loc", loc);
             d.setArguments(args);

        return d;
         }
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            super.onCreateDialog(savedInstanceState);
            m_loc = getArguments().getString("loc");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Create New Blueprint");

            // Set up the input
            final EditText input = new EditText(getActivity());
            input.setText("Enter Blueprint Name");
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_name = input.getText().toString();
                    m_db.createBlueprint(m_name);
                    //m_db.addLocationToBlueprint(m_name, m_loc);
                    //cacheBlueprint(m_db, m_loc);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            //builder.show();
            return builder.create();
        }

        private void cacheCuratedBlueprint(CuratedDO blueprint, String firstLoc) {
            DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
            final DynamoCacheDAO dao = database.dynamoCacheDAO();
            CuratedBlueprint cachedBlueprint = new CuratedBlueprint(blueprint.getName(),blueprint);
            dao.insertCuratedBlueprint(cachedBlueprint);
            DatabaseAccess task = DatabaseAccess.getInstance(getActivity());
            Future<CuratedDO> item = task.getCuratedItem("location", firstLoc);
            CuratedDO location = new CuratedDO();
            try {
                location = item.get();
            } catch (ExecutionException | InterruptedException e) {

            }

            BlueprintLocation blueprintLocation = new BlueprintLocation(location.getName(), location);
            if (dao.getLocationById(location.getName()).equals(null)) {
                dao.insertBlueprintLocation(blueprintLocation);
            }
            CuratedBlueprintLocationPairing pair = new CuratedBlueprintLocationPairing(cachedBlueprint.getId(), blueprintLocation.getId());
            dao.insertCuratedBlueprintLocationPairing(pair);
        }

        //Used to save a newly created User blueprint as well as the (first) location that is added.
        private void cacheUserBlueprint(UserDO blueprint, String firstLoc) {
            DynamoCacheDatabase database = DynamoCacheDatabase.getInMemoryInstance(getActivity());
            final DynamoCacheDAO dao = database.dynamoCacheDAO();
            UserBlueprint cachedBlueprint = new UserBlueprint(blueprint.getName(),blueprint);
            dao.insertUserBlueprint(cachedBlueprint);
            DatabaseAccess task = DatabaseAccess.getInstance(getActivity());
            Future<CuratedDO> item = task.getCuratedItem("location", firstLoc);
            CuratedDO location = new CuratedDO();
            try {
                location = item.get();
            } catch (ExecutionException | InterruptedException e) {

            }

            BlueprintLocation blueprintLocation = new BlueprintLocation(location.getName(), location);
            if (dao.getLocationById(location.getName()).equals(null)) {
                dao.insertBlueprintLocation(blueprintLocation);
            }
            UserBlueprintLocationPairing pair = new UserBlueprintLocationPairing(cachedBlueprint.getId(), blueprintLocation.getId());
            dao.insertUserBlueprintLocationPairing(pair);
        }
    }



