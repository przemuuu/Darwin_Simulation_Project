package agh.ics.oop.model.utilities;

import agh.ics.oop.model.entities.FertileField;
import agh.ics.oop.model.entities.Grass;

import java.util.*;

import static java.lang.Math.min;

public class RandomGrassPositionGenerator implements Iterable<Vector2d> {
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private final int NUMBER_OF_GRASS;
    protected final Map<Vector2d, FertileField> fertileFields;
    protected final Map<Vector2d, Grass> grassMap;

    public RandomGrassPositionGenerator(int maxWidth, int maxHeight, int grassCount, Map<Vector2d, FertileField> fertileFields, Map<Vector2d, Grass> grassMap) {
        this.MAX_WIDTH = maxWidth;
        this.MAX_HEIGHT = maxHeight;
        this.NUMBER_OF_GRASS = grassCount;
        this.fertileFields = fertileFields;
        this.grassMap = grassMap;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        List<Vector2d> positions = generateGrassPositions();
        return positions.iterator();
    }

    public List<Vector2d> generateRandomPositions() {
        List<Vector2d> positions = new ArrayList<>();
        for (int x = 0; x <= MAX_WIDTH; x++) {
            for (int y = 0; y <= MAX_HEIGHT; y++) {
                positions.add(new Vector2d(x, y));
            }
        }
        Collections.shuffle(positions);
        return positions;
    }

    public List<Vector2d> generateGrassPositions() {
        List<Vector2d> grassPositions = new ArrayList<>();
        Random random = new Random();
        for (FertileField field : fertileFields.values()) {
            Vector2d activePosition = field.getPosition();
            if (!grassMap.containsKey(activePosition)) {
                if (random.nextDouble() < 0.8) {
                    grassPositions.add(activePosition);
                }
            }
        }
        if (grassPositions.size() < NUMBER_OF_GRASS) {
            List<Vector2d> randomPositions = generateRandomPositions();
            for (Vector2d randomPosition : randomPositions) {
                if (!grassMap.containsKey(randomPosition) && !grassPositions.contains(randomPosition) && !fertileFields.containsKey(randomPosition)) {
                    if (random.nextDouble() < 0.2) {
                        grassPositions.add(randomPosition);
                    }
                }
                if (grassPositions.size() == NUMBER_OF_GRASS) {
                    break;
                }
            }
        }
        return grassPositions.subList(0, min(NUMBER_OF_GRASS,grassPositions.size()));
    }
}
