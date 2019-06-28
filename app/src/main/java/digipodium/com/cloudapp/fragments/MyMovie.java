package digipodium.com.cloudapp.fragments;

import java.util.Map;

class MyMovie {
    // properties should be public
    public String movie;

    public MyMovie(){
        // empty constructor required by firebase
    }

    public MyMovie(String movie) {
        this.movie=movie;
    }
}
