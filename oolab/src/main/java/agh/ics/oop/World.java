package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        Direction[] newArgs;
        newArgs = run_enum(args);
        run(newArgs);
        System.out.println("Stop");
    }

    public static Direction[] run_enum(String[] args) {
        Direction[] directions = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            directions[i] = switch (args[i]) {
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "r" -> Direction.RIGHT;
                case "l" -> Direction.LEFT;
                default -> throw new IllegalStateException("Wrong args");
            };
        }
        return directions;
    }

    public static void run(Direction[] directions) {
        for (Direction direct : directions) {
            switch (direct) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
                default -> {
                }
            }
        }
    }
}
