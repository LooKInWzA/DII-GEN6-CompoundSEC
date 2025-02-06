class LuxuryHotel extends HotelManagement {

    public LuxuryHotel(String hotelName, int roomCount, double roomRate) {
        super(hotelName, roomCount, roomRate);
    }

    @Override
    public void bookRoom(int rooms) {
        if (roomCount >= rooms) {
            roomCount -= rooms;
            System.out.println("จองห้อง " + rooms + " ห้องสำเร็จ");
        } else {
            System.out.println("ไม่สามารถจองห้องได้ เนื่องจากห้องไม่เพียงพอ");
        }
    }

    @Override
    public void cancelBooking(int rooms) {
        roomCount += rooms;
        System.out.println("ยกเลิกการจอง " + rooms + " ห้องสำเร็จ");
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
