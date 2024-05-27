package com.example.ddaveragecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mActions = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ActionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        mRecyclerView = findViewById(R.id.actions_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Add default "Add action" button to list
        mActions.add(new ActionButton());

        // Create adapter and attach it to RecyclerView
        mAdapter = new CustomAdapter(mActions);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void onNewAttackClick(View view) {
        Intent intent = new Intent(MainActivity.this, AttackActivity.class);
        startActivity(intent);
    }

    /** @noinspection InnerClassMayBeStatic*/
    public class ActionHolder extends RecyclerView.ViewHolder {
        private TextView mActionName;

        public ActionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.action_view_item, parent, false));

            // Initialize views...
            mActionName = (TextView)itemView.findViewById(R.id.action_name);
        }

        public void bind(Action action) {
            String name = "Attacks: ";
            for(Attack attack : action.getAllAttacks()) {
                name += attack.getName() + ", ";
            }
            if(action.getAllSaves().size() > 0) {
                name += "\n\nSaves: ";
                for(Save save : action.getAllSaves()) {
                    name += save.getName() + ", ";
                }
            }

            mActionName.setText(name);
        }
    }

    public class ActionsAdapter extends RecyclerView.Adapter<ActionHolder> {
        private ArrayList<Action> actions;

        public ActionsAdapter(ArrayList<Action> actions) {
            this.actions = actions;
        }

        @Override
        public int getItemCount() {
            return actions.size();
        }

        @NonNull
        @Override
        public ActionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            return new ActionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ActionHolder holder, int position) {
            holder.bind(actions.get(position));
        }
    }

    public class NestedRecyclerViewAdapter extends RecyclerView.Adapter {
        private List actions;

        public NestedRecyclerViewAdapter(List actions) {
            this.actions = actions;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_recycler_view_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Action holder, int position) {
            // Get the current action for this row
            Action action = (Action) actions.get(position);

            // Set up the inner recyclerview adapter with attacks first, then saves.
            InnerRecyclerViewAdapter innerAdapter = new InnerRecyclerViewAdapter(action.getAllAttacks(), action.getAllSaves());

            // Attach the inner recyclerView to our viewHolder.
            holder.innerRecyclerView.setAdapter(innerAdapter);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            RecyclerView innerRecyclerView;

            ViewHolder(View itemView) {
                super(itemView);
                innerRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_inner);

                // If needed: setup your layoutManager here...
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
                innerRecyclerView.setLayoutManager(linearLayoutManager);
            }
        }
    }

    public class InnerRecyclerViewAdapter extends RecyclerView.Adapter {
        private List attacks;
        private List saves;

        public InnerRecyclerViewAdapter(List attacks, List saves) {
            this.attacks = attacks;
            this.saves = saves;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < attacks.size()) {
                return 0; // Attack view type
            } else {
                return 1; // Save view type
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            switch (viewType) {
                case 0: // Attack view holder
                    View attackView = layoutInflater.inflate(R.layout.attack_item_layout, parent, false);
                    return new AttackViewHolder(attackView);

                case 1: // Save view holder
                    View saveView = layoutInflater.inflate(R.layout.save_item_layout, parent, false);
                    return new SaveViewHolder(saveView);

                default:
                    throw new IllegalArgumentException("Invalid view type");
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            if (getItemViewType(position) == 0) {
                ((AttackViewHolder)viewHolder).bind(attacks.get(position));
            } else {
                ((SaveViewHolder)viewHolder).bind(saves.get(position - attacks.size()));
            }
        }

        @Override
        public int getItemCount() {
            return attacks.size() + saves.size();
        }
        public class InnerRecyclerViewAdapter extends RecyclerView.Adapter {
            private List attacks;
            private List saves;

            public InnerRecyclerViewAdapter(List attacks, List saves) {
                this.attacks = attacks;
                this.saves = saves;
            }

            @Override
            public int getItemViewType(int position) {
                if (position < attacks.size()) {
                    return 0; // Attack view type
                } else {
                    return 1; // Save view type
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                switch (viewType) {
                    case 0: // Attack view holder
                        View attackView = layoutInflater.inflate(R.layout.attack_item_layout, parent, false);
                        return new AttackViewHolder(attackView);

                    case 1: // Save view holder
                        View saveView = layoutInflater.inflate(R.layout.save_item_layout, parent, false);
                        return new SaveViewHolder(saveView);

                    default:
                        throw new IllegalArgumentException("Invalid view type");
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
                if (getItemViewType(position) == 0) {
                    ((AttackViewHolder)viewHolder).bind(attacks.get(position));
                } else {
                    ((SaveViewHolder)viewHolder).bind(saves.get(position - attacks.size()));
                }
            }

            @Override
            public int getItemCount() {
                return attacks.size() + saves.size();
            }

    }