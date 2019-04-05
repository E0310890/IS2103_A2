package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Lend;

public class Helper {

    public static String dateToFormattedDateString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static void displayLending(List<Lend> lendList) {

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
