package sample;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Here is the swap
     * @param obj
     * @return true if the compared object is the same class
     */
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final Location other = (Location) obj;
        if (this.x != other.x){
            return false;
        }
        return this.y == other.y;
    }

    public Location offset(Direction direction){
        return new Location(x + direction.getX(), y = direction.getY());
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString(){
        return "Location [x = " + this.x + ", y = " + this.y + "}";
    }

}
