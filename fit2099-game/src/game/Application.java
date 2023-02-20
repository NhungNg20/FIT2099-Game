package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.actors.enemies.Bowser;
import game.actors.enemies.Koopa;
import game.actors.friendlies.PrincessPeach;
import game.actors.friendlies.Toad;
import game.grounds.*;
import game.grounds.fountains.HealthFountain;
import game.grounds.fountains.PowerFountain;
import game.grounds.highgrounds.Wall;
import game.grounds.highgrounds.WarpPipe;
import game.grounds.trees.Mature;
import game.grounds.trees.Sapling;
import game.grounds.trees.Sprout;
import game.items.*;
import game.items.magical.Bottle;
import game.items.magical.PowerStar;
import game.items.magical.SuperMushroom;

/**
 * The main class for the Mario World game.
 *
 */
public class Application {

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(
				new Dirt(), new Wall(), new Floor(), new WarpPipe(),
				new Sprout(), new Sapling(), new Mature(),new Lava(),
				new PowerFountain(), new HealthFountain() );

		List<String> mainMapStr = Arrays.asList(
				"..........................................##..........+.........................",
				"............+............+..................#...................................",
				"............................................#...................................",
				".............................................##......................+..........",
				"...........................+...................#...............H................",
				"................................................#..........+....................",
				".................+...................+.....H......#.............................",
				"............H....................................##.............................",
				"............................+...................##..............................",
				".........+..............................+#____####.................+............",
				".................+.....................+#_____###++.............................",
				".......................................+#______###.........+....................",
				"......................+.................+#_____###..............................",
				"........................+........................##.............+...............",
				"...................................................#.........................+..",
				"...+........................................A.......#...........................",
				"...................+.................................#.............+............",
				"....................................+.................#.........................",
				"................................................+......##.......................");
		GameMap mainMap = new GameMap(groundFactory, mainMapStr);
		world.addGameMap(mainMap);

		List<String> lavaMapStr = Arrays.asList(
				"............................##...............",
				"............................##.......L.......",
				"...+.....................L..........___......",
				"..........+.............######.....__C__.....",
				"................LL.....#.+..#.##....___......",
				".......................##..#....##...........",
				".......#....#####...A....##......###.........",
				"......####.+#####......##...........##.......",
				".L...######LL....###++##.............#..+..##",
				"....#####........##++##.......+......##..##..",
				"#####........LL........................##....",
				"###.......................................H..",
				"###...............###L.............LL........",
				"##.......+.......#___#.............LL........",
				".................#___#......+................",
				"................#.....#......................",
				".............................................");

		GameMap lavaMap = new GameMap(groundFactory, lavaMapStr);
		world.addGameMap(lavaMap);

		makeWarpPipePair(mainMap ,lavaMap,70,16,37,3);	// warp between maps
		makeWarpPipePair(mainMap ,mainMap,3,3,70,3);
		makeWarpPipePair(mainMap ,mainMap,20,10,48,13);

		Actor mario = new Player("Mario", 'm', 100);
		mario.addItemToInventory(new Bottle());
		world.addPlayer(mario, mainMap.at(42, 10));
		// world.addPlayer(mario, lavaMap.at(37, 3)); // Places Mario on WarpPipe in lavaMap. Comment out above line to use
		lavaMap.at(19, 15).addActor(new Bowser(lavaMap));
		lavaMap.at(19, 13).addActor(new PrincessPeach());
		mainMap.at(44,10).addActor(new Toad());

		/*  Test items/ground
		mainMap.at(42,9).addItem(new PowerStar());
		mainMap.at(42,11).addItem(new SuperMushroom());
		mainMap.at(43,11).addItem(new Coin(5000));
		mario.addItemToInventory(new Wrench());
		mainMap.at(35, 10).addItem(new Wrench());
		mainMap.at(42,12).addActor(new Koopa(mainMap));
		mainMap.at(42, 11).addItem(new FireFlower());
		mainMap.at(43, 12).setGround(new PowerFountain());
		mainMap.at(42,12).setGround(new HealthFountain());
		*/
		world.run();
	}

	/**
	 * Creates a pair of WarpPipes and links them together so that actor can warp between them
	 * @param map1 map where WarpPipe 1 is to be located
	 * @param map2 map where WarpPipe 2 is to be located
	 * @param x1 X coordinate of WarpPipe 1
	 * @param y1 Y coordinate of WarpPipe 1
	 * @param x2 X coordinate of WarpPipe 2
	 * @param y2 Y coordinate of WarpPipe 2
	 */
	public static void makeWarpPipePair(GameMap map1, GameMap map2, int x1,int y1,int x2,int y2){
		WarpPipe w1 = new WarpPipe(map1.at(x1,y1));
		WarpPipe w2 = new WarpPipe(map2.at(x2,y2));
		w1.setPair(w2);
		w2.setPair(w1);
		w1.getLocation().setGround(w1);
		w2.getLocation().setGround(w2);
	}
}
