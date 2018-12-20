package net.dankrushen.danknn;

public class DankImageGrid {
    private int width = 1280;
    private int height = 720;

    private int columns = 1;
    private int rows = 1;

    // Imagine these being multiplied by 2, this is used on both sides of the column unless
    private int columnSpacing = 2;
    private int rowSpacing = 2;

    private double extraAutoSpacingPercent = 0;

    private AutoSpacingType autoSpacingType = AutoSpacingType.NONE;

    public enum AutoSpacingType {
        NONE,
        SQUARE,
        SQUARE_PLUS_DEFINED,
        SQUARE_PLUS_PERCENT
    }

    public DankImageGrid() {

    }

    public DankImageGrid(int width, int height) {

        this.width = width;
        this.height = height;
    }

    public DankImageGrid(int width, int height, int columns, int rows) {

        this.width = width;
        this.height = height;

        this.columns = columns;
        this.rows = rows;
    }

    public DankImageGrid(int width, int height, int columns, int rows, int columnSpacing, int rowSpacing) {

        this.width = width;
        this.height = height;

        this.columns = columns;
        this.rows = rows;

        this.columnSpacing = columnSpacing;
        this.rowSpacing = rowSpacing;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public class DankSpacing {
        public final int columnSpacing;
        public final int rowSpacing;

        public DankSpacing(int columnSpacing, int rowSpacing) {
            this.columnSpacing = columnSpacing;
            this.rowSpacing = rowSpacing;
        }
    }

    public DankSpacing getAutoSpacing() {
        switch(getAutoSpacingType()) {
            case NONE:
                return new DankSpacing(getColumnSpacing(), getRowSpacing());
            case SQUARE:
                return getSquareSpacing();
            case SQUARE_PLUS_DEFINED:
                return getSquareSpacing(columnSpacing, rowSpacing);
            case SQUARE_PLUS_PERCENT:
                return getSquareSpacingPercent(getExtraAutoSpacingPercent());
        }

        return new DankSpacing(getColumnSpacing(), getRowSpacing());
    }

    public int getColumnSpacing() {
        return columnSpacing;
    }

    public void setColumnSpacing(int columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    public void setColumnSpacingPercent(double columnSpacingPercent) {
        setColumnSpacing((int)Math.round((((double)getPixelsPerColumn()) * (columnSpacingPercent / 100d)/2d)));
    }

    public int getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(int rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public void setRowSpacingPercent(double rowSpacingPercent) {
        setRowSpacing((int)Math.round((((double)getPixelsPerRow()) * (rowSpacingPercent / 100d))/2d));
    }

    public DankSpacing getSquareSpacing(int extraColumnSpacing, int extraRowSpacing) {
        int minLength = Math.min(getPixelsPerColumn(), getPixelsPerRow());

        int columnSpacing = Math.floorDiv(getPixelsPerColumn() - minLength, 2) + extraColumnSpacing;
        int rowSpacing = Math.floorDiv(getPixelsPerRow() - minLength, 2) + extraRowSpacing;

        return new DankSpacing(columnSpacing, rowSpacing);
    }

    public DankSpacing getSquareSpacing(int extraSpacing) {
        return getSquareSpacing(extraSpacing, extraSpacing);
    }

    public DankSpacing getSquareSpacing() {
        return getSquareSpacing(0);
    }

    public DankSpacing getSquareSpacingPercent(double extraSpacingPercent) {
        int minLength = Math.min(getPixelsPerColumn(), getPixelsPerRow());

        return getSquareSpacing((int)Math.round(((((double)minLength) * (extraSpacingPercent / 100d)))/2d));
    }

    public double getExtraAutoSpacingPercent() {
        return extraAutoSpacingPercent;
    }

    public void setExtraAutoSpacingPercent(double extraAutoSpacingPercent) {
        this.extraAutoSpacingPercent = extraAutoSpacingPercent;
    }

    public AutoSpacingType getAutoSpacingType() {
        return autoSpacingType;
    }

    public void setAutoSpacingType(AutoSpacingType autoSpacingType) {
        this.autoSpacingType = autoSpacingType;
    }

    public int getPixelsPerColumn() {
        return Math.floorDiv(getWidth(), getColumns());
    }

    public int getPixelsPerRow() {
        return Math.floorDiv(getHeight(), getRows());
    }
    
    public class DankDrawSpace {
    	public final int x;
    	public final int y;
    	public final int width;
    	public final int height;
    	
    	public DankDrawSpace(int x, int y, int width, int height) {
    		this.x = x;
    		this.y = y;
    		this.width = width;
    		this.height = height;
    	}
    }
    
    public DankDrawSpace getDrawSpaceAt(int column, int row) {
        DankSpacing spacing = getAutoSpacing();

    	int x = (column * getPixelsPerColumn()) + spacing.columnSpacing;
    	int y = (row * getPixelsPerRow()) + spacing.rowSpacing;
    	
    	int width = getPixelsPerColumn() - spacing.columnSpacing;
    	int height = getPixelsPerRow() - spacing.rowSpacing;
    	
    	return new DankDrawSpace(x, y, width, height);
    }
}
