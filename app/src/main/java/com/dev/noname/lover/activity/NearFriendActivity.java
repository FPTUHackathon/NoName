package com.dev.noname.lover.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.dev.noname.lover.R;
import com.dev.noname.lover.adapter.Rv_Place_Adapter;
import com.dev.noname.lover.model.Location;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.GPSTracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class NearFriendActivity extends AppCompatActivity {

    protected ArrayList<Location> listLocation = new ArrayList<>();
    protected Rv_Place_Adapter adapter;
    protected RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<String> listuserID=new ArrayList<>();
    //GPSTracker gpsTracker = new GPSTracker(this);

    private void loadLocaltionFromDB(){

        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listLocation.clear();
                for (DataSnapshot dnsp : dataSnapshot.getChildren()){
                    Location location = new Location();


                    try{
                        Map<String,Object> map = (Map)dnsp.getValue();
                        location.setLat(Double.valueOf((double)map.get("lat")));
                        location.setLon(Double.valueOf((double)map.get("long")));

                        Users users = new Users();
                        users.setName(String.valueOf(map.get("name")));
                        users.setStatus(String.valueOf(map.get("status")));
                        location.setUsser(users);

                        listLocation.add(location);


                        adapter.notifyDataSetChanged();
                       // Log.d("NearFriendACtivity", "onCreate: "+adapter.getItemCount());
                     //   Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException ex){
                        ex.printStackTrace();
                    }

                }
                //Get listUserID
                DatabaseReference db1= FirebaseDatabase.getInstance().getReference().child("Location");
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            listuserID.add(dataSnapshot.getKey());
                            adapter.notifyDataSetChanged();
                            // Log.d("NearFriendACtivity", "onCreate: "+adapter.getItemCount());
                            //   Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException ex){
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_friend);
        loadLocaltionFromDB();

        recyclerView = findViewById(R.id.nearFriendRecyclerView);
        toolbar=findViewById(R.id.tool_near_friend_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find near friend");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        adapter = new Rv_Place_Adapter(listLocation,this,listuserID);
        recyclerView.setAdapter(adapter);


    }
}
