import java.net.*;
import java.io.*;
import java.util.*;

public class Client 
{
    public static void main(String[] args) throws IOException
    {
        int guess = 0;
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("localhost", 8080);

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        int number = Integer.parseInt(bf.readLine());

        boolean finish = false;

        while (true) 
        {
            finish = Boolean.parseBoolean(bf.readLine());

            if(finish == true)
                break;


            System.out.print("Enter number between 0 and 100: ");
            guess = scanner.nextInt();
            
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(guess);
            pr.flush();
            
            if(guess < 0 || guess > 100)
            {
                System.out.println("Please enter a number between 1 and 100.");
                continue;
            }

            if (guess == number) 
            {
                System.out.println("You have guessed the number!");
                break;
            } 
            else if (guess > number) 
            {
                System.out.println("Secret number is lower than " + guess);
            } 
            else 
            {
                System.out.println("Secret number is higher than " + guess);
            }
        }

        if(number != guess)
            System.out.println("Other player has guessed the number before you!");

        s.close();
        scanner.close();
    }
}
