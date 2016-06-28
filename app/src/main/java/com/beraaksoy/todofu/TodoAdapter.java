package com.beraaksoy.todofu;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import github.nisrulz.recyclerviewhelper.RVHAdapter;

/**
 * Created by beraaksoy on 6/6/16.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> implements RVHAdapter {

    private List<Todo> mTodoList = new ArrayList<>();
    private final Context mContext;


    public TodoAdapter(List<Todo> list, Context context) {
        mTodoList = list;
        mContext = context;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(todoItemView);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Todo todoItem = mTodoList.get(position);
        holder.itemTitleView.setText(todoItem.getTitle());
        holder.itemPriorityView.setText(todoItem.getPriority());
        holder.itemDateView.setText(todoItem.getDate());

        switch (holder.itemPriorityView.getText().toString()) {
            case MainActivity.TODAY:
                holder.itemPriorityView.setBackground(mContext.getResources().getDrawable(R.drawable.priority_today_bg));
                //holder.itemPriorityView.setBackgroundColor(mContext.getResources().getColor(R.color.today));
                break;

            case MainActivity.SOON:
                //holder.itemPriorityView.setBackgroundColor(mContext.getResources().getColor(R.color.soon));
                holder.itemPriorityView.setBackground(mContext.getResources().getDrawable(R.drawable.priority_soon_bg));
                break;

            case MainActivity.LATER:
                //holder.itemPriorityView.setBackgroundColor(mContext.getResources().getColor(R.color.later));
                holder.itemPriorityView.setBackground(mContext.getResources().getDrawable(R.drawable.priority_later_bg));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        swap(fromPosition, toPosition);
        return false;
    }


    private void swap(int firstPosition, int secondPosition) {
        Collections.swap(mTodoList, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }


    @Override
    public void onItemDismiss(final int position, int direction) {
        final Todo todo = mTodoList.get(position);

        final Long id = todo.getId();
        final String title = todo.getTitle();
        final String note = todo.getNote();
        final String date = todo.getDate();
        final String priority = todo.getPriority();

        final TodoDAO dao = new TodoDAO(mContext);
        dao.delete(todo);
        mTodoList.remove(position);
        notifyItemRemoved(position);
        Snackbar snackbar = Snackbar.make(((MainActivity) mContext).findViewById(R.id.todoItemTitle), "Todo Deleted", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Todo Restored", Toast.LENGTH_SHORT).show();
                onItemUndoActionClicked();
            }

            private void onItemUndoActionClicked() {
                Todo todoItem = new Todo(id, title, note, date, priority);
                dao.insert(todoItem);
                mTodoList.add(position, todoItem);
                notifyDataSetChanged();
                // TODO: Need to fix a small bug. A todo item modified after UNDO saves on second try.
            }
        });
        snackbar.show();

    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitleView;
        private final TextView itemPriorityView;
        private final TextView itemDateView;

        public TodoViewHolder(final View itemView) {
            super(itemView);
            itemTitleView = (TextView) itemView.findViewById(R.id.todoItemTitle);
            itemPriorityView = (TextView) itemView.findViewById(R.id.radioToday);
            itemDateView = (TextView) itemView.findViewById(R.id.todoItemDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = DetailActivity.getActionIntent(context, MainActivity.TODOITEM, mTodoList.get(getAdapterPosition()), MainActivity.ACTION_EDIT);
                    Log.d("Position: ", mTodoList.get(getAdapterPosition()).getId().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
