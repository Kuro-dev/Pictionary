package org.kurodev.pictionary.overlay;

import org.junit.Test;

import static org.junit.Assert.*;

public class GUIManagerTest {

    @Test
    public void wellItsATest() {

        GUIManager.instantiate("MCXIV");
        GUIManager.addParticipant("Hey");
        GUIManager.addParticipant("May");
        GUIManager.addParticipant("ugtfdsadi");
        GUIManager.addParticipant("JGfkhgsv");
        GUIManager.addParticipant("sdugvbksjd");

        GUIManager.updateScore("May", 100);

        GUIManager.sendChat("May","Hello");
        GUIManager.sendChat("Hey","Watermelon");
        GUIManager.sendChat("May","Fantastic");
        GUIManager.sendChat("Hey","Mellow");
        GUIManager.sendChat("May","Bruh");

        GUIManager.setTime(4, 5);

    }

    public static void main(String[] args) {
        new GUIManagerTest().wellItsATest();
    }

}