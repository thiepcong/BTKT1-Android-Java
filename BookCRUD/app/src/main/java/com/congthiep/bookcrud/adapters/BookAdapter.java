package com.congthiep.bookcrud.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.bookcrud.R;
import com.congthiep.bookcrud.models.Book;

import java.text.SimpleDateFormat;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mBooks;
    private Context mContext;
    private static OnItemClickListener mListener;

    public BookAdapter(Context context, List<Book> books) {
        mContext = context;
        mBooks = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextBookTitle;
        private TextView mTextAuthor;
        private CheckBox mCheckBoxScience;
        private CheckBox mCheckBoxNovel;
        private CheckBox mCheckBoxChildren;
        private TextView mTextPublishDate;
        TextView buttonUpdate;
        TextView buttonDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextBookTitle = itemView.findViewById(R.id.text_book_title);
            mTextAuthor = itemView.findViewById(R.id.text_author);
            mCheckBoxScience = itemView.findViewById(R.id.checkbox_science);
            mCheckBoxNovel = itemView.findViewById(R.id.checkbox_novel);
            mCheckBoxChildren = itemView.findViewById(R.id.checkbox_children);
            mTextPublishDate = itemView.findViewById(R.id.text_publish_date);
            buttonUpdate = itemView.findViewById(R.id.textViewUpdate);
            buttonDelete = itemView.findViewById(R.id.textViewDelete);
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onUpdateClick(position);
                        }
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Book book) {
            mTextBookTitle.setText(book.getName());
            mTextAuthor.setText(book.getAuthor());
            mCheckBoxScience.setChecked(book.getTypes().contains("Khoa học"));
            mCheckBoxNovel.setChecked(book.getTypes().contains("Tiểu thuyết"));
            mCheckBoxChildren.setChecked(book.getTypes().contains("Thiếu nhi"));
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            mTextPublishDate.setText(book.getPublicDate());
        }
    }
}
