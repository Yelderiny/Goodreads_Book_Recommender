import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args)
    {
        var sc = new Scanner(System.in);
        var BE = new BookExtractor();

        BE.readExcel("goodreads_library_export.xlsx");

        int choice;
        System.out.println("Shall I choose (1) randomly or (2) based on the book's average rating?");
        choice = sc.nextInt();

        if (choice == 2) getRecommendations(BE.getRecommendationList()).forEach(System.out::println);
        else if (choice == 1) getRandomRecommendtaions(BE.getRecommendationList()).forEach(System.out::println);

    }

    /**
     * Picks three random books
     * @param recommendationList list of books
     * @return an arraylist of three books
     */
    private static ArrayList<Book> getRandomRecommendtaions(ArrayList<Book> recommendationList)
    {
        var recommendations = new ArrayList<Book>();
        var toyList = new ArrayList<>(recommendationList);
        var random = new Random();

        while (recommendations.size() < 3)
        {
            Book randomBook = toyList.get(random.nextInt(toyList.size()));

            recommendations.add(randomBook);
            toyList.remove(randomBook);
        }
        return recommendations;
    }

    /**
     * Picks the top three books based on the ratings
     * @param recommendationList list of books
     * @return an arraylist of threebooks
     */
    private static ArrayList<Book> getRecommendations(ArrayList<Book> recommendationList)
    {
        var recommendations = new ArrayList<Book>(); //output list
        var toyList = new ArrayList<>(recommendationList); //copied input list (to toy with)

        //iterate until there are three recommendations
        while (recommendations.size() < 3)
        {
            double rating = Double.MIN_VALUE; //variable to keep track of current highest rating

            //iterate toyList
            for (Book book : toyList)
            {
                if (book.getAvgRating() > rating) rating = book.getAvgRating(); //update ratign whenever a higher rating is found
            }
            double finalRating = rating; //finalise the value of rating

            recommendations.addAll(toyList.stream().filter(book -> book.getAvgRating() == finalRating).collect(Collectors.toList())); //add all the books with the current highest rating to recommendations
            ArrayList<Book> discarded = toyList.stream().filter(book -> book.getAvgRating() == finalRating).collect(Collectors.toCollection(ArrayList::new)); //add them also to another list called discarded

            discarded.forEach(toyList::remove); //remove all discarded books from toyList
        }

        while (recommendations.size() != 3) recommendations.remove(recommendations.size() - 1); //in the case there are more than three recommendations, remove from the end until there are three

        return recommendations;
    }
}
