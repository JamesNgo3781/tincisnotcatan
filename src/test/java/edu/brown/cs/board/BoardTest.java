package edu.brown.cs.board;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.brown.cs.catan.GameSettings;
import edu.brown.cs.catan.HumanPlayer;
import edu.brown.cs.catan.Player;

public class BoardTest {

  @Test
  public void InitializationTest() {
    Board b = new Board(new GameSettings());
    assertTrue(b.getTiles().size() == 37);
    assertTrue(b.getIntersections().size() == 54);
    assertTrue(b.getPaths().size() == 72);
  }

  @Test
  public void TileTest() {
    Board b = new Board(new GameSettings());

    Collection<Tile> tiles = b.getTiles();
    Map<IntersectionCoordinate, Intersection> ints = new HashMap<IntersectionCoordinate, Intersection>();
    Map<PathCoordinate, Path> paths = new HashMap<PathCoordinate, Path>();

    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 0, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(1, 0, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 1, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 0, 1), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(2, 0, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 2, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 0, 2), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(1, 1, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(1, 0, 1), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 1, 1), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(2, 2, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 2, 2), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(2, 0, 2), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 1, 2), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(1, 0, 2), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(2, 0, 1), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 2, 1), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(2, 1, 0), ints,
        paths, TileType.DESERT)));
    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(1, 2, 0), ints,
        paths, TileType.DESERT)));
  }

  @Test
  public void IntersectionTest() {
    Board b = new Board(new GameSettings());

    Map<IntersectionCoordinate, Intersection> intersections = b
        .getIntersections();

    // Random Place on Board
    assertTrue(intersections.containsKey(new IntersectionCoordinate(
        new HexCoordinate(0, 0, 2), new HexCoordinate(0, 0, 1),
        new HexCoordinate(1, 0, 2))));

    // Edge of Board
    assertTrue(intersections.containsKey(new IntersectionCoordinate(
        new HexCoordinate(2, 2, 0), new HexCoordinate(1, 2, 0),
        new HexCoordinate(2, 3, 0))));

    // Center of Board
    assertTrue(intersections.containsKey(new IntersectionCoordinate(
        new HexCoordinate(0, 0, 0), new HexCoordinate(1, 0, 0),
        new HexCoordinate(1, 1, 0))));
  }

  @Test
  public void PathTest() {
    Board b = new Board(new GameSettings());

    Map<PathCoordinate, Path> paths = b.getPaths();

    // Random Place on Board
    assertTrue(paths.containsKey(new PathCoordinate(new IntersectionCoordinate(
        new HexCoordinate(0, 0, 2), new HexCoordinate(0, 1, 2),
        new HexCoordinate(0, 0, 1)), new IntersectionCoordinate(
        new HexCoordinate(0, 0, 2), new HexCoordinate(0, 0, 1),
        new HexCoordinate(1, 0, 2)))));

    // Edge of Board
    assertTrue(paths.containsKey(new PathCoordinate(new IntersectionCoordinate(
        new HexCoordinate(0, 2, 0), new HexCoordinate(0, 3, 0),
        new HexCoordinate(0, 3, 1)), new IntersectionCoordinate(
        new HexCoordinate(0, 2, 0), new HexCoordinate(0, 3, 0),
        new HexCoordinate(1, 3, 0)))));

    // Center of Board
    assertTrue(paths.containsKey(new PathCoordinate(new IntersectionCoordinate(
        new HexCoordinate(0, 0, 0), new HexCoordinate(1, 0, 1),
        new HexCoordinate(1, 0, 0)), new IntersectionCoordinate(
        new HexCoordinate(0, 0, 0), new HexCoordinate(1, 0, 0),
        new HexCoordinate(1, 1, 0)))));

    for (Intersection i : b.getIntersections().values()) {
      assertTrue(i.getPaths().size() >= 2);
    }

    for (Path p : b.getPaths().values()) {
      assertTrue(p.getStart().getBuilding() == null);
      assertTrue(p.getEnd().getBuilding() == null);
    }
  }

  @Test
  public void PortTest() {
    Board b = new Board(new GameSettings());

    Collection<Tile> tiles = b.getTiles();
    Map<IntersectionCoordinate, Intersection> ints = new HashMap<IntersectionCoordinate, Intersection>();
    Map<PathCoordinate, Path> paths = new HashMap<PathCoordinate, Path>();

    assertTrue(tiles.contains(new Tile(0, new HexCoordinate(0, 0, 3), ints,
        paths, TileType.DESERT)));

    Map<HexCoordinate, Tile> tileMap = new HashMap<HexCoordinate, Tile>();
    for (Tile t : tiles) {
      tileMap.put(t.getCoordinate(), t);
    }
    // Tile portTile = tileMap.get(new HexCoordinate(0, 0, 3));
    // assertTrue(portTile.getIntersections().size() == 2);
    // assertTrue(portTile.getIntersections().iterator().next().getPort() !=
    // null);
    // Iterator<Intersection> iter = portTile.getIntersections().iterator();
    // assertTrue(iter.next().getPort().getResource() == Resource.WILDCARD);
    // assertTrue(iter.next().getPort().getResource() == Resource.WILDCARD);
    //
    // Tile portTile2 = tileMap.get(new HexCoordinate(0, 2, 3));
    // assertTrue(portTile2.getIntersections().size() == 2);
    // assertTrue(portTile2.getIntersections().iterator().next().getPort() !=
    // null);
    // Iterator<Intersection> iter2 = portTile2.getIntersections().iterator();
    // assertTrue(iter2.next().getPort().getResource() == Resource.WHEAT);
    // assertTrue(iter2.next().getPort().getResource() == Resource.WHEAT);
    //
    // Tile portTile3 = tileMap.get(new HexCoordinate(3, 0, 2));
    // assertTrue(portTile3.getIntersections().size() == 2);
    // assertTrue(portTile3.getIntersections().iterator().next().getPort() !=
    // null);
    // Iterator<Intersection> iter3 = portTile3.getIntersections().iterator();
    // assertTrue(iter3.next().getPort().getResource() == Resource.WILDCARD);
    // assertTrue(iter3.next().getPort().getResource() == Resource.WILDCARD);
  }

  @Test
  public void testLongestRoadNoRoad() {
    Board b = new Board(new GameSettings());
    HexCoordinate h1 = new HexCoordinate(0, 0, 0);
    HexCoordinate h2 = new HexCoordinate(-1, 0, 0);
    HexCoordinate h3 = new HexCoordinate(0, 0, 1);
    IntersectionCoordinate start = new IntersectionCoordinate(h1, h2, h3);

    HexCoordinate h4 = new HexCoordinate(0, 0, 0);
    HexCoordinate h5 = new HexCoordinate(0, 0, 1);
    HexCoordinate h6 = new HexCoordinate(0, -1, 0);
    IntersectionCoordinate end = new IntersectionCoordinate(h4, h5, h6);

    PathCoordinate pathCoord = new PathCoordinate(start, end);
    Player player = new HumanPlayer(0, "", "");

    b.getIntersections().get(start).placeSettlement(player);
    Path path = b.getPaths().get(pathCoord);
    assertTrue(path.getLongestPath(player) == 0);
  }

  @Test
  public void testLongestRoadOneRoad() {
    Board b = new Board(new GameSettings());
    HexCoordinate h1 = new HexCoordinate(0, 0, 0);
    HexCoordinate h2 = new HexCoordinate(-1, 0, 0);
    HexCoordinate h3 = new HexCoordinate(0, 0, 1);
    IntersectionCoordinate start = new IntersectionCoordinate(h1, h2, h3);

    HexCoordinate h4 = new HexCoordinate(0, 0, 0);
    HexCoordinate h5 = new HexCoordinate(0, 0, 1);
    HexCoordinate h6 = new HexCoordinate(0, -1, 0);
    IntersectionCoordinate end = new IntersectionCoordinate(h4, h5, h6);

    PathCoordinate pathCoord = new PathCoordinate(start, end);
    Player player = new HumanPlayer(0, "", "");

    b.getIntersections().get(start).placeSettlement(player);
    Path path = b.getPaths().get(pathCoord);
    assertTrue(path.canPlaceRoad(player));
    path.placeRoad(player);
    assertTrue(b.longestPath(player) == 1);
  }

  @Test
  public void testLongestRoadTwoRoads() {
    Board b = new Board(new GameSettings());
    HexCoordinate h1 = new HexCoordinate(0, 0, 0);
    HexCoordinate h2 = new HexCoordinate(-1, 0, 0);
    HexCoordinate h3 = new HexCoordinate(0, 0, 1);
    IntersectionCoordinate start = new IntersectionCoordinate(h1, h2, h3);

    HexCoordinate h4 = new HexCoordinate(0, 0, 0);
    HexCoordinate h5 = new HexCoordinate(0, 0, 1);
    HexCoordinate h6 = new HexCoordinate(0, -1, 0);
    IntersectionCoordinate end = new IntersectionCoordinate(h4, h5, h6);

    HexCoordinate h7 = new HexCoordinate(0, -1, 1);
    HexCoordinate h8 = new HexCoordinate(0, 0, 1);
    HexCoordinate h9 = new HexCoordinate(0, -1, 0);
    IntersectionCoordinate end2 = new IntersectionCoordinate(h7, h8, h9);

    PathCoordinate firstCoord = new PathCoordinate(start, end);
    PathCoordinate secondCoord = new PathCoordinate(end, end2);
    Player player = new HumanPlayer(0, "", "");

    b.getIntersections().get(start).placeSettlement(player);
    Path path = b.getPaths().get(firstCoord);
    assertTrue(path.canPlaceRoad(player));
    path.placeRoad(player);
    assertTrue(b.getPaths().get(secondCoord).canPlaceRoad(player));
    b.getPaths().get(secondCoord).placeRoad(player);
    assertTrue(b.longestPath(player) == 2);
  }

}
