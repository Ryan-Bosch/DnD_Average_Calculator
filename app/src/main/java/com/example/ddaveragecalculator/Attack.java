package com.example.ddaveragecalculator;

import java.util.ArrayList;

public class Attack {
    protected int mHitMod;
    protected int mCrit;
    protected int mMis;
    protected int mAC;
    protected Advantage mAdv;
    protected Damage mDmg;
    private ArrayList<Save> mSaves = new ArrayList<>();

    public enum Advantage {
        ADVANTAGE,
        DISADVANTAGE,
        STRAIGHT
    }

    protected Attack() {
        this(5, 20, 0, 15, Advantage.STRAIGHT, new Damage());
    }

    protected Attack(int hitMod, int crit, int mis, int ac) {
        this(hitMod, crit, mis, ac, Advantage.STRAIGHT, new Damage());
    }

    protected Attack(int hitMod, int crit, int mis, int ac, Advantage adv) {
        this(hitMod, crit, mis, ac, adv, new Damage());
    }

    protected Attack(int hitMod, int crit, int mis, int ac, Damage dmg) {
        this(hitMod, crit, mis, ac, Advantage.STRAIGHT, dmg);
    }

    protected Attack(int hitMod, int crit, int mis, int ac, Advantage adv, Damage dmg) {
        mHitMod = hitMod;
        mCrit = crit;
        mMis = mis;
        mAC = ac;
        mAdv = adv;
        mDmg = dmg;
    }

    public Double getAvgAttChange() {
        double hitChance = (mAC - mHitMod);//set minimum dice roll to hit

        //set max dice roll to miss
        if (hitChance <= mMis) {//max dice to miss if never mis w/o misfire
            hitChance = mMis;
            if (hitChance < 1) {
                hitChance = 1;
            }
        } else if (hitChance <= 1) {//max dice to miss if never mis w/o nat 1
            hitChance = 1;
        } else {//max dice to miss otherwise
            hitChance--;
        }

        //number of dice rolls that hit over total possible dice rolls
        hitChance = (20.00 - hitChance);
        hitChance /= (20.00);

        switch (mAdv) {
            case ADVANTAGE:
                hitChance = CalcAdvHit(hitChance * 20.00);
                break;
            case DISADVANTAGE:
                hitChance *= hitChance;
                break;
        }

        return hitChance;
    }

    public Double getAvgAttDmg() {
        double hitChance = getAvgAttChange();
        double critChance = ((21.00 - mCrit) / 20.00);
        double misfireChance = (mMis / 20.00);

        switch (mAdv) {
            case ADVANTAGE:
                critChance = CalcAdvHit(critChance * 20.00);
                misfireChance *= misfireChance;
                break;
            case DISADVANTAGE:
                critChance *= critChance;
                misfireChance = CalcAdvHit(misfireChance * 20.00);
                break;
        }

        //include full crit damage chance if no dmg without crit
        if (mAC > 20 + mHitMod) {
            critChance *= 2;
        }


        double totalSaveDamage = 0.0;
        for(Save s: mSaves){
            totalSaveDamage +=s.getAvgSaveDmg();
        }

        //for each damage type (or damage object)
        double dmg = mDmg.CalcAvgDmg(true);
        double critDmg = critChance * dmg;// Crit chance of damage type
        dmg += mDmg.mDmgMod + totalSaveDamage;
        double hitDmg = hitChance * dmg;
        double misDmg = misfireChance * dmg;

        return Math.round((hitDmg + critDmg - misDmg) * mDmg.mExtent * 100.00) / 100.00;
    }

    public double CalcAdvHit(double hit) {//hit = # of sides on die that result in the attack landing
        // 20 per hit + hit per not hit (where not hit = 20-hit) / 20*20
        // 20hit + (20-hit)hit / 400
        // 20hit + 20hit - hit*hit / 400
        return (40*hit - hit*hit)/400;
    }

    public void addSave(Save save) {
        mSaves.add(save);
    }
    public void removeSave(Save save, int i) {
        mSaves.remove(i);
    }

    public Save getSave(int i) { return mSaves.get(i); }
    public ArrayList<Save> getSaves() { return mSaves; }
    public void removeAllSaves() { mSaves = new ArrayList<>(); }
}