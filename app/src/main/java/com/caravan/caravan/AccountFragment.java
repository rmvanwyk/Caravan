package com.caravan.caravan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amazonaws.mobile.auth.core.IdentityManager;

public class AccountFragment extends Fragment {
    Button signOutButton;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        signOutButton = (Button) view.findViewById(R.id.button_signout);
        signOutButton.setOnClickListener((buttonView) -> {
            final IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
            if (identityManager.isUserSignedIn()) {
                identityManager.signOut();
            }
        });
        return view;
    }
}
