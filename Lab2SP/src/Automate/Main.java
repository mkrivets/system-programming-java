package Automate;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main
{
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("The name of your file is:");
        String filename = sc.nextLine();

        Path p = Paths.get(filename);
        File f = p.toFile();

        try
        {
            Automate finiteStateMachine = new Automate(f);
            System.out.println("All words (without periodic fragments) that are perceived\n" +
                    "by the given finite automate:");
            finiteStateMachine.printWords();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("There is no file with the name like this");
        }
    }
}