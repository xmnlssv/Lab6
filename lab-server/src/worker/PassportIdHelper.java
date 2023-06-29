package worker;

import model.LabWork;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PassportIdHelper {
    private static final Set<String> passportIds = new LinkedHashSet<>();

    public static void initialize(List<LabWork> labWorkList) {
        for (LabWork labWork : labWorkList) {
            passportIds.add(labWork.getAuthor().getPassportId());
        }
    }

    private PassportIdHelper() {}

    public static void saveId(String passportId) {
        passportIds.add(passportId);
    }

    public static boolean containsId(String passportId) {
        return passportIds.contains(passportId);
    }
}
