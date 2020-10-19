package Automate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Automate {

    private final int lenAlph;  // number of letters in alphabet
    private final int numberOfStates;
    private final int firstState;
    private final int numOfFinStates;

    private ArrayList<ArrayList<Integer>> WordsAsInt = new ArrayList<>();
    private HashSet<ArrayList<Character>> FinalWords = new HashSet<>();

    private final List<Integer> finStatesList = new LinkedList<>();
    private ArrayList<Character>[][] transitionFunc;
    private int[][] neighbourStates; // have transitions

    private void baseTransitionFunc()   // empty matrix with character lists
    {
        transitionFunc = new ArrayList[numberOfStates][numberOfStates];
        for (int i = 0; i < numberOfStates; i++)
        {
            for (int j = 0; j < numberOfStates; j++)
            {
                transitionFunc[i][j] = new ArrayList<>();
            }
        }
    }

    private void baseNeighbourStates()  // empty matrix
    {
        neighbourStates = new int[numberOfStates][numberOfStates];

        for (int i = 0; i < numberOfStates; i++)
        {
            for (int j = 0; j < numberOfStates; j++)
            {
                neighbourStates[i][j] = 0;
            }
        }
    }

    Automate(File file) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(file);

        lenAlph = Integer.parseInt(scanner.nextLine());
        numberOfStates = Integer.parseInt(scanner.nextLine());
        firstState = Integer.parseInt(scanner.nextLine());

        String finStateLine = scanner.nextLine();
        var finStatesString = finStateLine.split(" ");
        numOfFinStates = Integer.parseInt(finStatesString[0]);

        for (int i = 1; i <= numOfFinStates; i++)
        {
            finStatesList.add(Integer.parseInt(finStatesString[i]));
        }

        baseTransitionFunc();
        baseNeighbourStates();

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            var inputTransitions = line.split(" ");

            int st1 = Integer.parseInt(inputTransitions[0]);
            int st2 = Integer.parseInt(inputTransitions[2]);

            neighbourStates[st1][st2] += 1;
            var letter = inputTransitions[1].charAt(0);
            transitionFunc[st1][st2].add(letter);
        }
    }

    private void determPath(Integer firstState, ArrayList<Integer> thisWord)
    {
        if (finStatesList.contains(firstState))
        {
            ArrayList<Integer> word = new ArrayList<>(thisWord);
            WordsAsInt.add(word);
        }
        else
            {
            for (int i = 0; i < numberOfStates; i++)
            {
                if (neighbourStates[firstState][i] > 0)
                {
                    neighbourStates[firstState][i] = neighbourStates[firstState][i] - 1;
                    thisWord.add(i);
                    determPath(i, thisWord);

                    neighbourStates[firstState][i] = neighbourStates[firstState][i] + 1;
                    thisWord.remove(thisWord.lastIndexOf(i));
                }
            }
        }
    }

    private ArrayList<ArrayList<Character>> pathIntoChArr(ArrayList<Integer> wordInt)
    {
        Integer thisState = wordInt.get(0);
        ArrayList<ArrayList<Character>> charsArray = new ArrayList<>();

        for (int i = 1; i < wordInt.size(); i++)
        {
            Integer nextState = wordInt.get(i);
            ArrayList<Character> chars = new ArrayList<>(transitionFunc[thisState][nextState]);
            charsArray.add(chars);
            thisState = nextState;
        }
        return charsArray;
    }

    private ArrayList<Integer> pathIntoIntArr(ArrayList<Integer> wordInt)   // to exclude repetitive transitions later
    {
        ArrayList<Integer> path = new ArrayList<>();
        int thisState = wordInt.get(0);
        for (int i = 1; i < wordInt.size(); i++)
        {
            int nextState = wordInt.get(i);
            path.add(thisState * numberOfStates + nextState);
            thisState = nextState;
        }
        return path;
    }

    private boolean correctWord(ArrayList<Integer> integerWord, ArrayList<Character> word)
    {
        HashMap<Integer, ArrayList<Character>> nonRepMap = new HashMap<>();

        for (int i = 0; i < integerWord.size(); i++)
        {
            if (nonRepMap.containsKey(integerWord.get(i)))
            {
                if (nonRepMap.get(integerWord.get((i))).contains(word.get(i)))
                {
                    return false;
                }
                else
                    {
                    nonRepMap.get(integerWord.get(i)).add(word.get(i));
                }
            }
            else
                {
                ArrayList<Character> letters = new ArrayList<>();
                letters.add(word.get(i));
                nonRepMap.put(integerWord.get(i), letters);
            }
        }
        return true;
    }

    private void ChArrIntoWords(ArrayList<ArrayList<Character>> charsArray, int start, ArrayList<Character> current, ArrayList<Integer> path)
    {
        if (start == charsArray.size())
        {
            if (correctWord(path, current))
            {
                ArrayList<Character> word = new ArrayList<>(current);
                FinalWords.add(word);
            }

        }
        else
            {
            for (int j = 0; j < charsArray.get(start).size(); j++)
            {
                current.add(charsArray.get(start).get(j));
                ChArrIntoWords(charsArray, start + 1, current, path);
                current.remove(current.size() - 1);
            }
        }
    }

    private void determWords()
    {
        ArrayList<Integer> word = new ArrayList<>();
        word.add(firstState);
        determPath(firstState, word);

        for (ArrayList<Integer> integers : WordsAsInt)
        {
            ArrayList<Character> current = new ArrayList<>();
            ChArrIntoWords(pathIntoChArr(integers), 0, current, pathIntoIntArr(integers));
        }
    }

    public void printWords()
    {
        determWords();
        for (ArrayList<Character> word : FinalWords)
        {
            StringBuilder builder = new StringBuilder();
            for (Character ch : word)
            {
                if (ch != "e".charAt(0))    // "e" - zero-length word
                    builder.append(ch);
            }
            System.out.println(builder.toString());
        }
    }
}
