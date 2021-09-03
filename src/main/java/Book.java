import java.util.ArrayList;

public class Book
{
    //attributes
    private String title;
    private String author;
    private double avgRating;
    private int yearPublished;
    private final ArrayList<String> shelves = new ArrayList<>();
    private boolean owned;
    private boolean read;

    //accessors
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getAvgRating() { return avgRating; }
    public int getYearPublished() { return yearPublished; }
    public ArrayList<String> getShelves() { return shelves; }
    public boolean isOwned() { return owned; }
    public boolean isRead() { return read; }

    //mutators
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }
    public void addShelf(String shelf) { shelves.add(shelf); }
    public void setOwned(boolean owned) { this.owned = owned; }
    public void setRead(boolean read) { this.read = read; }

    @Override
    public String toString()
    {
        return String.format("%s by %s, published in %d with a rating of %.2f", getTitle(), getAuthor(), getYearPublished(), getAvgRating());
    }
}
