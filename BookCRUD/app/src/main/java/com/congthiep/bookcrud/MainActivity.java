package com.congthiep.bookcrud;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.bookcrud.adapters.BookAdapter;
import com.congthiep.bookcrud.adapters.OnItemClickListener;
import com.congthiep.bookcrud.models.Book;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Book> bookList = new ArrayList<>();
    private Book currentBook;
    private RecyclerView recyclerViewJobList;
    private BookAdapter bookAdapter;
    private CheckBox science,novel,child;
    private Button buttonAdd,buttonUpdate;
    private EditText name,author,publicDate,search;
    private boolean isAddOrUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        currentBook = new Book();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        recyclerViewJobList = findViewById(R.id.recyclerViewTourList);
        recyclerViewJobList.setLayoutManager(new LinearLayoutManager(this));
        name = findViewById(R.id.book_name);
        author = findViewById(R.id.book_author);
        publicDate = findViewById(R.id.buttonPickDate);
        search = findViewById(R.id.searchEditText);
        science = findViewById(R.id.checkBox1);
        novel = findViewById(R.id.checkBox2);
        child = findViewById(R.id.checkBox3);

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
                List<Book> searchList = bookList.stream().
                        filter(e -> e.getName().contains(editable.toString())).
                        collect(Collectors.toList());
                bookAdapter = new BookAdapter(MainActivity.this, searchList);
                recyclerViewJobList.setAdapter(bookAdapter);
                bookAdapter.setOnItemClickListener(new OnItemClickListener() {
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

        publicDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                        publicDate.setText(dd+"/"+(mm+1)+"/"+yy);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAddOrUpdate) return;
                String nameBook = name.getText().toString();
                String authorBook = author.getText().toString();
                String publicDateBook = publicDate.getText().toString();
                List<String> types = new ArrayList<>();
                boolean scienType = science.isChecked();
                if(scienType) types.add("Khoa học");
                boolean novelType = novel.isChecked();
                if(novelType) types.add("Tiểu thuyết");
                boolean childType = child.isChecked();
                if(childType) types.add("Thiếu nhi");

                if(nameBook.isEmpty() || authorBook.isEmpty() || publicDateBook.isEmpty()){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentBook.setName(nameBook);
                    currentBook.setAuthor(authorBook);
                    currentBook.setPublicDate(publicDateBook);
                    currentBook.setTypes(types);
                    bookList.add(currentBook);
                    bookAdapter = new BookAdapter(MainActivity.this, bookList);
                    recyclerViewJobList.setAdapter(bookAdapter);
                    bookAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                    currentBook = new Book();
                    science.setActivated(false);
                    child.setActivated(false);
                    novel.setActivated(false);
                    name.setText("");
                    author.setText("");
                    publicDate.setText("");
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAddOrUpdate) return;
                String nameBook = name.getText().toString();
                String authorBook = author.getText().toString();
                String publicDateBook = publicDate.getText().toString();
                List<String> types = new ArrayList<>();
                boolean scienType = science.isChecked();
                if(scienType) types.add("Khoa học");
                boolean novelType = novel.isChecked();
                if(novelType) types.add("Tiểu thuyết");
                boolean childType = child.isChecked();
                if(childType) types.add("Thiếu nhi");

                if(nameBook.isEmpty() || authorBook.isEmpty() || publicDateBook.isEmpty()){
                    showToast("Vui lòng nhập đủ các trường");
                }else{
                    currentBook.setName(nameBook);
                    currentBook.setAuthor(authorBook);
                    currentBook.setPublicDate(publicDateBook);
                    currentBook.setTypes(types);
                    if(updateCurrentIndex==-1){
                        showToast("Lỗi update");
                        return;
                    }
                    bookList.set(updateCurrentIndex,currentBook);
                    bookAdapter = new BookAdapter(MainActivity.this, bookList);
                    recyclerViewJobList.setAdapter(bookAdapter);
                    bookAdapter = new BookAdapter(MainActivity.this, bookList);
                    recyclerViewJobList.setAdapter(bookAdapter);
                    bookAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                    currentBook = new Book();
                    science.setActivated(false);
                    child.setActivated(false);
                    novel.setActivated(false);
                    name.setText("");
                    author.setText("");
                    publicDate.setText("");
                }
                isAddOrUpdate = true;
            }
        });

    }

    private int updateCurrentIndex = -1;

    public void updateListItem(int position){
        isAddOrUpdate = false;
        updateCurrentIndex = position;
        currentBook = bookList.get(position);
        name.setText(currentBook.getName());
        author.setText(currentBook.getAuthor());
        publicDate.setText(currentBook.getPublicDate());
        science.setChecked(currentBook.getTypes().contains("Khoa học"));
        novel.setChecked(currentBook.getTypes().contains("Tiểu thuyết"));
        child.setChecked(currentBook.getTypes().contains("Thiếu nhi"));
    }

    public void deleteItem(int position){
        bookList.remove(position);
        bookAdapter = new BookAdapter(this, bookList);
        recyclerViewJobList.setAdapter(bookAdapter);
        bookAdapter.setOnItemClickListener(new OnItemClickListener() {
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
