package com.congthiep.tour_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.congthiep.tour_crud.adapter.OnItemClickListener;
import com.congthiep.tour_crud.adapter.TourAdapter;
import com.congthiep.tour_crud.adapter.VehicleAdapter;
import com.congthiep.tour_crud.models.Tour;
import com.congthiep.tour_crud.models.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Tour> tourList = new ArrayList<>();
    private Tour currentTour;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private Spinner spinnerVehicle;
    private RecyclerView recyclerViewTourList;
    private TourAdapter tourAdapter;
    private Button buttonAdd,buttonUpdate;
    private EditText from,to,duration,search;
    private boolean isAddOrUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        currentTour = new Tour();

        vehicles.add(new Vehicle("Xe máy", R.drawable.ic_motorcycle));
        vehicles.add(new Vehicle("Ô tô", R.drawable.ic_car));
        vehicles.add(new Vehicle("Máy bay", R.drawable.ic_plane));

        spinnerVehicle = findViewById(R.id.spinnerVehicle);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        recyclerViewTourList = findViewById(R.id.recyclerViewTourList);
        recyclerViewTourList.setLayoutManager(new LinearLayoutManager(this));
        from = findViewById(R.id.editTextFrom);
        to = findViewById(R.id.editTextTo);
        duration = findViewById(R.id.editTextDuration);
        search = findViewById(R.id.searchEditText);

        VehicleAdapter adapter = new VehicleAdapter(this, vehicles);
        spinnerVehicle.setAdapter(adapter);

        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Vehicle selectedVehicle = (Vehicle) parentView.getItemAtPosition(position);
                String selectedVehicleName = selectedVehicle.getName();
                int selectedVehicleImage = selectedVehicle.getImageResourceId();

                currentTour.setVehicle(new Vehicle(selectedVehicleName,selectedVehicleImage));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                currentTour.setVehicle(new Vehicle("Xe máy", R.drawable.ic_motorcycle));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("devBẻoeChange",charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("devChange",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.d("EfterdevChange",editable.toString());
                List<Tour> searchList = tourList.stream().
                        filter(e -> e.getItinerary().contains(editable.toString())).
                        collect(Collectors.toList());
                tourAdapter = new TourAdapter(MainActivity.this, searchList);
                recyclerViewTourList.setAdapter(tourAdapter);
                tourAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onUpdateClick(int position) {
                        isAddOrUpdate = true;
                        Log.d("de","update"+position);
                        updateListItem(position);
                    }

                    @Override
                    public void onDeleteClick(int position) {
                        Log.d("de","delete"+position);

                        deleteItem(position);
                    }
                });
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAddOrUpdate) return;
                String fromText = from.getText().toString();
                String toText = to.getText().toString();
                String durationText = duration.getText().toString();
                if(fromText.isEmpty() || toText.isEmpty() || durationText.isEmpty()){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentTour.setItinerary(fromText+"-"+toText);
                    currentTour.setDuration(durationText);
                    tourList.add(currentTour);
                    tourAdapter = new TourAdapter(MainActivity.this, tourList);
                    recyclerViewTourList.setAdapter(tourAdapter);
                    tourAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onUpdateClick(int position) {
                            isAddOrUpdate = true;
                            Log.d("de","update"+position);
                            updateListItem(position);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            Log.d("de","delete"+position);

                            deleteItem(position);
                        }
                    });
                    currentTour = new Tour();
                    currentTour.setVehicle(new Vehicle("Xe máy", R.drawable.ic_motorcycle));
                    from.setText("");
                    to.setText("");
                    duration.setText("");
                    spinnerVehicle.setSelection(0);
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddOrUpdate) return;
                String fromText = from.getText().toString();
                String toText = to.getText().toString();
                String durationText = duration.getText().toString();
                if(fromText.isEmpty() || toText.isEmpty() || durationText.isEmpty()){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentTour.setItinerary(fromText+"-"+toText);
                    currentTour.setDuration(durationText);
                    if(updateCurrentIndex==-1){
                        showToast("Lỗi update");
                        return;
                    }
                    tourList.set(updateCurrentIndex,currentTour);
                    tourAdapter = new TourAdapter(MainActivity.this, tourList);
                    recyclerViewTourList.setAdapter(tourAdapter);
                    tourAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onUpdateClick(int position) {
                            isAddOrUpdate = true;
                            Log.d("de","update"+position);
                            updateListItem(position);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            Log.d("de","delete"+position);

                            deleteItem(position);
                        }
                    });
                    currentTour = new Tour();
                    currentTour.setVehicle(new Vehicle("Xe máy", R.drawable.ic_motorcycle));
                    from.setText("");
                    to.setText("");
                    duration.setText("");
                    spinnerVehicle.setSelection(0);
                }
                isAddOrUpdate = true;
            }
        });

    }

    private int updateCurrentIndex = -1;

    public void updateListItem(int position){
        isAddOrUpdate = false;
        updateCurrentIndex = position;
        currentTour = tourList.get(position);
        String[] te = currentTour.getItinerary().split("-");
        from.setText(te[0]);
        to.setText(te[1]);
        duration.setText(currentTour.getDuration());
        spinnerVehicle.setSelection(0);
    }

    public void deleteItem(int position){
        tourList.remove(position);
        tourAdapter = new TourAdapter(this, tourList);
        recyclerViewTourList.setAdapter(tourAdapter);
        tourAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                isAddOrUpdate = true;
                Log.d("de","update"+position);
                updateListItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                Log.d("de","delete"+position);

                deleteItem(position);
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
