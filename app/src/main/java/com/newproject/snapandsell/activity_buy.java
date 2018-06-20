package com.newproject.snapandsell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newproject.snapandsell.Model.Product;
import com.newproject.snapandsell.RecyclerView.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class activity_buy extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private List<Product> mProducts;
    private RecyclerView mRecyclerView;
    private ProductsAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mRecyclerView =findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_buy.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        mProducts=new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product upload = postSnapshot.getValue(Product.class);
                    mProducts.add(upload);
                }

                mAdapter = new ProductsAdapter(mProducts,activity_buy.this);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(activity_buy.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
