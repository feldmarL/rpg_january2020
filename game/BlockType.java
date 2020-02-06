public enum BlockType { // Enum to check values in map.txt, set it's appearance and if it's solid (can player cross it or not)
    GRASS(false, '0'),
    SPAWN(false, 'S'),
    ENEMY(false, 'E'),
    TRAP(false, 'T'),
    BONUS(false, 'B'),
    BRICK(true, '1');

    private boolean solid;
    private char c;
    
    BlockType(boolean solid, char c){
        this.solid = solid;
        this.c = c;
    }
    public boolean isSolid(){
        return this.solid;
    }

    public static BlockType getByChar(char c) throws NullPointerException {
        for(BlockType blockType : values())
            if(blockType.c == c)
            return blockType;
        throw new NullPointerException("Char "+c+" is not linked to a BlockType !");
    }
}
