package digipodium.com.cloudapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import digipodium.com.cloudapp.LoginActivity;
import digipodium.com.cloudapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser()!=null){
                    auth.signOut();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
        return view;
    }

}
