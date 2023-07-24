package input;

import model.Coordinates;
import model.Country;
import model.Difficulty;
import model.EyeColor;
import model.HairColor;
import model.LabWork;
import model.Location;
import model.Person;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class InputReader {
    private Scanner scanner;
    private InputMode inputMode;

    public InputReader(Scanner scanner, InputMode inputMode) {
        this.scanner = scanner;
        this.inputMode = inputMode;
    }

    public Double getDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public Integer getInteger() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public Float getFloat() {
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public Long getLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public String receiveName() {
        String name = "";
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter a name of lab work:");
            }
            name = scanner.nextLine();
            if (inputMode == InputMode.MANUAL && (name == null || name.isEmpty())) {
                System.out.println("Entered name was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return name;
    }

    public double receiveX() {
        Double x = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter x-coordinate. It should be greater than -112.");
            }
            x = getDouble();
            if (inputMode == InputMode.MANUAL && (x == null || x <= -112)) {
                System.out.println("Entered x-coordinate was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return x;
    }

    public long receiveY() {
        Long y = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter y-coordinate.");
            }
            y = getLong();
            if (inputMode == InputMode.MANUAL && y == null) {
                System.out.println("Entered y-coordinate was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return y;
    }

    public Coordinates receiveCoordinates() {
        return new Coordinates(receiveX(), receiveY());
    }

    public Float receiveXLocation() {
        Float x = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter x-location.");
            }
            x = getFloat();
            if (inputMode == InputMode.MANUAL && x == null) {
                System.out.println("Entered x-location was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return x;
    }

    public long receiveYLocation() {
        Long y = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter y-location.");
            }
            y = getLong();
            if (inputMode == InputMode.MANUAL && y == null) {
                System.out.println("Entered y-location was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return y;
    }

    public Integer receiveZLocation() {
        Integer z = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter z-location.");
            }
            z = getInteger();
            if (inputMode == InputMode.MANUAL && z == null) {
                System.out.println("Entered z-location was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return z;
    }

    public Location receiveLocation() {
        return new Location(receiveXLocation(), receiveYLocation(), receiveZLocation());
    }

    public EyeColor receiveEyeColor() {
        while (true) {
            try {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("Choose variant of eye color. Enter color or the number corresponding" +
                            " to the desired option.");
                    System.out.print("Variants: \n1. BLUE; \n2. YELLOW; \n3. ORANGE; \n4. WHITE." +
                            " \nEnter your variant here: ");
                }
                String eyeChoose = scanner.nextLine().toUpperCase(Locale.ROOT);
                switch (eyeChoose) {
                    case "1":
                    case "BLUE":
                        return EyeColor.BLUE;
                    case "2":
                    case "YELLOW":
                        return EyeColor.YELLOW;
                    case "3":
                    case "ORANGE":
                        return EyeColor.ORANGE;
                    case "4":
                    case "WHITE":
                        return EyeColor.WHITE;
                    default:
                        if (inputMode == InputMode.MANUAL) {
                            System.out.println("You should to choose the color from list or its number. Try again.");
                            continue;
                        }
                        break;
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("You entered incorrect eye color. Choose the value from list.");
                }
            }
        }
    }

    public HairColor receiveHairColor() {
        while (true) {
            try {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("Choose variant of hair color. Enter color or the number corresponding" +
                            " to the desired option.");
                    System.out.print("Variants: \n1. GREEN; \n2. BLUE; \n3. YELLOW; \n4. ORANGE; \n5. WHITE." +
                            " \nEnter your variant here: ");
                }
                String hairChoose = scanner.nextLine().toUpperCase(Locale.ROOT);
                switch (hairChoose) {
                    case "1":
                    case "GREEN":
                        return HairColor.GREEN;
                    case "2":
                    case "BLUE":
                        return HairColor.BLUE;
                    case "3":
                    case "YELLOW":
                        return HairColor.YELLOW;
                    case "4":
                    case "ORANGE":
                        return HairColor.ORANGE;
                    case "5":
                    case "WHITE":
                        return HairColor.WHITE;
                    default:
                        if (inputMode == InputMode.MANUAL) {
                            System.out.println("You should to choose the color from list or its number. Try again.");
                            continue;
                        }
                        break;
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("You entered incorrect difficulty. Choose the value from list.");
                }
            }
        }
    }

    public double receiveMinimalPoint() {
        Double minimalPoint = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter a minimal point coordinate.");
            }
            minimalPoint = getDouble();
            if (inputMode == InputMode.MANUAL && minimalPoint == null) {
                System.out.println("Entered minimal point coordinate was empty or invalid. Please try again.");
                continue;
            }
            break;
        }
        return minimalPoint;
    }

    public Difficulty receiveDifficulty() {
        String difficultyChoose = null;
        while (true) {
            try {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("Choose variant of difficulty. Enter model.Difficulty or the number corresponding" +
                            " to the desired option.");
                    System.out.print("Variants: \n1. EASY; \n2. NORMAL; \n3. IMPOSSIBLE; \n4. INSANE." +
                            " \nEnter your variant here: ");
                }
                difficultyChoose = scanner.nextLine().toUpperCase();
                switch (difficultyChoose) {
                    case "1":
                    case "EASY":
                        return Difficulty.EASY;
                    case "2":
                    case "NORMAL":
                        return Difficulty.NORMAL;
                    case "3":
                    case "IMPOSSIBLE":
                        return Difficulty.IMPOSSIBLE;
                    case "4":
                    case "INSANE":
                        return Difficulty.INSANE;
                    default:
                        if (inputMode == InputMode.MANUAL) {
                            System.out.println("You should to choose the color from list or it's number. Try again.");
                            continue;
                        }
                        break;
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                if (inputMode == InputMode.MANUAL) {
                    System.out.println("You entered incorrect difficulty. Choose the value from list.");
                }
            }
        }
    }

    public Country receiveNationality() {
        while (true) {
            try {
                System.out.println("Choose variant of nationality. Enter model.Country or the number corresponding " +
                        "to the desired option.");
                System.out.print("Variants: \n1. UNITED_KINGDOM; \n2. FRANCE; \n3. INDIA; \n4. SOUTH_KOREA." +
                        " \nEnter your variant here: ");
                String nationalityChoose = scanner.nextLine().toUpperCase(Locale.ROOT);
                switch (nationalityChoose) {
                    case "1":
                    case "UNITED_KINGDOM":
                        return Country.UNITED_KINGDOM;
                    case "2":
                    case "FRANCE":
                        return Country.FRANCE;
                    case "3":
                    case "INDIA":
                        return Country.INDIA;
                    case "4":
                    case "SOUTH_KOREA":
                        return Country.SOUTH_KOREA;
                    default:
                        System.out.println("You should to choose the color from list or its number. Try again.");
                        break;
                }
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("You entered incorrect difficulty. Choose the value from list.");
            }
        }
    }

    public String receiveAuthorName() {
        String author = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter author name.");
            }
            author = scanner.nextLine();
            if (inputMode == InputMode.MANUAL && (author == null || author.isEmpty())) {
                System.out.println("Entered author name was empty. Please try again.");
            }
            break;
        }
        return author;
    }

    public String receivePassportId() {
        String passportId = null;
        while (true) {
            if (inputMode == InputMode.MANUAL) {
                System.out.println("Enter passport id.");
            }
            passportId = scanner.nextLine();
            if (inputMode == InputMode.MANUAL && (passportId == null || passportId.isEmpty())) {
                System.out.println("Entered author name was empty. Please try again.");
                continue;
            }
            break;
        }
        return passportId;
    }

    public Person receiveAuthor() {
        return new Person(receiveAuthorName(), receivePassportId(), receiveEyeColor(), receiveHairColor(),
                receiveNationality(), receiveLocation());
    }

    public LabWork receiveLabWork() {
        return new LabWork.LabWorkBuilder()
                .name(receiveName())
                .coordinates(receiveCoordinates())
                .creationDate()
                .minimalPoint(receiveMinimalPoint())
                .difficulty(receiveDifficulty())
                .author(receiveAuthor())
                .build();
    }
}
