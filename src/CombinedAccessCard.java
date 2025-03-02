import java.util.Date;
import java.util.Set;

public class CombinedAccessCard extends AccessCard {
    Set<Integer> allowedFloors;
    Set<Integer> allowedRooms;

    public CombinedAccessCard(String id, String owner, Date expiry, Set<Integer> floors, Set<Integer> rooms) {
        this.cardID = id;
        this.ownerName = owner;
        this.expiryDate = expiry;
        this.allowedFloors = floors;
        this.allowedRooms = rooms;
    }

    public boolean canAccessFloor(int floor) {
        return allowedFloors.contains(floor);
    }

    public boolean canAccessRoom(int room) {
        return allowedRooms.contains(room);
    }

    @Override
    public boolean validateAccess() {
        Date currentDate = new Date();
        return currentDate.before(expiryDate) &&
                (!allowedFloors.isEmpty() || !allowedRooms.isEmpty());
    }

    @Override
    public void encryptDate() {
        // Encryption logic
    }
}