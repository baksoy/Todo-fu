package com.beraaksoy.todofu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beraaksoy on 6/6/16.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    List<ToDo> mToDoList = new ArrayList<>();

    public ToDoAdapter(List<ToDo> list) {
        mToDoList = list;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View toDoItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ToDoViewHolder(toDoItemView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        ToDo toDo = mToDoList.get(position);
        holder.itemTitleView.setText(toDo.getTitle());
        holder.itemPriorityView.setText(toDo.getPriority());
    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitleView;
        private TextView itemPriorityView;

        public ToDoViewHolder(View itemView) {
            super(itemView);

            itemTitleView = (TextView) itemView.findViewById(R.id.todoItemTitle);
            itemPriorityView = (TextView) itemView.findViewById(R.id.radioToday);
        }
    }

}
