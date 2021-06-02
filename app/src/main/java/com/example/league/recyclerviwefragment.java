package com.example.league;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recyclerviwefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recyclerviwefragment extends Fragment {
    private View leagueView;
    private RecyclerView myleaguelist;
    private DatabaseReference leagueref,usersref;
















    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public recyclerviwefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recyclerviwefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recyclerviwefragment newInstance(String param1, String param2) {
        recyclerviwefragment fragment = new recyclerviwefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }















    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        leagueView= inflater.inflate(R.layout.fragment_recyclerviwefragment, container, false);
        myleaguelist=(RecyclerView) leagueView.findViewById(R.id.league_list);
        myleaguelist.setLayoutManager(new LinearLayoutManager(getContext()));
        leagueref= FirebaseDatabase.getInstance().getReference().child("new league");
        usersref=FirebaseDatabase.getInstance().getReference().child("new league");


        return leagueView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<leagues>()
                .setQuery(leagueref,leagues.class)
                .build();

        FirebaseRecyclerAdapter<leagues,leaguesviewholder> adapter
                = new FirebaseRecyclerAdapter<leagues, leaguesviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull leaguesviewholder leaguesviewholder, int i, @NonNull leagues leagues)
            {



leaguesviewholder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String visite_league_details=getRef(i).getKey();

        Intent profilintent=new Intent(getActivity(),profileActivity.class);
        profilintent.putExtra("visite_league_details",visite_league_details);
        startActivity(profilintent);
    //startActivity(new Intent(getActivity(),profileActivity.class));
    }
});




                String league_id=getRef(i).getKey();
                usersref.child(league_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.hasChild("league name"))
                        {
                            String Lname=snapshot.child("league name").getValue().toString();
                            String Locname=snapshot.child("location").getValue().toString();

                            leaguesviewholder.leaguesname.setText(Lname);
                            leaguesviewholder.locations.setText(Locname);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public leaguesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                leaguesviewholder viewholder=new leaguesviewholder(view);
                return viewholder;
            }
        };
        myleaguelist.setAdapter(adapter);
        adapter.startListening();

    }
    public static class leaguesviewholder extends RecyclerView.ViewHolder
    {
        TextView leaguesname,locations;
        public leaguesviewholder(@NonNull View itemView) {
            super(itemView);

            leaguesname=itemView.findViewById(R.id.league_name);
            locations=itemView.findViewById(R.id.location_name);
        }
    }
}