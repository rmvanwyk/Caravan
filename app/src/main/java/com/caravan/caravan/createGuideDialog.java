package com.caravan.caravan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by meaghan on 4/22/18.
 */

public class createGuideDialog extends DialogFragment {
        private DatabaseAccess m_db = DatabaseAccess.getInstance(getActivity());
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
            m_db = DatabaseAccess.getInstance(getActivity());
            m_loc = getArguments().getString("loc");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Create New Blueprint");

            // Set up the input
            final EditText input = new EditText(getActivity());
            input.setHint("Enter Blueprint Name");
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_name = input.getText().toString();
                    //if (m_name.length() == 0 || m_db.getCuratedItem("blueprint",m_name) != null) {
                    //    return;
                    //}
                    UserDO newBP = m_db.createBlueprint(m_name);
                    List<String> first = new ArrayList<>();
                    first.add(m_loc);
                    newBP.setLocationList(first);
                    Log.d("m_name:", newBP.getName());
                    cacheUserBlueprint(newBP, m_loc);
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
            DynamoCacheDatabase database = DynamoCacheDatabase.getInstance(getActivity());
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
        //May be called if you type the same name of existing blueprint in the dialog
        private void cacheUserBlueprint(UserDO blueprint, String firstLoc) {
            Future<CuratedDO> item = m_db.getCuratedItem("location", firstLoc);
            CuratedDO location = new CuratedDO();
            try {
                location = item.get();
            } catch (ExecutionException | InterruptedException e) {
            }
            DynamoCacheDAO dao = DynamoCacheDatabase.getInstance(getActivity()).dynamoCacheDAO();
            if (dao.getUserBlueprintById(blueprint.getName()) == null)
                dao.insertUserBlueprint(new UserBlueprint(blueprint.getName(), blueprint));
            for (CuratedDO blueprintLocation : m_db.getBlueprintLocations(blueprint)) {
                if(blueprintLocation != null) {
                    if(dao.getBlueprintLocationById(blueprintLocation.getName()) == null)
                        dao.insertBlueprintLocation(new BlueprintLocation(blueprintLocation.getName(), blueprintLocation));
                    dao.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing(blueprint.getName(),blueprintLocation.getName()));
                }
            }
            if(dao.getBlueprintLocationById(location.getName()) == null)
                dao.insertBlueprintLocation(new BlueprintLocation(location.getName(), location));
            dao.insertUserBlueprintLocationPairing(new UserBlueprintLocationPairing(blueprint.getName(),location.getName()));

            String logTag = "CREATE_GUIDE_DIALOG";
            Log.d(logTag, "Locations:");
            for(BlueprintLocation location1: dao.getAllBlueprintLocations())
                Log.d(logTag, location1.getId());
            Log.d(logTag, "Blueprints:");
            for(UserBlueprint bp: dao.getAllUserBlueprints())
                Log.d(logTag,bp.getId());
            Log.d(logTag,"Pairings:");
            for(UserBlueprintLocationPairing pairing: dao.getAllUserBlueprintLocationPairings())
                Log.d(logTag,"BlueprintId: "+pairing.getBlueprint_id()+" LocationId: " + pairing.getLocation_id());

        }
    }



