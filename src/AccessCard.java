import java.util.Date;

public abstract class AccessCard implements AccessControl {
    protected String cardID;
    protected String ownerName;
    protected Date expiryDate;

    public abstract boolean validateAccess();
    public abstract void encryptDate();

    @Override
    public boolean grantAccess() {
        return validateAccess();
    }

    @Override
    public boolean revokeAccess() {
        return true;
    }
}