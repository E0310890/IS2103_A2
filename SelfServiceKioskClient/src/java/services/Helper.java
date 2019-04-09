package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Book;
import model.Fine;
import model.Lend;

public class Helper {

    public static String dateToFormattedDateString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static void displayBook(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("===== No book with such a title.======");
            return;
        }
        System.out.println("Search Results:");
        System.out.println("Book ID | Title | Availability ");
        bookList.forEach((bl) -> {
            System.out.println(bl.getBookID() + " | " + bl.getTitle() + " | " + bl.getStatus());
        });
        System.out.println();

    }

    public static void displayFine(List<Fine> fineList) {

        if (fineList.isEmpty()) {
            System.out.println("===== You do not have any outstanding fine.=======");
            return;
        }

        System.out.println("Unpaid Fines for Member:");
        System.out.println("Fine ID | Amount");

        for (Fine f : fineList) {
            System.out.println(f.getLendID() + "   |  $" + f.getFineAmount());
        }
        System.out.println();
    }

    public static void displayLending(List<Lend> lendList) {

        if (lendList.isEmpty()) {
            System.out.println("===== You did not lend any book.=======");
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
        System.out.println("Book ID | Title"
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
        System.out.println();
    }
}