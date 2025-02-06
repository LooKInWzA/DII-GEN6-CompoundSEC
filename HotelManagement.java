abstract class HotelManagement {

    protected String hotelName;
    protected int roomCount;
    protected double roomRate;

    // คอนสตรัคเตอร์
    public HotelManagement(String hotelName, int roomCount, double roomRate) {
        this.hotelName = hotelName;
        this.roomCount = roomCount;
        this.roomRate = roomRate;
    }

    // เมธอด abstract สำหรับการจองห้องพัก
    public abstract void bookRoom(int rooms);

    // เมธอด abstract สำหรับการยกเลิกการจองห้อง
    public abstract void cancelBooking(int rooms);

    // เมธอด abstract สำหรับการเช็คอิน
    public abstract void checkIn(String guestName);

    // เมธอด abstract สำหรับการเช็คเอาท์
    public abstract void checkOut(String guestName);

    // เมธอดที่แสดงรายละเอียดโรงแรม
    public void showHotelDetails() {
        System.out.println("โรงแรม: " + hotelName);
        System.out.println("จำนวนห้องทั้งหมด: " + roomCount);
        System.out.println("ราคาห้อง: " + roomRate + " บาท");
    }
}
