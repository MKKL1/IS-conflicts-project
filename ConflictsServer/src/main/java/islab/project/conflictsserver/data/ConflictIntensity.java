package islab.project.conflictsserver.data;

import java.util.Arrays;

public enum ConflictIntensity {
    MINOR(1),
    WAR(2);

    private final int id;

    ConflictIntensity(int id) {
        this.id = id;
    }

    public static ConflictIntensity getById(int id) {
        return Arrays.stream(values()).filter(x -> x.id==id).findFirst().orElseThrow();
    }
}
