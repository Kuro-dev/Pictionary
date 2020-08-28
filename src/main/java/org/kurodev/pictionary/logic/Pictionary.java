package org.kurodev.pictionary.logic;

import org.kurodev.pictionary.logic.net.communication.command.tokens.HintToken;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author kuro
 **/
public class Pictionary implements Encodable {
    private static final String HIDDEN = "_";
    private final List<Integer> hintedLetters = new ArrayList<>();
    private int timerTime;
    private String word;
    private boolean won = false;

    public Pictionary(String word, int time) {
        this.word = word;
        timerTime = time;
    }

    public Pictionary(int time) {
        this("", time);
    }

    public Pictionary() {
        this("");
    }

    public Pictionary(String word) {
        this(word, 90);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pictionary that = (Pictionary) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return "Pictionary{" +
                "word='" + word + '\'' +
                ", timerTime=" + timerTime +
                '}';
    }

    @Override
    public void decode(EasyByteReader data) {
        word = data.readString();
        timerTime = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(word);
        out.write(timerTime);
    }

    public boolean isWon() {
        return won;
    }

    public void submit(String solution) {
        if (!won)
            won = word.equalsIgnoreCase(solution);
    }

    public String getWord() {
        return word;
    }

    public String getWordHidden() {
        return HIDDEN.repeat(word.length());
    }

    public HintToken getHint() {
        StringBuilder hint = new StringBuilder(word.length());
        boolean notHinted = true;
        if (hintedLetters.size() < word.length() / 2) {
            int a = getNewHint();
            hintedLetters.add(a);
        }
        for (int i = 0; i < word.length(); i++) {
            for (Integer integer : hintedLetters) {
                if (i == integer) {
                    hint.append(word.charAt(integer));
                    notHinted = false;
                    break;
                } else {
                    notHinted = true;
                }
            }
            if (notHinted)
                hint.append(HIDDEN);
        }
        return new HintToken(hint.toString());
    }

    private int getNewHint() {
        int a = new Random().nextInt(word.length());
        if (hintedLetters.stream().anyMatch(integer -> integer == a)) {
            return getNewHint();
        }
        return a;
    }
}
