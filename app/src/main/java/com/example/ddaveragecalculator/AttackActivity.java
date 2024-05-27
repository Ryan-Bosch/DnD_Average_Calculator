package com.example.ddaveragecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class AttackActivity extends AppCompatActivity {

    Button btnSubmitAtt, btnAddDmg;
    RadioButton rbAdv, rbDis;
    EditText ptAC, ptHitMod, ptCrit, ptMisfire;
    TextView tvHitMod, tvCrit, tvMisfire, tvAvgAttDmg, tvDmg;
    Attack attack;
    ConstraintLayout constraintLayout;
    Damage mDmg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        setup();
    }

    public void setup() {
        constraintLayout = findViewById(R.id.parent);

        btnSubmitAtt = findViewById(R.id.btnCalculateAtt);
        btnAddDmg = findViewById(R.id.btnAddDmg);

        rbAdv = findViewById(R.id.rbAdvantage);
        rbDis = findViewById(R.id.rbDisadvantage);

        ptAC = findViewById(R.id.ptAC);

        ptHitMod = findViewById(R.id.ptHitMod);
        ptCrit = findViewById(R.id.ptCrit);
        ptMisfire = findViewById(R.id.ptMisfire);

        tvHitMod = findViewById(R.id.tvHitMod);
        tvCrit = findViewById(R.id.tvCrit);
        tvMisfire = findViewById(R.id.tvMisfire);

        tvAvgAttDmg = findViewById(R.id.tvAvgAttDmg);
        attack = new Attack();
    }

    public void btnSubmitAttClick(View v) {
        attack.mHitMod = Integer.parseInt(String.valueOf(ptHitMod.getText()));
        attack.mCrit = Integer.parseInt(String.valueOf(ptCrit.getText()));
        attack.mMis = Integer.parseInt(String.valueOf(ptMisfire.getText()));
        attack.mAC = Integer.parseInt(String.valueOf(ptAC.getText()));

        if (rbAdv.isChecked()) {
            attack.mAdv = Attack.Advantage.ADVANTAGE;
        } else if (rbDis.isChecked()) {
            attack.mAdv = Attack.Advantage.DISADVANTAGE;
        } else { attack.mAdv = Attack.Advantage.STRAIGHT; }

        attack.mDmg = mDmg != null ? mDmg : new Damage();
        tvAvgAttDmg.setText(String.format(attack.getAvgAttDmg().toString()));
        tvAvgAttDmg.setVisibility(View.VISIBLE);
    }

    public void btnAddDmgClick(View v) {
        Intent intent = new Intent(AttackActivity.this, DamageActivity.class);
        if (mDmg != null) {
            // If mDmg is not null, add it as a parcelable extra.
            intent.putExtra("damage", mDmg);
        }
        startActivityForResult(intent, 1); // Request code can be any integer value you choose.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve the passed damage object
            mDmg = Objects.requireNonNull(data.getParcelableExtra("damage"));

            // Create a new TextView for displaying damage information.
            tvDmg = new TextView(this);
            tvDmg.setText(mDmg.toString());
            tvDmg.setId(View.generateViewId()); // Set an ID so that we can reference it later.

            // If there is no previous damage text view (i.e., tvDmgs ArrayList is empty),
            // then place this one to the right of btnAddDmg. Otherwise, place it below the last damage text view.

            // Create LayoutParams for your TextView.
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);

            // Set constraints on your View programmatically:
            params.leftToLeft = R.id.rgAdvantage;
            params.rightToRight = R.id.rgAdvantage;
            params.topToTop = R.id.btnAddDmg;
            params.bottomToBottom = R.id.btnAddDmg;

            tvDmg.setLayoutParams(params);
            constraintLayout.addView(tvDmg);
            btnAddDmg.setText(R.string.edit_damage);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}