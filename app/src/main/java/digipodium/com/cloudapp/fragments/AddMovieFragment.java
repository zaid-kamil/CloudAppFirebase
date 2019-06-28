package digipodium.com.cloudapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import digipodium.com.cloudapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMovieFragment extends Fragment {


    private ProgressDialog dialog;

    public AddMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        final EditText editMovie = view.findViewById(R.id.editMovie);
        Button btnAdd = view.findViewById(R.id.btnMovie);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie = editMovie.getText().toString().trim().toUpperCase();
                if(movie.length()==0){
                    Toast.makeText(getActivity(), "invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialog("updating database");
                FirebaseFirestore fs = FirebaseFirestore.getInstance();
                fs.collection("movies")
                        .add(new MyMovie(movie)) // the important data
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                hideDialog();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                                    editMovie.setText("");
                                }else{
                                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return view;
    }
    public void showDialog(String msg){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(msg);
        dialog.setTitle("Please wait");
        dialog.show();
    }

    public void hideDialog(){
        if(dialog !=null && dialog.isShowing()){
            dialog.hide();
        }
    }
}
