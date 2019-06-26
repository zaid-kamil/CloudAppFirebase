package digipodium.com.cloudapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import digipodium.com.cloudapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    private TextView textStatus;
    private EditText editName;
    private EditText editEmail;
    private EditText editConfPass;
    private EditText editPassword;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editConfPass = view.findViewById(R.id.editConfPass);
        editPassword = view.findViewById(R.id.editPass);
        textStatus = view.findViewById(R.id.textStatus);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String email = editEmail.getText().toString().trim().toLowerCase();
                String password = editPassword.getText().toString().trim().toLowerCase();
                String confpass = editConfPass.getText().toString().trim().toLowerCase();
                if (password.equals(confpass)) {
                    if (email.length() > 11) {
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    user.sendEmailVerification();
                                    String name = editName.getText().toString().trim().toLowerCase();
                                    UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                                            .Builder()
                                            .setDisplayName(name)
                                            .build();

                                    user.updateProfile(changeRequest);
                                }else{
                                    // alert the user
                                }
                            }
                        });
                    }
                }

            }
        });
        return view;
    }

}
