package com.example.ddaveragecalculator;

public class Save {
    protected Damage mDmg;
    int mDC;
    int mSaveMod;
    public Advantage mAdv;

    public enum Advantage {
        ADVANTAGE,
        DISADVANTAGE,
        STRAIGHT
    }

    public Save() {
        this(new Damage(), 15, 1, Advantage.STRAIGHT);
    }

    public Save(Damage dmg, int dc, int saveMod, Advantage adv) {
        mDmg = dmg;
        mDC = dc;
        mSaveMod = saveMod;
        mAdv = adv;
    }

    public Double getAvgSaveChance() {
        double hitChance = (mDC - mSaveMod); // calculate chance to hit
        if (hitChance < 0) { // ensure it's not negative
            hitChance = 0;
        }
        hitChance /= 20; // convert to percentage

        switch (mAdv) {
            case ADVANTAGE:
                hitChance *= hitChance; // apply advantage/disadvantage effects
                break;
            case DISADVANTAGE:
                hitChance = CalcAdvHit(hitChance * 20);
                break;
        }

        return hitChance * 19 / 20;
    }

    public Double getAvgSaveDmg(){
        return Math.round((mDmg.CalcAvgDmg(false) * getAvgSaveChance())*100.00) / 100.00;
    }

    public double CalcAdvHit(double hit) {//hit = # of sides on die that result in the attack landing
        double chance = 0.00;
        for (int i = 0; i < hit; i++) {//go threw loop once for each side of die that hits
            chance += (39.00 - i * 2) / 400.00;//its right. i checked. three times.
        }
        return chance;
    }
}