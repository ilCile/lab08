package it.unibo.deathnote.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private final List<String> deadPeople = new ArrayList<>();
    private final Map<String, String> deathCause = new HashMap<>();
    private final Map<String, String> deathDetails = new HashMap<>();

    private long time;
    private long causeTime;
    private String lastPerson;

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber <= 0 || ruleNumber > DeathNote.RULES.size() - 1){
            throw new IllegalArgumentException("Rule number invalid.");
        }
        return DeathNote.RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if(name == null){
            throw new NullPointerException("The argument is null");
        }
        if(!this.isNameWritten(name)){
            this.deadPeople.add(name);
            this.deathCause.put(name, "heart attack");
            this.deathDetails.put(name, "");
            this.lastPerson = name;
            time = System.nanoTime();
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(this.deadPeople.isEmpty() || cause == null){
            throw new IllegalStateException("The cause is null or there is no name written.");
        }
        causeTime = System.nanoTime() - this.time;
        causeTime = TimeUnit.NANOSECONDS.toMillis(causeTime);
        if(causeTime > 40){
            return false;
        }
        this.causeTime = System.nanoTime();
        this.deathCause.replace(lastPerson, cause);
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        if(this.deadPeople.isEmpty() || details == null){
            throw new IllegalStateException("The details are null or there is no name written.");
        }
        long detailsTime = System.nanoTime() - this.causeTime;
        detailsTime = TimeUnit.NANOSECONDS.toMillis(detailsTime);
        if(detailsTime > 6040){
            return false;
        }
        this.deathCause.replace(lastPerson, details);
        return true;
    }

    @Override
    public String getDeathCause(String name) {
        if(!this.isNameWritten(name)){
            throw new IllegalArgumentException("This name is not written.");
        }
        return this.deathCause.get(name);
    }

    @Override
    public String getDeathDetails(String name) {
        if(!this.isNameWritten(name)){
            throw new IllegalArgumentException("This name is not written.");
        }
        return this.deathDetails.get(name);
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.deadPeople.contains(name);
    }
    
}