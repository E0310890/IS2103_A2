package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Fine;
import model.Lend;
import model.Reservation;

public class Helper {

    public static String dateToFormattedDateString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static void displayFine(List<Fine> fineList) {
        if (fineList.isEmpty()) {
            return;
        }

        System.out.println("Id | Amount");
        String[] idSpacing = new String[]{" ", "  "};

        for (Fine f : fineList) {
            String fineId = f.getLendID().toString();
            if (f.getLendID() < 10) {
                fineId += idSpacing[1];
            } else if (f.getLendID() < 100) {
                fineId += idSpacing[0];
            }
            System.out.println(
                    fineId + "| $"
                    + f.getFineAmount());
        }

    }

    public static void displayReservation(List<Reservation> resList) {
        if (resList.isEmpty()) {
            return;
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        int maxLength = resList.stream()
                .mapToInt(r -> r.getBook().getTitle().length())
                .max().getAsInt();
        String spacing = "";
        for (int i = 0; i < maxLength; i++) {
            spacing += " ";
        }

        System.out.println("Id | Book"
                + spacing.substring(3)
                + "| Member | Reserve Date ");

        String[] idSpacing = new String[]{" ", "  "};

        for (Reservation r : resList) {
            String resId = r.getReservationID().toString();
            if (r.getReservationID() < 10) {
                resId += idSpacing[1];
            } else if (r.getReservationID() < 100) {
                resId += idSpacing[0];
            }
            int to = (spacing.length() - r.getBook().getTitle().length()) + 2;
            System.out.println(
                    resId + "| "
                    + r.getBook().getTitle()
                    + (spacing.substring(1, to)) + "| "
                    + r.getMember().getFirstName() + " " + r.getMember().getLastName() + " | "
                    + simpleDateFormat.format(r.getReserveDate())
            );
        }

    }

    public static void displayLending(List<Lend> lendList) {

        if (lendList.isEmpty()) {
            return;
        }

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        int maxLength = lendList.stream()
                .mapToInt(l -> l.getBook().getTitle().length())
                .max().getAsInt();
        String spacing = "";
        for (int i = 0; i < maxLength; i++) {
            spacing += " ";
        }

        String[] idSpacing = new String[]{" ", "  "};

        System.out.println("Currently Lent Books:");
        System.out.println("Id |Title"
                + spacing.substring(3)
                + "| Due Date");

        for (Lend l : lendList) {
            String lendId = l.getLendID().toString();
            if (l.getLendID() < 10) {
                lendId += idSpacing[1];
            } else if (l.getLendID() < 100) {
                lendId += idSpacing[0];
            }
            int to = (spacing.length() - l.getBook().getTitle().length()) + 2;
            System.out.println(
                    lendId + "|"
                    + l.getBook().getTitle()
                    + (spacing.substring(0, to)) + "| "
                    + simpleDateFormat.format(l.getDueDate())
            );
        }
    }
}
