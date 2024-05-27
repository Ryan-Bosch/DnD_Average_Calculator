package com.example.ddaveragecalculator;

import java.util.ArrayList;

public class Action {
    private ArrayList<Attack> mAttacks;
    private ArrayList<Save> mSaves;

    protected Action() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public Action(ArrayList<Attack> attacks, ArrayList<Save> saves) {
        mAttacks = attacks;
        mSaves = saves;
    }

    // getters and setters for Attack list

    public void addAttack(Attack attack) {
        mAttacks.add(attack);
    }

    public void removeAttack(int index) {
        if (index >= 0 && index < mAttacks.size()) {
            mAttacks.remove(index);
        }
    }

    public Attack getAttack(int index) {
        return mAttacks.get(index);
    }

    public ArrayList<Attack> getAllAttacks() {
        return mAttacks;
    }

    public void clearAllAttacks() {
        mAttacks.clear();
    }

    // getters and setters for Save list

    public void addSave(Save save) {
        mSaves.add(save);
    }

    public void removeSave(int index) {
        if (index >= 0 && index < mSaves.size()) {
            mSaves.remove(index);
        }
    }

    public Save getSave(int index) {
        return mSaves.get(index);
    }

    public ArrayList<Save> getAllSaves() {
        return mSaves;
    }

    public void clearAllSaves() {
        mSaves.clear();
    }
}
