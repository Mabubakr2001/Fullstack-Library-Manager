package dev.bakr.library_manager.helpers;

import dev.bakr.library_manager.enums.BookStatus;

public class BookStatusValidator {
    public static boolean validateStatus(String bookStatus) {
        boolean validStatus = false;
        // loop over the enum values (constants) and compare each value with the give book status
        for (int i = 0; i < BookStatus.values().length; i++) {
            var statusConstant = BookStatus.values()[i];
            if (statusConstant.name().equalsIgnoreCase(bookStatus)) {
                validStatus = true;
                break;
            }
        }
        return validStatus;
    }
}
