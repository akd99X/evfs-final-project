public class Encryptor {
    private static final char KEY = 'K'; // XOR key

    public static String simpleXOR(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append((char)(c ^ KEY));
        }
        return output.toString();
    }
}
