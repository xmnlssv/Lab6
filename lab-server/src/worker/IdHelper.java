package worker;

import model.LabWork;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class IdHelper {
    private static final Set<Integer> IDS = new LinkedHashSet<>();
    public static final Random random = new Random();
    public static final int UPPER_BOUND = 9999999;

    private IdHelper() {}

    public static void initialize(List<LabWork> labWorkList) {
        for (LabWork labWork : labWorkList) {
            IDS.add(labWork.getId());
        }
    }

    public static void saveId(Integer id) {
        if (id != null) {
            IDS.add(id);
        }
    }

    public static void removeId(Integer id) {
        if (id != null) {
            IDS.remove(id);
        }
    }

    public static void removeIds(List<Integer> ids) {
        IDS.removeAll(ids.stream().filter(Objects::nonNull).toList());
    }

    public static void removeAll() {
        IDS.clear();
    }

    public static boolean containsId(Integer id) {
        return id != null && IDS.contains(id);
    }

    public static Integer generateId() {
        return random.nextInt(UPPER_BOUND);
    }
}
