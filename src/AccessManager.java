import java.util.*;

class AccessManager {
    private List<AccessCard> cards = new ArrayList<>();

    public CombinedAccessCard createCombinedCard(String id, String owner, Date expiry, Set<Integer> floors, Set<Integer> rooms) {
        return new CombinedAccessCard(id, owner, expiry, floors, rooms);
    }

    public void addCard(AccessCard card) {
        cards.add(card);
        AccessLog.recordAccess("Card added: " + card.cardID);
    }

    public void revokeCard(String cardID) {
        cards.removeIf(c -> c.cardID.equals(cardID));
        AccessLog.recordAccess("Card revoked: " + cardID);
    }

    public boolean checkRoomAccess(CombinedAccessCard card, Room room) {
        return card.canAccessFloor(room.getFloor().getFloorNumber()) &&
                card.canAccessRoom(room.getRoomNumber());
    }

    public List<AccessCard> getCards() {
        return cards;
    }
}