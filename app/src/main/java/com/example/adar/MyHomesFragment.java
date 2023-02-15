package com.example.adar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class MyHomesFragment extends Fragment {

    private RecyclerView mMyHomesRecycler;
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;
    private FirestoreRecyclerAdapter<HouseModel, MyHomesFragment.houseViewHolder> houseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_homes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        Query query = mStore.collection("houses").document(mUser.getUid()).collection("myHomes");
        FirestoreRecyclerOptions<HouseModel> allMyHouses = new FirestoreRecyclerOptions.Builder<HouseModel>().setQuery(query,HouseModel.class).build();

        houseAdapter = new FirestoreRecyclerAdapter<HouseModel, MyHomesFragment.houseViewHolder>(allMyHouses) {
            @Override
            protected void onBindViewHolder(@NonNull MyHomesFragment.houseViewHolder holder, int position, @NonNull HouseModel model) {

                holder.mHouseCardCity.setText(model.getCity() + " - "+ model.getCountry());
                holder.mHouseCardPrice.setText("$"+model.getPrice()+" / night");
                Picasso.get().load(model.getImageUrl()).into(holder.mHouseCardImage);

                String houseId = houseAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(view.getContext(),HouseDetails.class);
                        intent.putExtra("houseId", houseId);
                        intent.putExtra("city", model.getCity());
                        intent.putExtra("country",model.getCountry());
                        intent.putExtra("price",model.getPrice());
                        intent.putExtra("description",model.getDescription());
                        intent.putExtra("phone",model.getPhone());
                        intent.putExtra("imageUrl", model.getImageUrl());
                        intent.putExtra("isOwnHouse","true");

                        view.getContext().startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public MyHomesFragment.houseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homes_layout, parent, false);
                return new MyHomesFragment.houseViewHolder(view);
            }
        };

        mMyHomesRecycler = getView().findViewById(R.id.myHomesRecycler);
        mMyHomesRecycler.setHasFixedSize(true);
        mMyHomesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        mMyHomesRecycler.setItemAnimator(null);
        mMyHomesRecycler.setAdapter(houseAdapter);
    }

    public class houseViewHolder extends RecyclerView.ViewHolder{

        TextView mHouseCardCity;
        TextView mHouseCardPrice;
        ImageView mHouseCardImage;


        public houseViewHolder(@NonNull View itemView) {

            super(itemView);
            mHouseCardCity = itemView.findViewById(R.id.houseCardCity);
            mHouseCardPrice = itemView.findViewById(R.id.houseCardPrice);
            mHouseCardImage = itemView.findViewById(R.id.houseCardImage);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        houseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(houseAdapter != null){
            houseAdapter.stopListening();
        }
    }
}