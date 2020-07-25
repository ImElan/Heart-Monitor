package app.android.heartmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ValuesActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<String> mValues;
    private RecyclerView mRecycler;
    private RecyclerViewAdapter mAdapter;
    private String patient_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);

        patient_name = getIntent().getStringExtra("patient_name");

        Toolbar mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(patient_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child(patient_name);
        mDatabase.keepSynced(true);

        mValues = new ArrayList<>();
        mRecycler = findViewById(R.id.main_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
//                    Log.d("FIREBASE_CHECK",ds.getValue()+"");
                    mValues.add(ds.getValue().toString());
                }
                mAdapter = new RecyclerViewAdapter(mValues,ValuesActivity.this);
                mRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.graph_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.view_graph) {
            Intent GraphIntent = new Intent(ValuesActivity.this,GraphActivity.class);
            GraphIntent.putExtra("values",mValues);
            startActivity(GraphIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
