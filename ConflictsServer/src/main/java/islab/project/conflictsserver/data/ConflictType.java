package islab.project.conflictsserver.data;

import java.util.Arrays;

public enum ConflictType {
    EXTRASYSTEMIC(1),
    INTERSTATE(2),
    INTERNAL(3),
    INTERNATIONALIZED(4);

    private final int id;

    ConflictType(int id) {
        this.id = id;
    }

    public static ConflictType getById(int id) {
        return Arrays.stream(values()).filter(x -> x.id==id).findFirst().orElseThrow();
    }
}
