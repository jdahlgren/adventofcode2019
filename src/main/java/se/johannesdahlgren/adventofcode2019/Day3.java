package se.johannesdahlgren.adventofcode2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import se.johannesdahlgren.adventofcode2019.util.FileToListUtil;
import se.johannesdahlgren.adventofcode2019.util.Point;

public class Day3 {

  private static final String RIGHT = "R";
  private static final String UP = "U";
  private static final String LEFT = "L";
  private static final String DOWN = "D";

  private final List<String> pathCommandsForLines;
  private List<Point> line1;
  private List<Point> line2;


  public Day3(String filePath) {
    pathCommandsForLines = FileToListUtil.getLinePathFromFile(filePath);
  }

  public int findShortestRouteToIntersection() {
    convertPathCommandsToLines();
    List<Point> intersectingPoints = getIntersectingPoints();
    return calculateLeastStepsToIntersection(intersectingPoints);
  }

  public int findDistanceToClosestIntersection() {
    convertPathCommandsToLines();
    List<Point> intersectingPoints = getIntersectingPoints();
    return getSmallestManhattanDistance(intersectingPoints);
  }

  private void convertPathCommandsToLines() {
    line1 = createLine(pathCommandsForLines.get(0));
    line2 = createLine(pathCommandsForLines.get(1));
  }

  private List<Point> createLine(String pathCommandsForLine) {
    List<Point> line = new ArrayList<>();
    Point start = new Point(0, 0);
    line.add(start);

    String[] pathCommand = pathCommandsForLine.split(",");
    for (String step : pathCommand) {
      createPointFromStep(step, line);
    }
    return line;
  }

  private void createPointFromStep(String step, List<Point> line) {
    String direction = step.substring(0, 1);
    int amount = Integer.parseInt(step.substring(1));
    for (int i = 0; i < amount; i++) {
      Point lastPoint = line.get(line.size() - 1);
      Point nextPoint = getNewPointInDirection(direction, lastPoint);
      line.add(nextPoint);
    }
  }

  private Point getNewPointInDirection(String direction, Point lastPoint) {
    switch (direction) {
      case UP:
        return new Point(lastPoint.getX(), lastPoint.getY() + 1);
      case RIGHT:
        return new Point(lastPoint.getX() + 1, lastPoint.getY());
      case DOWN:
        return new Point(lastPoint.getX(), lastPoint.getY() - 1);
      case LEFT:
        return new Point(lastPoint.getX() - 1, lastPoint.getY());
    }
    throw new RuntimeException("Unknown direction");
  }

  private List<Point> getIntersectingPoints() {
    Point ignore = new Point(0, 0);
    HashSet<Point> firstLineWithoutDuplicates = new HashSet<>(line1);
    HashSet<Point> secondLineWithoutDuplicates = new HashSet<>(line2);
    firstLineWithoutDuplicates.retainAll(secondLineWithoutDuplicates);
    firstLineWithoutDuplicates.remove(ignore);

    return new ArrayList<>(firstLineWithoutDuplicates);
  }

  private int calculateLeastStepsToIntersection(List<Point> intersectingPoints) {
    int minSteps = Integer.MAX_VALUE;
    for (Point intersectingPoint : intersectingPoints) {
      int stepsToIntersection = calculateLeastStepsToIntersection(intersectingPoint);
      if (minSteps > stepsToIntersection) {
        minSteps = stepsToIntersection;
      }
    }
    return minSteps;
  }

  private int calculateLeastStepsToIntersection(Point point) {
    return line1.indexOf(point) + line2.indexOf(point);
  }

  private int getSmallestManhattanDistance(List<Point> intersectingPoints) {
    int minDistance = 99999999;
    for (Point point : intersectingPoints) {
      int distance = calculateManhattanDistance(point);
      if (minDistance > distance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }

  private int calculateManhattanDistance(Point point) {
    return Math.abs(point.getX()) + Math.abs(point.getY());
  }
}
