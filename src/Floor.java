import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private List<Room> rooms = new ArrayList<>();

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public boolean removeRoom(int roomNumber) {
        return rooms.removeIf(r -> r.getRoomNumber() == roomNumber);
    }
}