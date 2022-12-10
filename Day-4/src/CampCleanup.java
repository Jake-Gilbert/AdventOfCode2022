import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

//Day Four parses a list of schedules with
//each line representing a pair of Elves' schedules.
//Sometimes the zones overlap e.g. 1-3, 3-6.
//Part 1 required finding any instances of overlap.
//Part 2 required finding only the overlaps where one set contains the entirety of the other.
public class CampCleanup
{
    private int overlapCounter = 0;

    public int processSchedule(String path)
    {
        try
        {
            final var fileString = Files.readString(Path.of(path));
            final var fileStringArray =  fileString.split("\r\n");

            for (String scheduleGroup : fileStringArray)
            {
                final var scheduleGroups = scheduleGroup.split(",");
                /*if (checkScheduleContainsOther(scheduleGroups[0], scheduleGroups[1]))
                {
                    overlapCounter++;
                }*/
                if (checkOverlap(scheduleGroups[0], scheduleGroups[1]))
                {
                    overlapCounter++;
                }
            }
            System.out.println(overlapCounter);
            return overlapCounter;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return 0;
        }
    }

    private void appendToMap(ArrayList<Integer> scheduleValues, Map<Integer, Integer> countOfValues)
    {
        for (Integer zone : scheduleValues)
        {
            if (countOfValues.get(zone) != null)
            {
                countOfValues.put(zone, countOfValues.get(zone) + 1);
            }
            else
            {
                countOfValues.putIfAbsent(zone, 1);
            }
        }
    }

    private boolean checkScheduleContainsOther(String firstSchedule, String secondSchedule)
    {
        final var firstScheduleList = calculateAllSections(firstSchedule);
        final var secondScheduleList = calculateAllSections(secondSchedule);

        return firstScheduleList.containsAll(secondScheduleList) || secondScheduleList.containsAll(firstScheduleList);
    }

    private boolean checkOverlap(String firstSchedule, String secondSchedule)
    {
        final var firstScheduleList = calculateAllSections(firstSchedule);
        final var secondScheduleList = calculateAllSections(secondSchedule);

        return firstScheduleList.stream().anyMatch(secondScheduleList::contains) || secondScheduleList.stream().anyMatch(firstScheduleList::contains);
    }

    private ArrayList<Integer> calculateAllSections(String schedule)
    {
        final String[] splitSchedule = schedule.split("-");
        final var fullSchedule = new ArrayList<Integer>();
        for (int i = Integer.parseInt(splitSchedule[0]); i <= Integer.parseInt(splitSchedule[1]); i++)
        {
            fullSchedule.add(i);
        }
        return fullSchedule;
    }
}
