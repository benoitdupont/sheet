package be.dupont;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Sheet {
    
    private static final Logger logger = LoggerFactory.getLogger(Sheet.class);
    
    Map<Position, Part> parts = new HashMap<>();
    private int length;
    private int width;

    public Sheet(Sheet sheet) {
        this.length = sheet.length;
        this.width = sheet.width;
        this.parts.putAll(sheet.parts);
    }

    public Sheet() {
    }

    /**
     * Two parts intersect iff
     * // Let's stay that this intersect because we want a value of 1 unity (1cm) between two piececs (width of the blade)
     */
    static boolean intersect(Position key, Part value, Position candidate, Part part) {
        List<Position> theFirst = getPositionsFrom(value.getWidth(), value.getLength(), key.getX(), key.getY());
        List<Position> theSecond = getPositionsFrom(part.getWidth(), part.getLength(), candidate.getX(), candidate.getY());

        if (theFirst.removeAll(theSecond)) {
            return true;
        }

        if (theSecond.removeAll(theFirst)) {
            return true;
        }

        return false;
    }

    static List<Position> getPositionsFrom(int width, int length, int startX, int startY) {
        List<Position> allPositions = new ArrayList<>();

        for (int i = startX; i <= startX + width; i++) {
            for (int j = startY; j <= startY + length; j++) {
                allPositions.add(new Position(i, j));
            }
        }
        return allPositions;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Sheet{" +
                "length=" + length +
                ", width=" + width +
                ", parts=" + parts +
                '}';
    }

    public Map<Position, Part> getParts() {
        return parts;
    }

    /**
     * A sheet is valid iff the size of it's component is lower than the base size
     *
     * TODO this is false, it doesn't take the position into account
     */
    public boolean isValid() {

        if(true) return true;
        logger.info("Base sheet of size: " + length + " * " + width);

        int partsLenghts = 0;
        int partsWidth = 0;
        for (Part part : parts.values()) {
            partsLenghts += part.getLength();

            partsWidth += part.getWidth();
        }

        logger.info("Verifying partsLength with base length: " + partsLenghts + " should be < " + length);
        logger.info("Verifying partsWidth with base width: " + partsWidth + " should be < " + width);

        return partsLenghts < length && partsWidth < width;
    }

    /**
     * @return a copy of the list with the parts
     */
    public Sheet addParts2Sheet(List<Part> parts) {
        Sheet newSheet = new Sheet(this);

        for (Part part : parts) {
            Position position = getPosition(part, newSheet);

            if(position == null){
                throw new IllegalStateException("Cannot add part, no position found");
            }

            if(newSheet.getParts().containsKey(position)){
                throw new IllegalStateException("two parts are being added to the same position");
            }
            newSheet.getParts().put(position, part);
        }

        logger.info(newSheet.toString());

        // This occurs when  the algo cannot do place parts
        if (newSheet.getParts().size() < parts.size()) {
            throw new IllegalStateException("Cannot place all the parts in sheet");
        }

        return newSheet;
    }

    /**
     * Find a valid position in the sheet
     */
    private Position getPosition(Part part, Sheet newSheet) {
        List<Position> candidates = getAllValidPositions(part, newSheet);
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }

        return candidates.get(0);
    }

    private List<Position> getAllValidPositions(Part part, Sheet newSheet) {
        // Get all positions in the sheet
        List<Position> candidates = getAllPositionsFromSheet();

        // Get only the ones that can contains the part without exceeding the sheet size
        List<Position> newCandidates = candidates.stream().filter(p -> isPartValidInSheet(p, part, length, width)).collect(Collectors.toList());

        // Then, only the ones which are not intersecting with others
        List<Position> lastCandidates = new ArrayList<>();
        for (Position candidate : newCandidates) {

            if (!isPartCollidingWithOthersPartsInSheet(candidate, part, newSheet)) {
                lastCandidates.add(candidate);
            }
        }

        return lastCandidates;
    }

    /**
     * Note that we should use the newSheet here instead of this otherwise we won't get the parts already added
     */
    private boolean isPartCollidingWithOthersPartsInSheet(Position candidate, Part part, Sheet newSheet) {
        for (Map.Entry<Position, Part> entry : newSheet.getParts().entrySet()) {
            if (intersect(entry.getKey(), entry.getValue(), candidate, part)) {
                return true;
            }
        }

        return false;
    }

    private List<Position> getAllPositionsFromSheet() {
        List<Position> allPositions = getPositionsFrom(width, length, 0, 0);

        return allPositions;
    }

    public boolean isSheetValidWithPositions() {
        for (Position position : parts.keySet()) {
            // check length is not out of bound
            if (!isPartValidInSheet(position, parts.get(position), length, width)) return false;
        }

        // TODO must check that no intersections between parts

        return true;
    }

    static boolean isPartValidInSheet(Position position, Part part, int length, int width) {
        if (position.getY() + part.getLength() > length) {
            logger.debug("position.getX():" + position.getX() + " part.getLength():" + part.getLength() + " length:" + length);
            return false;
        }

        if (position.getX() + part.getWidth() > width) {
            logger.debug("position.getY():" + position.getY() + " part.getWidth():" + part.getWidth() + " width" + width);
            return false;
        }
        return true;
    }

    /**
     * Area of the sheet minus the sum of area of all the parts
     */
    long getLostSpaceInSheet() {
        int sheetArea = getLength() * getWidth();

        Integer sumOfPartArea = getParts().values().stream()
                .map(p -> p.getWidth() * p.getLength())
                .reduce(0, Integer::sum);

        double diff = sheetArea - sumOfPartArea;
        return Math.round(diff/sheetArea * 100);
    }

    static class Position {
        int x;

        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {

            return Objects.hash(x, y);
        }
    }

}
