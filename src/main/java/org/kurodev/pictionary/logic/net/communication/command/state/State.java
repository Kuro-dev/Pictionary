package org.kurodev.pictionary.logic.net.communication.command.state;

/**
 * @author kuro
 **/
public enum State {
    WAITING_FOR_PLAYERS,
    INITIALIZING,
    READY,
    ;

    public static State get(int i) {
        for (State e : State.values()) {
            if (e.ordinal() == i) {
                return e;
            }
        }
        throw new IllegalArgumentException("unknown event " + i);
    }
}
