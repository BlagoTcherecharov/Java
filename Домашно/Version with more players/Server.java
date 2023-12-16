import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server 
{
    private static final int PORT = 8080;
    private static final int MAX_THREADS = 10; 

    public static void main(String[] args) throws IOException 
    {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Server started");

        AtomicBoolean gameWon = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        Random randomNum = new Random();
        int number = randomNum.nextInt(100);

        try 
        {
            while (true) 
            {
                Socket s = ss.accept();
                System.out.println("New client connected: " + s);

                Runnable clientHandler = new ClientHandler(s, number, gameWon);
                executorService.submit(clientHandler);
            }
        } 
        finally 
        {
            executorService.shutdown();
            ss.close();
        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable {
    private Socket s;
    private int number;
    private AtomicBoolean gameWon;
    private boolean finish = false;

    public ClientHandler(Socket s, int number, AtomicBoolean gameWon) {
        this.s = s;
        this.number = number;
        this.gameWon = gameWon;
    }

    @Override
    public void run() {
        int guess = 0;
        try {
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(number);
            pr.flush();
        } catch (IOException e) {
            System.out.println("PrintWriter error!");
        }

        while (number != guess && !gameWon.get()) {
            try {
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println(finish);
                pr.flush();

                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                guess = Integer.parseInt(bf.readLine());
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }

            if (guess == number) {
                System.out.println("You have guessed the number!");
                
                // Use compareAndSet to safely set the value to true
                if (!gameWon.get()) {
                    boolean oldValue = gameWon.get();
                    boolean newValue = true;
                    gameWon.compareAndSet(oldValue, newValue);
                }

                break;
            } else if (guess > number)
                System.out.println("Secret number is lower than " + guess);
            else
                System.out.println("Secret number is higher than " + guess);
        }
        
        if(number != guess)
        {
            finish = true;
            PrintWriter pr;
            try {
                pr = new PrintWriter(s.getOutputStream());
                pr.println(finish);
                pr.flush();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println("Other player has guessed the number before you!");
        }
    }
}import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server 
{
    private static final int PORT = 8080;
    private static final int MAX_THREADS = 10; 

    public static void main(String[] args) throws IOException 
    {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Server started");

        AtomicBoolean gameWon = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        Random randomNum = new Random();
        int number = randomNum.nextInt(100);

        try 
        {
            while (true) 
            {
                Socket s = ss.accept();
                System.out.println("New client connected: " + s);

                Runnable clientHandler = new ClientHandler(s, number, gameWon);
                executorService.submit(clientHandler);
            }
        } 
        finally 
        {
            executorService.shutdown();
            ss.close();
        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable {
    private Socket s;
    private int number;
    private AtomicBoolean gameWon;
    private boolean finish = false;

    public ClientHandler(Socket s, int number, AtomicBoolean gameWon) {
        this.s = s;
        this.number = number;
        this.gameWon = gameWon;
    }

    @Override
    public void run() {
        int guess = 0;
        try {
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(number);
            pr.flush();
        } catch (IOException e) {
            System.out.println("PrintWriter error!");
        }

        while (number != guess && !gameWon.get()) {
            try {
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println(finish);
                pr.flush();

                InputStreamReader in = new InputStreamReader(s.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                guess = Integer.parseInt(bf.readLine());
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }

            if (guess == number) {
                System.out.println("You have guessed the number!");
                
                // Use compareAndSet to safely set the value to true
                if (!gameWon.get()) {
                    boolean oldValue = gameWon.get();
                    boolean newValue = true;
                    gameWon.compareAndSet(oldValue, newValue);
                }

                break;
            } else if (guess > number)
                System.out.println("Secret number is lower than " + guess);
            else
                System.out.println("Secret number is higher than " + guess);
        }
        
        if(number != guess)
        {
            finish = true;
            PrintWriter pr;
            try {
                pr = new PrintWriter(s.getOutputStream());
                pr.println(finish);
                pr.flush();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println("Other player has guessed the number before you!");
        }
    }
}
