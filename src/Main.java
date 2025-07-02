import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VirtualFileSystem vfs = new VirtualFileSystem();

        while (true) {
            System.out.print("\nEVFS> ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ", 2);

            switch (parts[0].toLowerCase()) {
                case "create":
                    if (parts.length < 2) {
                        System.out.println("❗ Usage: create <filename>");
                        break;
                    }
                    System.out.print("Enter content: ");
                    String content = scanner.nextLine();
                    vfs.createFile(parts[1], content);
                    break;
                case "read":
                    if (parts.length < 2) {
                        System.out.println("❗ Usage: read <filename>");
                        break;
                    }
                    vfs.readFile(parts[1]);
                    break;
                case "delete":
                    if (parts.length < 2) {
                        System.out.println("❗ Usage: delete <filename>");
                        break;
                    }
                    vfs.deleteFile(parts[1]);
                    break;
                case "list":
                    vfs.listFiles();
                    break;
                case "log":
                    vfs.showLog();
                    break;
                case "stats":
                    vfs.showStats();
                    break;
                case "exit":
                    System.out.println("👋 Exiting EVFS.");
                    return;
                default:
                    System.out.println("❓ Unknown command. Try: create <file>, read <file>, delete <file>, list, log, stats, exit");
            }
        }
    }
}
