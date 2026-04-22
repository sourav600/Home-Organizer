package com.misourav.homeorganizer.domain.model;

public enum RoleCode {
    HOUSEHOLDER(4),
    HOUSEMAKER(3),
    MEMBER(2),
    CHILD(1);

    private final int level;

    RoleCode(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }

    public boolean outranks(RoleCode other) {
        return this.level > other.level;
    }
}
