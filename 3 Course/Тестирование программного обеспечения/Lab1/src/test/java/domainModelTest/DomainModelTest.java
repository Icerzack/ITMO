package domainModelTest;

import domainModel.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainModelTest {

    Girl girl = new Girl("Kate Johnson", 25, Gender.FEMALE);
    Stranger stranger = new Stranger("Dim Dimich", 14, Gender.MALE);
    Bar bar = new Bar("The Golden Owl", "123 Main Street");

    @Test
    public void testGirlInitialization() {
        assertEquals("Kate Johnson", girl.getName());
        assertEquals(25, girl.getAge());
        assertEquals(Gender.FEMALE, girl.getGender());
    }

    @Test
    public void testStrangerInitialization() {
        assertEquals("Dim Dimich", stranger.getName());
        assertEquals(14, stranger.getAge());
        assertEquals(Gender.MALE, stranger.getGender());
    }

    @Test
    public void testBarInitialization() {
        assertEquals("The Golden Owl", bar.getName());
        assertEquals("123 Main Street", bar.getLocation());
    }


    @Test
    public void testStrangerBringGirl() {
        stranger.bringGirl(girl);
        assertEquals(girl, stranger.getGirl());
    }

    @Test
    public void testStrangerLaughLoudly() {
        stranger.laughLoudly();
        assertTrue(stranger.isLaughing());
    }

    @Test
    public void testGirlHatesGuy() {
        girl.hatesGuy(stranger);
        assertTrue(girl.isHatingSomeone());
    }

    @Test
    public void testStrangerTransformToCloud(){
        Cloud cloud = stranger.transformToCloud();
        assertEquals(cloud.getFromWhom(), stranger);
    }

}

