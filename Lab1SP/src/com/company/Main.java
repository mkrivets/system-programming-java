package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;

    public class Main
    {
        public static void main(String[] args)
        {
            try
            {
                File file = new File("1.txt");       // init file
                Scanner myReader = new Scanner(file);
                ArrayList<String> lines = new ArrayList<String>();
                ArrayList<String> words = new ArrayList<String>();
                while (myReader.hasNextLine())      // file into lines
                {
                    String data = myReader.nextLine();
                    lines.add(data);
                }
                myReader.close();

                int WordMaxLen = 30;

                words = LinesIntoCorrectWords(lines, WordMaxLen);

                String word;
                for (int i = 0; i < words.size(); i++)
                {
                    word = words.get(i);
                    if (VowelsMoreThanConsonants(word) == true)
                    {
                        System.out.println(word);
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        public static ArrayList<String> LinesIntoCorrectWords(ArrayList<String> lines, int WordMaxLen)
        {
            ArrayList<String> words = new ArrayList<String>();      // result of the function
            String line;
            String letter;
            String word = "";

            int k;      // counter for elements of lines
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                k = 0;
                while (k < line.length())
                {
                    if (Character.toString(line.charAt(k)).matches("[a-zA-Zа-яА-Я]") == true)
                    {
                        word = "";
                        word += line.charAt(k);
                        k++;
                        while ((k < line.length()) && (Character.toString(line.charAt(k)).matches("[a-zA-Zа-яА-Я]") == true))
                        {
                            word += line.charAt(k);     // adding letters to the word until non letter
                            k++;
                        }
                        if (word.length()>WordMaxLen)                   // cutting words over 30 letters
                        {
                            word = word.substring(0, WordMaxLen);
                        }
                        if (words.contains(word.toLowerCase()) == false)
                        {
                            words.add(word.toLowerCase());
                        }
                    }
                    k++;
                }
            }
            return words;
        }

        public static Boolean VowelsMoreThanConsonants(String word)
        {
            int vowels = 0;
            int consonants = 0;
            char ch;
            for (int j = 0; j < word.length(); j++)
            {
                ch = word.charAt(j);
                if ((ch == 'a') || (ch == 'e') || (ch == 'i') || (ch == 'o') || (ch == 'u') || (ch == 'y') || (ch == 'а') || (ch == 'о') || (ch == 'и') || (ch == 'е') || (ch == 'ё') || (ch == 'э') || (ch == 'ы') || (ch == 'у') || (ch == 'ю') || (ch == 'я'))
                {
                    vowels++;
                }
                else
                {
                    consonants++;
                }
            }
            if (vowels > consonants) return true;
            else return false;
        }
    }
