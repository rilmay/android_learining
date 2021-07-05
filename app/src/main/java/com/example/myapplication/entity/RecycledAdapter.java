package com.example.myapplication.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RecycledAdapter  extends RecyclerView.Adapter<RecycledAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<State> states;

    public interface OnStateClickListener{
        void onStateClick(State state, int position);
    }

    private OnStateClickListener onClickListener;

    public RecycledAdapter(Context context, List<State> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }

    public RecycledAdapter(Context context, List<State> states, OnStateClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecycledAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.adapter_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycledAdapter.ViewHolder holder, int position) {
        State state = states.get(position);

        holder.imageView.setImageResource(state.getFlagResource());
        holder.nameView.setText(state.getName());
        holder.capitalView.setText(state.getCapital());

        holder.count.setText(state.getCount() + "");
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = state.getCount() + 1;
                state.setCount(count);
                holder.count.setText(count + "");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onStateClick(state, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, capitalView, count;
        final Button plus;
        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.flag);
            nameView = (TextView) view.findViewById(R.id.name);
            capitalView = (TextView) view.findViewById(R.id.capital);
            count = (TextView) view.findViewById(R.id.count);
            plus = (Button) view.findViewById(R.id.plus);
        }
    }
}
