package com.ritik.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnProfileUpdate;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavSport);

        edtProfileFavSport.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    btnProfileUpdate.callOnClick();
                }
                return false;
            }
        });

        btnProfileUpdate = view.findViewById(R.id.btnProfileUpdate);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("profileName") != null) {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }
        if(parseUser.get("profileBio") != null) {
            edtProfileBio.setText(parseUser.get("profileBio").toString());
        }
        if(parseUser.get("profileProfession") != null) {
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());
        }
        if(parseUser.get("profileHobbies") != null) {
            edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());
        }
        if(parseUser.get("profileFavSport") != null) {
            edtProfileFavSport.setText(parseUser.get("profileFavSport").toString());
        }

        btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info...");
                progressDialog.show();

                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileFavSport", edtProfileFavSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            FancyToast.makeText(getContext(), "Profile Updated", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                        }
                        else {
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

}
