package net.bitnine.jwt;

public enum State {

    VALID ("valid"),
    INVALID ("invalid");

    private final String name;       

    private State(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
