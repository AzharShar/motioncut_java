import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LinkShortener {
    private Map<String, String> urlMap;

    public LinkShortener() {
        this.urlMap = new HashMap<>();
    }

    public String shortenURL(String longURL) {
        if (urlMap.containsKey(longURL)) {
            return "URL already exists. Please use the existing short URL.";
        }

        String shortURL = generateShortURL();
        urlMap.put(longURL, shortURL);
        return shortURL;
    }

    public String expandURL(String shortURL) {
        if (!urlMap.containsValue(shortURL)) {
            return "Invalid short URL.";
        }

        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals(shortURL)) {
                return entry.getKey();
            }
        }
        return "Error expanding URL.";
    }

    private String generateShortURL() {
        // Implement your own hash function or use a library like Apache Commons Codec
        // For simplicity, we'll use a simple hash function here
        int hash = (int) (Math.random() * 1000000);
        return "http://short.link/" + hash;
    }

    public static void main(String[] args) {
        LinkShortener shortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longURL = scanner.next();
                    String shortURL = shortener.shortenURL(longURL);
                    System.out.println("Short URL: " + shortURL);
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortURLInput = scanner.next();
                    String expandedURL = shortener.expandURL(shortURLInput);
                    System.out.println("Expanded URL: " + expandedURL);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}