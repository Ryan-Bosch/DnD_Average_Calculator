package com.example.ddaveragecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DamageActivity extends AppCompatActivity {

    TextView tvDmgMod, tvD4, tvD6, tvD8, tvD10, tvD12;
    EditText ptDmgMod, ptD4, ptD6, ptD8, ptD10, ptD12, ptExtent;
    Damage damage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage);

        setup();

        Intent i = getIntent();
        if(i.hasExtra("damage")){
            damage = (Damage) i.getSerializableExtra("damage");
            assert damage != null;
            ptD4.setText(damage.mD4);
            ptD6.setText(damage.mD6);
            ptD8.setText(damage.mD8);
            ptD10.setText(damage.mD10);
            ptD12.setText(damage.mD12);
            ptDmgMod.setText(String.format("%s", damage.mDmgMod));
            ptExtent.setText(String.format("%s", damage.mExtent));
        }
    }

    private void setup() {

        ptDmgMod = findViewById(R.id.ptDmgMod);
        ptD4 = findViewById(R.id.ptD4);
        ptD6 = findViewById(R.id.ptD6);
        ptD8 = findViewById(R.id.ptD8);
        ptD10 = findViewById(R.id.ptD10);
        ptD12 = findViewById(R.id.ptD12);
        ptExtent = findViewById(R.id.ptExtent);


        tvDmgMod = findViewById(R.id.tvDmgMod);
        tvD4 = findViewById(R.id.tvD4);
        tvD6 = findViewById(R.id.tvD6);
        tvD8 = findViewById(R.id.tvD8);
        tvD10 = findViewById(R.id.tvD10);
        tvD12 = findViewById(R.id.tvD12);

        damage = new Damage();
    }
    
    public void btnSaveDmgClick(View V) {
        damage.mDmgMod = Integer.parseInt(String.valueOf(ptDmgMod.getText()));
        damage.mD4 = Integer.parseInt(String.valueOf(ptD4.getText()));
        damage.mD6 = Integer.parseInt(String.valueOf(ptD6.getText()));
        damage.mD8 = Integer.parseInt(String.valueOf(ptD8.getText()));
        damage.mD10 = Integer.parseInt(String.valueOf(ptD10.getText()));
        damage.mD12 = Integer.parseInt(String.valueOf(ptD12.getText()));

        damage.mExtent = Double.parseDouble(String.valueOf(ptExtent.getText()));

        // Pass the damage object back to MainActivity as result
        Intent resultIntent = new Intent();
        resultIntent.putExtra("damage", damage);
        setResult(RESULT_OK, resultIntent);
        finish(); // Close this activity
    }
}