package digipodium.com.cloudapp.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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

import digipodium.com.cloudapp.MainActivity;
import digipodium.com.cloudapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    private TextView textStatus;
    private EditText editEmail;
    private EditText editPassword;
    private ProgressDialog dialog;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        textStatus = view.findViewById(R.id.textStatus);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim().toLowerCase();
                String password = editPassword.getText().toString().trim().toLowerCase();
                if (email.length() > 11 && password.length() > 6) {
                    showDialog("registering...");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    hideDialog();
                                    if (task.isSuccessful()) {
                                        textStatus.setTextColor(Color.GREEN);
                                        textStatus.setText("Logging in");
                                        updateUI(task.getResult().getUser());
                                    }else{
                                        Exception exception = task.getException();
                                        textStatus.setText(exception.getMessage());
                                        textStatus.setTextColor(Color.RED);
                                    }
                                }
                            });
                }
            }
        });
        return view;
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish(); // kill the activity after login
        }else{
            textStatus.setText("There was problem getting user info");
        }
    }

    public void showDialog(String msg){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(msg);
        dialog.setTitle("Please wait");
        dialog.show();
    }

    public void hideDialog(){
        if(dialog!=null && dialog.isShowing()){
            dialog.hide();
        }
    }
}
