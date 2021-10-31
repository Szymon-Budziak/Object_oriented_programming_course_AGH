package agh.ics.oop;

import java.util.List;

public class World {
    public static void main(String[] args) {
        // Labs 1
//        System.out.println("Start");
//        Direction[] newArgs;
//        newArgs = run_enum(args);
//        run(newArgs);
//        System.out.println("Stop");

        // Labs 2
//        Vector2d position1 = new Vector2d(1, 2);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2, 1);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));
//
//        System.out.println(MapDirection.SOUTH);
//        System.out.println(MapDirection.SOUTH.next());
//        System.out.println(MapDirection.SOUTH.previous());
//        System.out.println(MapDirection.SOUTH.toUnitVector());

        // Labs 3
//        Animal animal = new Animal();
//        System.out.println(animal);
//        String[] directions = new String[]{"r", "forward", "f", "left", "b"};
//        MoveDirection[] newDirections = OptionsParser.parse(directions);
//        for (MoveDirection direct : newDirections) {
//            animal.move(direct);
//            System.out.println(animal);
//        }

        // Labs 4
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }

    public void run(Animal animal, List<MoveDirection> directions) {
        directions.forEach(animal::move);
    }
//    public static Direction[] run_enum(String[] args) {
//        Direction[] directions = new Direction[args.length];
//        for (int i = 0; i < args.length; i++) {
//            directions[i] = switch (args[i]) {
//                case "f" -> Direction.FORWARD;
//                case "b" -> Direction.BACKWARD;
//                case "r" -> Direction.RIGHT;
//                case "l" -> Direction.LEFT;
//                default -> throw new IllegalStateException("Wrong args");
//            };
//        }
//        return directions;
//    }
//
//    public static void run(Direction[] directions) {
//        for (Direction direct : directions) {
//            switch (direct) {
//                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
//                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
//                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
//                case LEFT -> System.out.println("Zwierzak skręca w lewo");
//                default -> {
//                }
//            }
//        }
}