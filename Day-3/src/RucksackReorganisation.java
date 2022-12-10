import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//Day Three a list of rucksacks with 2 compartments was given,
//each had to be checked for the single item that existed in both compartments.
//Part 2 required finding the item that existed in 3 different rucksacks.
public class RucksackReorganisation
{
    public static void main(String[] args) {
        calculateRucksackValues("AdventOfCode2022/Day-3/src/actual_backpacks");
        calculateBadgeGroupValues("AdventOfCode2022/Day-3/src/actual_backpacks");
    }

    private static int calculateRucksackValues(String path)
    {
        try
        {
            final var fileString = Files.readString(Path.of(path));
            final var fileStringArray =  fileString.split("\r\n");

            final var allRucksackItemMap = new HashMap<Character, Integer>();
            for (String word : fileStringArray)
            {
                final var duplicateItem = processWord(word);

                if (allRucksackItemMap.get(duplicateItem) != null)
                {
                    allRucksackItemMap.put(duplicateItem, allRucksackItemMap.get(duplicateItem) + 1);
                }
                else allRucksackItemMap.putIfAbsent(duplicateItem, 1);
            }
            System.out.println(calculateTotalOfMap(allRucksackItemMap));
            return calculateTotalOfMap(allRucksackItemMap);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    private static int calculateBadgeGroupValues(String path)
    {
        try
        {
            final var fileString = Files.readString(Path.of(path));
            final var fileStringArray =  fileString.split("\r\n");
            var totalSum = 0;
            for (int i = 0; i < fileStringArray.length; i += 3)
            {
                if (i + 3 <= fileStringArray.length)
                {
                    final var rucksackWordList = new ArrayList<String>();
                    rucksackWordList.add(fileStringArray[i]);
                    rucksackWordList.add(fileStringArray[i + 1]);
                    rucksackWordList.add(fileStringArray[i + 2]);
                    totalSum += calculateValueOfItem(findCommonItem(rucksackWordList));
                }
            }
            System.out.println(totalSum);
            return totalSum;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }

    private static Character processWord(String word)
    {
        try
        {
            final var ruckSackFirstCompartmentMap = new HashMap<Character, Integer>();
            final var ruckSackSecondCompartmentMap = new HashMap<Character, Integer>();

            final var charArray = word.toCharArray();
            for (int i = 0; i < (charArray.length / 2); i++)
            {
                if (ruckSackFirstCompartmentMap.get(charArray[i]) != null)
                {
                    ruckSackFirstCompartmentMap.put(charArray[i], ruckSackFirstCompartmentMap.get(charArray[i]) + 1);
                    continue;
                }
                ruckSackFirstCompartmentMap.put(charArray[i], 1);
            }
            for (int i = charArray.length / 2; i < charArray.length; i++)
            {
                if (ruckSackSecondCompartmentMap.get(charArray[i]) != null)
                {
                    ruckSackSecondCompartmentMap.put(charArray[i], ruckSackSecondCompartmentMap.get(charArray[i]) + 1);
                    continue;
                }
                ruckSackSecondCompartmentMap.put(charArray[i], 1);
            }
            return ruckSackFirstCompartmentMap.keySet().stream().filter(item -> ruckSackSecondCompartmentMap.get(item) != null).findAny().get();
        }
        catch (Exception e)
        {
            System.out.println(word);
            return null;
        }
    }

    private static Character findCommonItem(List<String> words)
    {
        final var firstCharCountMap = new HashMap<Character, Integer>();
        final var secondCharCountMap = new HashMap<Character, Integer>();
        final var thirdCharCountMap = new HashMap<Character, Integer>();
        for (int i = 0; i < words.size(); i++)
        {
            if (i == 0)
            {
                firstCharCountMap.putAll(generateCharCountMap(firstCharCountMap, words.get(i)));
            }
            else if (i == 1)
            {
                secondCharCountMap.putAll(generateCharCountMap(secondCharCountMap, words.get(i)));
            }
            else
            {
                thirdCharCountMap.putAll(generateCharCountMap(thirdCharCountMap, words.get(i)));
            }
        }
        return firstCharCountMap.keySet().stream().filter(item -> secondCharCountMap.get(item) != null && thirdCharCountMap.get(item) != null).findFirst().get();
    }

    private static Map<Character, Integer> generateCharCountMap(Map<Character, Integer> charCountMap, String word)
    {
        for (Character item : word.toCharArray())
        {
            if (charCountMap.get(item) != null)
            {
                charCountMap.put(item, charCountMap.get(item)  + 1);
                continue;
            }
            charCountMap.put(item, 1);
        }
        return charCountMap;
    }

    private static int calculateTotalOfMap(Map<Character, Integer> duplicateItemMap)
    {
        return duplicateItemMap.entrySet().stream().map(item -> processTotalValueOfItem(item.getKey(), item.getValue())).mapToInt(value -> value).sum();
    }

    private static int processTotalValueOfItem(Character item, Integer count)
    {
        return calculateValueOfItem(item) * count;
    }

    //Uses ASCII value to calculate value of each alphabetical character.
    private static int calculateValueOfItem(Character item)
    {
        return Character.isUpperCase(item) ? (int) item - 96 + 58: (int) item - 96;
    }

}
