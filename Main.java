import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 🔹 Interface Room กำหนดมาตรฐานของห้องพัก
interface Room {
    void bookRoom();
    double getPrice();
    String getRoomType();
}

// 🔹 คลาสลูกที่ Implement Room
class StandardRoom implements Room {
    private int roomNumber;
    public StandardRoom(int roomNumber) { this.roomNumber = roomNumber; }
    @Override public void bookRoom() { System.out.println("✅ จองห้อง Standard หมายเลข " + roomNumber + " สำเร็จ"); }
    @Override public double getPrice() { return 1000; }
    @Override public String getRoomType() { return "Standard"; }
}

class DeluxeRoom implements Room {
    private int roomNumber;
    public DeluxeRoom(int roomNumber) { this.roomNumber = roomNumber; }
    @Override public void bookRoom() { System.out.println("✅ จองห้อง Deluxe หมายเลข " + roomNumber + " สำเร็จ"); }
    @Override public double getPrice() { return 2000; }
    @Override public String getRoomType() { return "Deluxe"; }
}

class SuiteRoom implements Room {
    private int roomNumber;
    public SuiteRoom(int roomNumber) { this.roomNumber = roomNumber; }
    @Override public void bookRoom() { System.out.println("✅ จองห้อง Suite หมายเลข " + roomNumber + " สำเร็จ"); }
    @Override public double getPrice() { return 5000; }
    @Override public String getRoomType() { return "Suite"; }
}

// 🔹 คลาสแม่ Payment
abstract class Payment {
    protected double amount;
    public Payment(double amount) { this.amount = amount; }
    public abstract void processPayment();
    @Override public String toString() { return "💰 ยอดชำระ: " + amount + " บาท"; }
}

// 🔹 คลาสลูกของ Payment
class CashPayment extends Payment {
    public CashPayment(double amount) { super(amount); }
    @Override public void processPayment() { System.out.println("💵 ชำระเงินด้วยเงินสด: " + amount + " บาท เรียบร้อยแล้ว"); }
}

class CreditCardPayment extends Payment {
    private String cardNumber;
    public CreditCardPayment(double amount, String cardNumber) { super(amount); this.cardNumber = cardNumber; }
    @Override public void processPayment() {
        System.out.println("💳 ชำระเงินด้วยบัตรเครดิต (xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length() - 4) + "): " + amount + " บาท เรียบร้อยแล้ว");
    }
    @Override public String toString() { return super.toString() + "\n💳 วิธีชำระ: บัตรเครดิต (xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length() - 4) + ")"; }
}

class EWalletPayment extends Payment {
    private String walletName;
    public EWalletPayment(double amount, String walletName) { super(amount); this.walletName = walletName; }
    @Override public void processPayment() { System.out.println("📲 ชำระเงินด้วย " + walletName + ": " + amount + " บาท เรียบร้อยแล้ว"); }
    @Override public String toString() { return super.toString() + "\n📲 วิธีชำระ: " + walletName; }
}

// 🔹 ระบบจองโรงแรม
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Room> bookedRooms = new ArrayList<>();
        int roomCounter = 100; // ใช้กำหนดหมายเลขห้องอัตโนมัติ

        while (true) {
            // ✅ แสดงตัวเลือกห้อง
            System.out.println("\n📌 กรุณาเลือกประเภทห้อง (ป้อนหมายเลข):");
            System.out.println("1. Standard (1,000 บาท)");
            System.out.println("2. Deluxe (2,000 บาท)");
            System.out.println("3. Suite (5,000 บาท)");
            System.out.println("0. เสร็จสิ้นการเลือกห้อง");
            System.out.print("👉 ป้อนหมายเลขที่ต้องการ: ");
            int choice = scanner.nextInt();

            if (choice == 0) break;

            Room room = null;
            switch (choice) {
                case 1: room = new StandardRoom(++roomCounter); break;
                case 2: room = new DeluxeRoom(++roomCounter); break;
                case 3: room = new SuiteRoom(++roomCounter); break;
                default:
                    System.out.println("❌ เลือกห้องไม่ถูกต้อง");
                    continue;
            }

            bookedRooms.add(room);
            room.bookRoom();
        }

        if (bookedRooms.isEmpty()) {
            System.out.println("❌ คุณยังไม่ได้เลือกห้อง!");
            return;
        }

        // ✅ คำนวณราคารวม
        double totalPrice = 0;
        System.out.println("\n📋 ห้องที่คุณจอง:");
        for (Room room : bookedRooms) {
            System.out.println("- " + room.getRoomType() + " หมายเลข " + roomCounter + " (" + room.getPrice() + " บาท)");
            totalPrice += room.getPrice();
        }
        System.out.println("\n💰 ยอดรวมที่ต้องชำระ: " + totalPrice + " บาท");

        // ✅ เลือกช่องทางชำระเงิน
        System.out.println("\n💰 กรุณาเลือกวิธีการชำระเงิน:");
        System.out.println("1. เงินสด");
        System.out.println("2. บัตรเครดิต");
        System.out.println("3. e-Wallet");
        System.out.print("👉 ป้อนหมายเลขที่ต้องการ: ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine(); // Handle newline

        Payment payment = null;
        switch (paymentMethod) {
            case 1:
                payment = new CashPayment(totalPrice);
                break;
            case 2:
                System.out.print("💳 ป้อนหมายเลขบัตรเครดิต (16 หลัก): ");
                String cardNumber = scanner.nextLine();
                payment = new CreditCardPayment(totalPrice, cardNumber);
                break;
            case 3:
                System.out.print("📲 ป้อนชื่อ e-Wallet: ");
                String walletName = scanner.nextLine();
                payment = new EWalletPayment(totalPrice, walletName);
                break;
            default:
                System.out.println("❌ เลือกวิธีการชำระเงินไม่ถูกต้อง");
                return;
        }

        // ✅ ดำเนินการชำระเงิน
        payment.processPayment();
        System.out.println(payment);

        scanner.close();
    }
}
