import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//Day 2 - A rock paper scissors program that parses a list of instructions and generates a value.
//The first part required a response move to be played against a certain move. E.g. Scissors vs paper.
//The second part gave outcomes that had to be achieved by winning / losing / drawing.
public class RockPaperChristmas {

    private int totalScore = 0;

    public RockPaperChristmas(String filePath)
    {
        try
        {
            beginGameSimulation(filePath);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void beginGameSimulation(String filePath) throws IOException {
        final var fileString = Files.readString(Path.of(filePath));
        final var fileStringArray =  fileString.split("\r\n");
        int index = 0;
        for (String word : fileStringArray)
        {
            final var charArray = word.toCharArray();
            //Part 1 commented out
            //totalScore += processCharIntoValue(charArray[2]) + calculateScoreAfterBattle(charArray[2], charArray[0]);
            totalScore += calculateScoreAfterBattle(charArray[2], charArray[0]);
        }
        System.out.println(totalScore);
    }

    public int processShapeIntoValue(Character move)
    {
        switch (move)
        {
            case 'A':
            case 'X':
                return 1;
            case 'B':
            case 'Y':
                return 2;
            case 'C':
            case 'Z':
                return 3;
            default:
                return 0;
        }
    }

    //Part 1
    private int calculateScoreAfterBattle(Character yourMove, Character theirMove)
    {
        return determineScoreFromAction(yourMove) + processShapeIntoValue(determineAction(yourMove, theirMove));
       /* if (processCharIntoValue(yourMove) == processCharIntoValue(theirMove))
        {
            return 3;
        }
        else if (determineAction(yourMove, theirMove).equals('c'))
        {
            return 6;
        }
        else
        {
            return 0;
        }*/
    }

    private boolean determineVictor(Character yourMove, Character theirMove)
    {
        if (yourMove.equals('X') && (theirMove.equals('B') || theirMove.equals('A')))
        {
            return false;
        }
        if (yourMove.equals('Y') && (theirMove.equals('B') || theirMove.equals('C')))
        {
            return false;
        }
        if (yourMove.equals('Z') && (theirMove.equals('C') || theirMove.equals('A')))
        {
            return false;
        }
        return true;
    }

    //Part 2
    private Character determineAction(Character yourMove, Character theirMove)
    {
        if (yourMove.equals('X'))
        {
            return determineLosingMove(theirMove);
        }
        if (yourMove.equals('Y'))
        {
            return theirMove;
        }
        if (yourMove.equals('Z'))
        {
            return determineWinningMove(theirMove);
        }
        return 'C';
    }

    private int determineScoreFromAction(Character action)
    {
        switch (action)
        {
            case 'X':
                return 0;
            case 'Y':
                return 3;
            case 'Z':
                return 6;
            default:
                return action;
        }
    }

    //Part 2
    private Character determineLosingMove(Character theirMove)
    {
        switch (theirMove)
        {
            case 'A':
                return 'C';
            case  'B':
                return 'A';
            case 'C':
                return 'B';
            default:
                return theirMove;
        }
    }

    private Character determineWinningMove(Character theirMove)
    {
        switch (theirMove)
        {
            case 'A':
                return 'B';
            case  'B':
                return 'C';
            case 'C':
                return 'A';
            default:
                return theirMove;
        }
    }
}
