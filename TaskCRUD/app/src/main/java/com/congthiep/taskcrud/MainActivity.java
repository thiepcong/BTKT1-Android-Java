package com.congthiep.taskcrud;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.taskcrud.adapters.JobAdapter;
import com.congthiep.taskcrud.adapters.OnItemClickListener;
import com.congthiep.taskcrud.models.Job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Job> jobList = new ArrayList<>();
    private Job currentJob;
    private RecyclerView recyclerViewJobList;
    private JobAdapter jobAdapter;
    private RadioGroup genderRadio;
    private Button buttonAdd,buttonUpdate;
    private EditText title,content,completeDate,search;
    private boolean isAddOrUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        currentJob = new Job();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        recyclerViewJobList = findViewById(R.id.recyclerViewTourList);
        recyclerViewJobList.setLayoutManager(new LinearLayoutManager(this));
        title = findViewById(R.id.editTextJobTitle);
        content = findViewById(R.id.editTextJobContent);
        completeDate = findViewById(R.id.buttonPickDate);
        search = findViewById(R.id.searchEditText);
        genderRadio = findViewById(R.id.gender);
        RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
        RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonMale.setChecked(true);

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
                List<Job> searchList = jobList.stream().
                        filter(e -> e.getJobTitle().contains(editable.toString())).
                        collect(Collectors.toList());
                jobAdapter = new JobAdapter(MainActivity.this, searchList);
                recyclerViewJobList.setAdapter(jobAdapter);
                jobAdapter.setOnItemClickListener(new OnItemClickListener() {
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

        completeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        completeDate.setText(dd+"/"+(mm+1)+"/"+yy);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAddOrUpdate) return;
                String titleText = title.getText().toString();
                String contentText = content.getText().toString();
                String completeDateText = completeDate.getText().toString();

                if(titleText.isEmpty() || contentText.isEmpty() || completeDateText.isEmpty() || genderRadio == null){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentJob.setJobContent(contentText);
                    currentJob.setJobTitle(titleText);
                    currentJob.setCompletionDate(completeDateText);
                    currentJob.setGender(getGender());
                    jobList.add(currentJob);
                    jobAdapter = new JobAdapter(MainActivity.this, jobList);
                    recyclerViewJobList.setAdapter(jobAdapter);
                    jobAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                    currentJob = new Job();
                    RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
                    RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
                    radioButtonMale.setChecked(true);
                    title.setText("");
                    content.setText("");
                    completeDate.setText("");
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddOrUpdate) return;
                String titleText = title.getText().toString();
                String contentText = content.getText().toString();
                String completeDateText = completeDate.getText().toString();

                if(titleText.isEmpty() || contentText.isEmpty() || completeDateText.isEmpty() || genderRadio == null){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentJob.setJobContent(contentText);
                    currentJob.setJobTitle(titleText);
                    currentJob.setCompletionDate(completeDateText);
                    currentJob.setGender(getGender());
                    if(updateCurrentIndex==-1){
                        showToast("Lỗi update");
                        return;
                    }
                    jobList.set(updateCurrentIndex,currentJob);
                    jobAdapter = new JobAdapter(MainActivity.this, jobList);
                    recyclerViewJobList.setAdapter(jobAdapter);
                    jobAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                    currentJob = new Job();
                    RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
                    RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
                    radioButtonMale.setChecked(true);
                    title.setText("");
                    content.setText("");
                    completeDate.setText("");
                }
                isAddOrUpdate = true;
            }
        });

    }

    private int updateCurrentIndex = -1;

    private boolean getGender(){
        RadioGroup genderRadioGroup = findViewById(R.id.gender);
        boolean isMale = false;

        int selectedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            if (selectedRadioButton.getId() == R.id.radioButtonMale) {
                isMale = true;
            }
        }return isMale;
    }

    public void updateListItem(int position){
        isAddOrUpdate = false;
        updateCurrentIndex = position;
        currentJob = jobList.get(position);
        title.setText(currentJob.getJobTitle());
        content.setText(currentJob.getJobContent());
        completeDate.setText(currentJob.getCompletionDate());
        RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
        RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonMale.setChecked(!currentJob.isGender());
        radioButtonFemale.setChecked(currentJob.isGender());
    }

    public void deleteItem(int position){
        jobList.remove(position);
        jobAdapter = new JobAdapter(this, jobList);
        recyclerViewJobList.setAdapter(jobAdapter);
        jobAdapter.setOnItemClickListener(new OnItemClickListener() {
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
