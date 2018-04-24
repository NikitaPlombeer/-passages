package ru.sharadze;

public enum BorderType {
    TOP(0), BOTTOM(2), LEFT(1), RIGHT(3);

    private int value;

    BorderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BorderType convert(ru.sharadze.Passages.BorderType t) {
        for (BorderType borderType : BorderType.values()) {
            if(borderType.value == t.value())
                return borderType;
        }
        return null;
    }

    public static ru.sharadze.Passages.BorderType convert(BorderType borderType) {
        return ru.sharadze.Passages.BorderType.from_int(borderType.value);
    }
}