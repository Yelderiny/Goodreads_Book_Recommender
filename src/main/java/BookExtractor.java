import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BookExtractor
{
    private final ArrayList<Book> allBooks = new ArrayList<>();
    private final ArrayList<Book> recommendationList = new ArrayList<>();

    public ArrayList<Book> getRecommendationList() { return recommendationList; }

    /**
     * Extracts all the information from the input Excel files, transforms them into objects, filters them, then filters them
     */
    public void readExcel(String inPath)
    {
        File excelFile = new File(inPath); //get input Excel file

        try (FileInputStream input = new FileInputStream(excelFile))
        {
            XSSFWorkbook workbook = new XSSFWorkbook(input); //get workbook from the FileInputStream

            XSSFSheet propSheet = workbook.getSheetAt(0); //instantiate the sheet

            allBooks.addAll(rowToBook(propSheet)); //fill allBooks with all the books in sheet 1

            recommendationList.addAll(allBooks.stream().filter(book -> book.isOwned() && !book.isRead()).collect(Collectors.toList())); //recommendationsList filter out read and unowened books
        }
        catch (IOException ioe) { ioe.printStackTrace(); }
    }

    /**
     * Helper function that goes through the entire Excel sheet transforming a row into a Book object
     * @param propSheet current excel sheet
     * @return an arraylist of Books
     */
    private ArrayList<Book> rowToBook(XSSFSheet propSheet)
    {
        ArrayList<Book> bookList = new ArrayList<>(); //return variable

        //traverse every Row with enhance for loop
        for (Row row : propSheet)
        {
            if (row.getRowNum() == 0) continue;

            Book book = new Book(); //create new Book instance

            //traverse every cell in the row
            for (Cell cell : row)
            {
                //identify the column and add to the object instance accordingly
                switch (cell.getColumnIndex())
                {
                    case 1 -> book.setTitle(cell.getStringCellValue());
                    case 2 -> book.setAuthor(cell.getStringCellValue());
                    case 8 -> book.setAvgRating(cell.getNumericCellValue());
                    case 12 -> book.setYearPublished((int) cell.getNumericCellValue());
                    case 16 -> setBookShelves(book, cell);
                    case 22 -> book.setRead(cell.getNumericCellValue() != 0);
                    case 25 -> book.setOwned(cell.getNumericCellValue() != 0);
                }
            }
            bookList.add(book); //add the book to the output list
        }
        return bookList;
    }

    /**
     * Extracts the shelve names of the cell, separates them, then adds them individually to the book object
     * @param book current book object
     * @param cell current cell
     */
    private void setBookShelves(Book book, Cell cell)
    {
        String[] shelves = cell.getStringCellValue().split(",");

        for (String shelf : shelves) { book.addShelf(shelf); }
    }
}
