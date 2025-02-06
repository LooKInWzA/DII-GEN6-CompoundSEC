class BudgetHotel extends HotelManagement {

    public BudgetHotel(String hotelName, int roomCount, double roomRate) {
        super(hotelName, roomCount, roomRate);
    }

    @Override
    public void bookRoom(int rooms) {
        if (roomCount >= rooms) {
            roomCount -= rooms;
            System.out.println("จองห้อง " + rooms + " ห้องสำเร็จในโรงแรมราคาประหยัด");
        } else {
            System.out.println("ห้องพักไม่เพียงพอในโรงแรมราคาประหยัด");
        }
    }

    @Override
    public void cancelBooking(int rooms) {
        roomCount += rooms;
        System.out.println("ยกเลิกการจองห้อง " + rooms + " ห้องในโรงแรมราคาประหยัดสำเร็จ");
    }

    @Override
    public void checkIn(String guestName) {
        System.out.println(guestName + " เช็คอินที่ " + hotelName + " สำเร็จ");
    }

    @Override
    public void checkOut(String guestName) {
        System.out.println(guestName + " เช็คเอาท์จาก " + hotelName + " สำเร็จ");
    }
}
