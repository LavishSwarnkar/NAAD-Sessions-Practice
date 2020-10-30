package com.lavish.android.practice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavish.android.practice.databinding.WordlistItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private Context context;
    private List<String> words;

    public WordListAdapter(Context context, List<String> words) {
        this.context = context;
        this.words = words;
    }



    //Creates / Inflates view
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate view for the item
        WordlistItemBinding b = WordlistItemBinding.inflate(
                LayoutInflater.from(context)
                , parent
                , false
        );

        //Create & return ViewHolder
        return new WordViewHolder(b, this);
    }


    //Binds the data to view
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        //Get data
        String word = words.get(position);

        //Bind the data with view
        holder.b.word.setText(word);
    }


    @Override
    public int getItemCount() {
        return words.size();
    }



    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        WordListAdapter adapter;
        WordlistItemBinding b;

        public WordViewHolder(@NonNull WordlistItemBinding b, WordListAdapter adapter) {
            super(b.getRoot());

            this.adapter = adapter;
            this.b = b;

            b.word.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Get position
            int mPosition = getLayoutPosition();

            //Data at position
            String element = words.get(mPosition);

            //Change the string (add prefix)
            words.set(mPosition, "Clicked  " + element);

            //Notify adapter
            adapter.notifyItemChanged(mPosition);
        }
    }


}
