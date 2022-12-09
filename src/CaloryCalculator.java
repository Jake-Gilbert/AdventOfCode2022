import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//Day One
//Trawls through a collection of backpacks owned by Santa's Elves and tracks both the sum of the backpack
//carrying the most calories and the sum of the top three caloric counts.
public class CaloryCalculator
{

    private int maximumCalory = 0;
    private int localGreatest = 0;
    private int localCaloryGroup = 0;
    private int maximumCollectionOfCalories = 0;
    private final Set<Integer> maximumCaloryHolders = new HashSet<>();

    public CaloryCalculator(String fileName)
    {
        try
        {
            File caloryFile = new File(fileName);
            calculateMaxCaloriesOfSingleElf(caloryFile);
            calculateMaxCaloriesForGroup(caloryFile);
        } catch (Exception e)
        {
            System.out.println("Failed");
        }

    }

    //Part 1 - Max value of single elf
    private int calculateMaxCaloriesOfSingleElf(File file) throws IOException
    {
        final var fileString = Files.readString(Path.of(file.getAbsolutePath()));
        final var fileStringArray = fileString.split("\r\n");
        int index = 0;
        for (String word : fileStringArray)
        {
            var calory = tryParseInt(word.trim(), 0);
            if (calory == 0)
            {
                localGreatest = 0;
            }
            else
            {
                localGreatest += calory;
            }
            if (index + 1 <= fileStringArray.length - 1 || index == fileStringArray.length - 1)
            {
                if (index > fileStringArray.length - 1 || index > fileStringArray.length - 1 && (fileStringArray[index + 1].equals("")))
                {
                    continue;
                }
                maximumCalory = compareToTheGreatestElf(localGreatest);
            }
            index++;
        }
        System.out.println(maximumCalory);
        return maximumCalory;
    }

    //Part 2 - max value of top 3 Elves
    private int calculateMaxCaloriesForGroup(File file) throws IOException
    {
        final var fileString = Files.readString(Path.of(file.getAbsolutePath()));
        final var fileStringArray = fileString.split("\r\n");
        int index = 0;
        for (String word : fileStringArray)
        {
            var calory = tryParseInt(word.trim(), 0);
            if (calory == 0)
            {
                localCaloryGroup = 0;
            }
            else
            {
                localCaloryGroup += calory;
            }
            if (index + 1 <= fileStringArray.length - 1 || index == fileStringArray.length - 1)
            {
                if (index == fileStringArray.length - 1 || index < fileStringArray.length - 1 && (fileStringArray[index + 1].equals("")))
                {
                    compareToTheMaximumElves(localCaloryGroup);
                }
            }
            index++;
        }
        System.out.println(calculateTotalOfMaximumHolders());
        return calculateTotalOfMaximumHolders();
    }

    private void compareToTheMaximumElves(int caloryGroup)
    {
        if (maximumCaloryHolders.size() >= 3)
        {
            if (maximumCaloryHolders.stream().filter(value -> value > caloryGroup).count() != maximumCaloryHolders.size())
            {
                maximumCaloryHolders.remove(Collections.min(maximumCaloryHolders));
                maximumCaloryHolders.add(caloryGroup);
            }
            if (maximumCollectionOfCalories < calculateTotalOfMaximumHolders())
            {
                maximumCollectionOfCalories = calculateTotalOfMaximumHolders();
            }
        } else
        {
            maximumCaloryHolders.add(caloryGroup);
        }
    }

    private int compareToTheGreatestElf(int caloricGroup)
    {
        return Math.max(caloricGroup, maximumCalory);
    }

    private int calculateTotalOfMaximumHolders()
    {
        return maximumCaloryHolders.stream().mapToInt(Integer::intValue).sum();
    }

    private int tryParseInt(String value, int defaultVal)
    {
        if (value.equals(" "))
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(value);
        } catch (NumberFormatException e)
        {
            return defaultVal;
        }
    }
}
