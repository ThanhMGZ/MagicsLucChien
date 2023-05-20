package net.thanhmgz.magicslucchien.gui;

public enum GUIType {

    DAILY(1),
    TOP_KILLS(2),
    TOP_DOUBLE_KILLS(3),
    TOP_TRIPLE_KILLS(4),
    TOP_POINTS(5);

    private final int i;

    GUIType(int i) {
        this.i = i;
    }

    public int getID() {
        return i;
    }
}
