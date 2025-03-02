class Room {
    private int roomNumber;
    private Floor floor;

    public Room(int roomNumber, Floor floor) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        floor.addRoom(this);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Floor getFloor() {
        return floor;
    }
}