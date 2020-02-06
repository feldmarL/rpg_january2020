public class BlockLocation { 
    private int x,
                y;

    public BlockLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public BlockType getType(){
        return GameMain.getInstance().getGame().getBlocks().get(hashCode());
    }

    @Override
    public int hashCode() {
        return y*12+x;
    }
}
