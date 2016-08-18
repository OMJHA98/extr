package com.expensemanager.app.expense;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expensemanager.app.R;
import com.expensemanager.app.models.Expense;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhaolong Zhong on 8/16/16.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG= ExpenseAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_DEFAULT = 0;
    private ArrayList<Expense> expenses;
    private Context context;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return this.expenses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_DEFAULT:
                View view = inflater.inflate(R.layout.expense_item_default, parent, false);
                viewHolder = new ViewHolderDefault(view);
                break;
            default:
                View defaultView = inflater.inflate(R.layout.expense_item_default, parent, false);
                viewHolder = new ViewHolderDefault(defaultView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_DEFAULT:
                ViewHolderDefault viewHolderDefault = (ViewHolderDefault) viewHolder;
                configureViewHolderDefault(viewHolderDefault, position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolderDefault(ViewHolderDefault viewHolder, int position) {
        Expense expense = expenses.get(position);

        viewHolder.amountTextView.setText(String.valueOf(expense.getAmount()));
        viewHolder.noteTextView.setText(String.valueOf(expense.getNote()));
        viewHolder.itemView.setOnClickListener(v -> {
            ExpenseDetailActivity.newInstance(context, expenses.get(position).getId());
            // todo: create animation
        });
    }

    public void clear() {
        expenses.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Expense> expenses) {
        this.expenses.addAll(expenses);
        notifyDataSetChanged();
    }

    public static class ViewHolderDefault extends RecyclerView.ViewHolder {
        @BindView(R.id.expense_item_default_amount_text_view_id) TextView amountTextView;
        @BindView(R.id.expense_item_default_note_text_view_id) TextView noteTextView;

        private View itemView;

        public ViewHolderDefault(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }
}
