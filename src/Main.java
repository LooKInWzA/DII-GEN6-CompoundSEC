import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    private static AccessManager manager = new AccessManager();
    private static List<Floor> floors = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {
        showMainMenu();
    }

    private static void showMainMenu() throws Exception {
        while(true) {
            System.out.println("\n=== Access Control System ===");
            System.out.println("1. Create Floor");
            System.out.println("2. Create Room");
            System.out.println("3. Issue Access Card");
            System.out.println("4. Check Access");
            System.out.println("5. Revoke Card");
            System.out.println("6. Modify Card");
            System.out.println("7. Revoke Room");
            System.out.println("8. Revoke Floor");
            System.out.println("9. Show All Data");
            System.out.println("10. Show System Logs");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1: createFloor(); break;
                case 2: createRoom(); break;
                case 3: issueCard(); break;
                case 4: checkAccess(); break;
                case 5: revokeCard(); break;
                case 6: modifyCard(); break;
                case 7: revokeRoom(); break;
                case 8: revokeFloor(); break;
                case 9: showAllData(); break;
                case 10: showLogs(); break;
                case 0: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void createFloor() {
        System.out.print("Enter floor number: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        floors.add(new Floor(number));
        AccessLog.recordAccess("Floor created: " + number);
        System.out.println("Floor " + number + " created!");
    }

    private static void createRoom() {
        System.out.print("Enter room number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter floor number: ");
        int floorNum = scanner.nextInt();
        scanner.nextLine();

        Optional<Floor> floor = floors.stream()
                .filter(f -> f.getFloorNumber() == floorNum)
                .findFirst();

        if(floor.isPresent()) {
            Room room = new Room(roomNum, floor.get());
            rooms.add(room);
            AccessLog.recordAccess("Room created: " + roomNum + " on floor " + floorNum);
            System.out.println("Room " + roomNum + " created!");
        } else {
            System.out.println("Floor not found!");
        }
    }

    private static void issueCard() throws Exception {
        System.out.print("Owner name: ");
        String owner = scanner.nextLine();

        System.out.print("Expiry date (yyyy-mm-dd): ");
        Date expiry = sdf.parse(scanner.nextLine());

        System.out.print("Allowed floors (space-separated): ");
        Set<Integer> floors = parseNumbers(scanner.nextLine());

        System.out.print("Allowed rooms (space-separated): ");
        Set<Integer> rooms = parseNumbers(scanner.nextLine());

        String cardId = "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        CombinedAccessCard card = manager.createCombinedCard(cardId, owner, expiry, floors, rooms);
        manager.addCard(card);

        AccessLog.recordAccess("Card issued: " + cardId);
        System.out.println("Card issued successfully!");
    }

    private static void checkAccess() {
        System.out.print("Enter card ID: ");
        String cardId = scanner.nextLine();

        System.out.print("Enter room number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();

        Optional<Room> room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNum)
                .findFirst();

        Optional<CombinedAccessCard> card = manager.getCards().stream()
                .filter(c -> c.cardID.equals(cardId))
                .map(c -> (CombinedAccessCard)c)
                .findFirst();

        if(card.isPresent() && room.isPresent()) {
            boolean access = manager.checkRoomAccess(card.get(), room.get());
            System.out.println("Access " + (access ? "GRANTED" : "DENIED"));
            AccessLog.recordAccess("Access checked: " + cardId + " for room " + roomNum);
        } else {
            System.out.println("Invalid card/room!");
        }
    }

    private static void revokeCard() {
        System.out.print("Enter card ID to revoke: ");
        String cardId = scanner.nextLine();
        manager.revokeCard(cardId);
    }

    private static void modifyCard() throws Exception {
        System.out.print("Enter card ID to modify: ");
        String cardId = scanner.nextLine();

        System.out.print("New owner name: ");
        String newOwner = scanner.nextLine();

        System.out.print("New expiry date (yyyy-mm-dd): ");
        Date newExpiry = sdf.parse(scanner.nextLine());

        System.out.print("New allowed floors (space-separated): ");
        Set<Integer> newFloors = parseNumbers(scanner.nextLine());

        System.out.print("New allowed rooms (space-separated): ");
        Set<Integer> newRooms = parseNumbers(scanner.nextLine());

        manager.modifyCard(cardId, newOwner, newExpiry, newFloors, newRooms);
    }

    private static void revokeRoom() {
        System.out.print("Enter room number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();

        Optional<Room> room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNum)
                .findFirst();

        if(room.isPresent()) {
            Floor floor = room.get().getFloor();
            floor.removeRoom(roomNum);
            rooms.removeIf(r -> r.getRoomNumber() == roomNum);
            AccessLog.recordAccess("Room revoked: " + roomNum);
            System.out.println("Room revoked!");
        } else {
            System.out.println("Room not found!");
        }
    }

    private static void revokeFloor() {
        System.out.print("Enter floor number: ");
        int floorNum = scanner.nextInt();
        scanner.nextLine();

        Optional<Floor> floor = floors.stream()
                .filter(f -> f.getFloorNumber() == floorNum)
                .findFirst();

        if(floor.isPresent()) {
            rooms.removeIf(r -> r.getFloor().getFloorNumber() == floorNum);
            floors.removeIf(f -> f.getFloorNumber() == floorNum);
            AccessLog.recordAccess("Floor revoked: " + floorNum);
            System.out.println("Floor and all rooms revoked!");
        } else {
            System.out.println("Floor not found!");
        }
    }

    private static void showAllData() {
        System.out.println("\n=== Floors ===");
        floors.forEach(f -> System.out.println("- Floor " + f.getFloorNumber()));

        System.out.println("\n=== Rooms ===");
        rooms.forEach(r -> System.out.println("- Room " + r.getRoomNumber() + " (Floor " + r.getFloor().getFloorNumber() + ")"));

        System.out.println("\n=== Cards ===");
        manager.getCards().forEach(c -> {
            CombinedAccessCard card = (CombinedAccessCard)c;
            System.out.println("ID: " + card.cardID);
            System.out.println("Owner: " + card.ownerName);
            System.out.println("Expiry: " + sdf.format(card.expiryDate));
            System.out.println("Floors: " + card.allowedFloors);
            System.out.println("Rooms: " + card.allowedRooms + "\n");
        });
    }

    private static void showLogs() {
        System.out.println("\n=== System Logs ===");
        List<String> logs = AccessLog.getLogs();
        if(logs.isEmpty()) {
            System.out.println("No logs found.");
        } else {
            logs.forEach(System.out::println);
        }
    }

    private static Set<Integer> parseNumbers(String input) {
        Set<Integer> numbers = new HashSet<>();
        for(String s : input.split(" ")) {
            try {
                numbers.add(Integer.parseInt(s));
            } catch(NumberFormatException e) {
                System.out.println("Invalid number: " + s);
            }
        }
        return numbers;
    }
}