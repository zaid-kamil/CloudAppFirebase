package digipodium.com.cloudapp.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import digipodium.com.cloudapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        final RecyclerView movieView = view.findViewById(R.id.movieList);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("movies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    List<MyMovie> movies = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        MyMovie movie = snapshot.toObject(MyMovie.class);
                        movies.add(movie);
                    }
                    movieView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    movieView.setAdapter(new MovieListAdapter(getActivity(),movies));
                } else {
                    Exception exception = task.getException();
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.Holder> {

        private final LayoutInflater inflater;
        private final List<MyMovie> movieList;

        public MovieListAdapter(Context context, List<MyMovie> movieList) {
            inflater = LayoutInflater.from(context);
            this.movieList = movieList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=inflater.inflate(R.layout.movie_card,parent,false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            MyMovie movie = movieList.get(position);
            holder.textMovie.setText(movie.movie);
            holder.textClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "nothing", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        class Holder extends RecyclerView.ViewHolder{
            TextView textMovie,textClick;
            public Holder(@NonNull View itemView) {
                super(itemView);
                textClick= itemView.findViewById(R.id.textClick);
                textMovie= itemView.findViewById(R.id.textMovie);
            }
        }
    }
}
