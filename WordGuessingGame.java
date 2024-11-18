import java.util.*;

// Trie Node class
class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

// Trie Data Structure
class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    // Check if a word exists in the Trie
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEndOfWord;
    }
}

public class WordGuessingGame {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Preload a list of words
        String[] words = {"apple", "banana", "cherry", "mango", "grape"};
        for (String word : words) {
            trie.insert(word);
        }

        // Randomly select a word for the game
        String selectedWord = words[new Random().nextInt(words.length)];
        System.out.println("A new word has been selected for guessing!");

        // Provide a hint at the start
        System.out.println("Hint: The word has " + selectedWord.length() + " letters and starts with '" + selectedWord.charAt(0) + "'.");

        // Initialize the guessing game
        Set<Character> guessedLetters = new HashSet<>();
        int attempts = selectedWord.length() + 3; // Allow extra attempts
        char[] currentGuess = new char[selectedWord.length()];
        Arrays.fill(currentGuess, '_');
        currentGuess[0] = selectedWord.charAt(0); // Reveal the first letter

        // Display the initial hint (with the first letter revealed)
        System.out.println("\nWord to guess: " + String.valueOf(currentGuess));
        Scanner scanner = new Scanner(System.in);

        while (attempts > 0) {
            // Ask the user to guess a letter
            System.out.print("\nEnter your guess (single letter): ");
            String input = scanner.nextLine();

            // Validate input
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Please enter a single letter.");
                continue;
            }

            char guessedLetter = Character.toLowerCase(input.charAt(0));

            // Check if the letter was already guessed
            if (guessedLetters.contains(guessedLetter)) {
                System.out.println("You already guessed that letter. Try again.");
                continue;
            }

            guessedLetters.add(guessedLetter);

            // Check if the letter is in the word
            if (selectedWord.indexOf(guessedLetter) >= 0) {
                System.out.println("Correct guess!");
                for (int i = 0; i < selectedWord.length(); i++) {
                    if (selectedWord.charAt(i) == guessedLetter) {
                        currentGuess[i] = guessedLetter;
                    }
                }
            } else {
                System.out.println("Wrong guess.");
            }

            // Display the current state of the word
            System.out.println("\nWord to guess: " + String.valueOf(currentGuess));

            // Check if the word is fully guessed
            if (String.valueOf(currentGuess).equals(selectedWord)) {
                System.out.println("\nCongratulations! You've guessed the word: " + selectedWord);
                scanner.close();
                return;
            }

            // Decrement attempts
            attempts--;
            System.out.println("Attempts remaining: " + attempts);
        }

        // Game over
        System.out.println("\nGame over! The word was: " + selectedWord);
        scanner.close();
    }
}