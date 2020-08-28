package org.kurodev.pictionary.logic.net.communication.command.tokens.event;

/**
 * @author kuro
 **/
public enum Event {
    GAME_START,
    GAME_END,
    ;

    public static Event get(int i) {
        for (Event e : Event.values()) {
            if (e.ordinal() == i) {
                return e;
            }
        }
        throw new IllegalArgumentException("unknown event " + i);
    }
}
