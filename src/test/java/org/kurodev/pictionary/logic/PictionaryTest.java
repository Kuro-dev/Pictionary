package org.kurodev.pictionary.logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class PictionaryTest {

    @Test
    public void submit() {
        Pictionary pic = new Pictionary("Hello World");
        pic.submit("ASRLIHAOIRAR");
        assertFalse(pic.isWon());
        pic.submit("Hello World");
        assertTrue(pic.isWon());
        pic.submit("ASRLIHAOIRAR");
        assertTrue(pic.isWon());
    }

    @Test
    public void getWordHidden() {
        Pictionary pic = new Pictionary("Hello World");
        assertEquals("_____ _____", pic.getWordHidden());
        assertEquals(pic.getWord().length(), pic.getWordHidden().length());
    }

    @Test
    public void getHint() {
        Pictionary pic = new Pictionary("Hello World");
        for (int i = 0; i < 10; i++) {
            assertEquals(pic.getWord().length(), pic.getHint().getHint().length());
//            System.out.println(pic.getHint().getHint());
        }
    }
}