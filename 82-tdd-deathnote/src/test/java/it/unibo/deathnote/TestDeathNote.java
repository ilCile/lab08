package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static java.lang.Thread.sleep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {

    private DeathNote deathNote;
    private final String PERSON_NAME = "Giovanni";
    private final String PERSON_NAME1 = "Francesco";
    private final String DEFAULT_DEATH_CAUSE = "heart attack";
    private final String ANOTHER_DEATH_CAUSE = "karting accident";
    private final String DEATH_DETAILS = "ran for too long";

    @BeforeEach
    public void setUp(){
        this.deathNote = new DeathNoteImplementation(); 
    }

    @Test
    public void testGetRule(){
        try{
            deathNote.getRule(0);
            deathNote.getRule(-1);
            fail("It was supposed to throw an exception");
        }catch(IllegalArgumentException e){
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    public void testRuleEmpty(){
        for(int i = 0; i < DeathNote.RULES.size(); i++){  
            assertNotNull(deathNote.getRule(i));
            assertFalse(deathNote.getRule(i).isBlank());
        }
    }

    @Test
    public void testName(){
        assertFalse(deathNote.isNameWritten(PERSON_NAME));
        deathNote.writeName(PERSON_NAME);
        assertTrue(deathNote.isNameWritten(PERSON_NAME));
        assertFalse(deathNote.isNameWritten(PERSON_NAME1));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    public void testDeathCause(){
        try{
            deathNote.writeDeathCause(ANOTHER_DEATH_CAUSE);
            deathNote.writeName(PERSON_NAME);
            assertEquals(DEFAULT_DEATH_CAUSE, deathNote.getDeathCause(PERSON_NAME));
            deathNote.writeName(PERSON_NAME1);
            assertTrue(deathNote.writeDeathCause(ANOTHER_DEATH_CAUSE));
            assertEquals(ANOTHER_DEATH_CAUSE, deathNote.getDeathCause(PERSON_NAME1));
            sleep(100);
            deathNote.writeDeathCause(DEFAULT_DEATH_CAUSE);
            assertEquals(ANOTHER_DEATH_CAUSE, deathNote.getDeathCause(PERSON_NAME1));

        }catch(IllegalStateException e){
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
            assertFalse(e.getMessage().isBlank());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeathDetails(){
        try{
            deathNote.writeDetails(DEATH_DETAILS);
            deathNote.writeName(PERSON_NAME);
            assertEquals("", deathNote.getDeathDetails(PERSON_NAME));
            assertTrue(deathNote.writeDetails(DEATH_DETAILS));
            assertEquals(DEATH_DETAILS, deathNote.getDeathDetails(PERSON_NAME));
            deathNote.writeName(PERSON_NAME1);
            sleep(6100);
            deathNote.writeDetails("");
            assertEquals(DEATH_DETAILS, deathNote.getDeathDetails(PERSON_NAME));
            
        }catch(IllegalStateException e){
            assertNotNull(e);
            assertFalse(e.getMessage().isEmpty());
            assertFalse(e.getMessage().isBlank());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}