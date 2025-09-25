import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();


    enum Pawn {
        WHITE('w'), BLACK('b'), DEATH('o'), EMPTY('.'), WHITEKING('W'), BLACKKING('B'),
        ;

        char value;

        Pawn(char value) {
            this.value = value;
        }
    }

    enum Player {
        COMPUTER("Копьютер"), USER("Человек"), INITIAL("Пока не известно"),
        ;

        String player;

        Player(String player) {
            this.player = player;
        }
    }


    static int randPlay(int x) { // верно ли я сдесь сделал ретерн
        if (x < 6) activPlayer = Player.USER;
        else activPlayer = Player.COMPUTER;
        return x;
    }

    static void collorPawn(Pawn callor, Pawn[][] allField, int ot, int po) {
        for (int i = ot; i < po; i++) {
            for (int j = 0; j < allField.length; j++) {
                if ((i + j) % 2 == 1 && allField[i][j] == Pawn.EMPTY) {
                    allField[i][j] = callor;
                }
            }
        }
    }

    static void writeMap(Pawn[][] allField) {
        for (int i = 0; i < allField.length; i++) {
            for (int j = 0; j < allField[i].length; j++) {
                System.out.print(allField[i][j].value + " ");
                if (allField[i][j] == Pawn.DEATH) {
                    allField[i][j] = Pawn.EMPTY;
                }
            }
            System.out.println();
        }

    }

    static Player activPlayer;
    static Player winner = Player.INITIAL;
    static int countWhite = 5;
    static int countBlack = 5;

    static void playPawn(Pawn callor, Pawn callorKing, Pawn[][] allField, int x, int y, Pawn killCollor, Pawn killCollorKing, Player gamer, Player expect) {
        int fromStr = scanner.nextInt();
        int fromStlb = scanner.nextInt();

        if (allField[fromStr][fromStlb] == callor) {
            System.out.println("Так нельзя ходить здесь стоит ваша фишка");
        }

        if (allField[fromStr][fromStlb] == Pawn.EMPTY) {
            if (callor == Pawn.BLACK) {
                if (fromStr == x - 1 && (fromStlb == y - 1 || fromStlb == y + 1)) {
                    allField[x][y] = Pawn.EMPTY;
                    allField[fromStr][fromStlb] = callor;
                    activPlayer = expect;
                }
            }
            if (callor == Pawn.WHITE) {
                if (fromStr == x + 1 && (fromStlb == y - 1 || fromStlb == y + 1)) {
                    allField[x][y] = Pawn.EMPTY;
                    allField[fromStr][fromStlb] = callor;
                    activPlayer = expect;
                }
            }
            if (callor == Pawn.BLACK && fromStr == 0) {
                allField[fromStr][fromStlb] = Pawn.BLACKKING; // превращение в дамку
            }
            if (callor == Pawn.WHITE && fromStr == 7) {
                allField[fromStr][fromStlb] = Pawn.WHITEKING; // превращение в дамку
            }
        } else {
            if (allField[fromStr][fromStlb] == killCollor || allField[fromStr][fromStlb] == killCollorKing) {
                int strRazn = fromStr - (x - fromStr); // на сколько строк
                int stlbRazn = fromStlb - (y - fromStlb);// на сколько столбцов
                if (strRazn >= 0 && strRazn < 8 && stlbRazn >= 0 && stlbRazn < 8) {
                    if (allField[strRazn][stlbRazn] != callor && allField[strRazn][stlbRazn] != killCollor && allField[strRazn][stlbRazn] != callorKing && allField[strRazn][stlbRazn] != killCollorKing) { // если не какая не стоит
                        allField[strRazn][stlbRazn] = callor;
                        allField[fromStr][fromStlb] = Pawn.DEATH;
                        allField[x][y] = Pawn.EMPTY;
                        if (callor == Pawn.BLACK) {
                            countWhite--;
                        } else {
                            countBlack--;
                        }
                        activPlayer = gamer;

                        if (callor == Pawn.BLACK && fromStr == 0) {
                            allField[fromStr][fromStlb] = Pawn.BLACKKING; // превращение в дамку
                        }
                        if (callor == Pawn.WHITE && fromStr == 7) {
                            allField[fromStr][fromStlb] = Pawn.WHITEKING; // превращение в дамку
                        }
                    }
                    System.out.println(activPlayer);
                }
            }
        }
    }

    static void playKingPawn(Pawn callor, Pawn smollCal, Pawn[][] allField, int x, int y, Pawn killCollor, Pawn killCollorKing, Player gamer, Player expect) {
        if (allField[x][y] == callor) {// дамка черная
            int fromStrK = scanner.nextInt();
            int fromStlbK = scanner.nextInt();
            if (allField[fromStrK][fromStlbK] == Pawn.EMPTY) {
                int raznostStr = Math.abs(x - fromStrK);       // узнаем диагональ ли это
                int raznostStlb = Math.abs(y - fromStlbK);
                if (raznostStr == raznostStlb) {
                    allField[x][y] = Pawn.EMPTY;
                    allField[fromStrK][fromStlbK] = callor;
                    activPlayer = expect;
                }
            }
            if (allField[fromStrK][fromStlbK] == killCollor || allField[fromStrK][fromStlbK] == killCollorKing) {
                int stepX = (x < fromStrK) ? 1 : -1; // если наша строка меньше той куда идем тогда плюсуем строку
                int stepY = (y < fromStlbK) ? 1 : -1;
                if (fromStrK >= 0 && fromStrK < 8 && fromStlbK >= 0 && fromStlbK < 8) {
                    if (allField[fromStrK + stepX][fromStlbK + stepY] != smollCal && allField[fromStrK + stepX][fromStlbK + stepY] != killCollor && allField[fromStrK + stepX][fromStlbK + stepY] != callor && allField[fromStrK + stepX][fromStlbK + stepY] != killCollorKing) {
                        allField[fromStrK + stepX][fromStlbK + stepY] = callor;
                        allField[fromStrK][fromStlbK] = Pawn.DEATH;
                        allField[x][y] = Pawn.EMPTY;
                        if (callor == Pawn.BLACKKING) {
                            countWhite--;
                            activPlayer = gamer;
                        } else {
                            countBlack--;
                            activPlayer = expect;
                        }

                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        //Обьявляется ход только в первом раунде
        // Если пешка просто походила то некс след пользователь
        // если пешка побила то ходит еще раз пользователь
        //пешки ходят в одном направлении и не бьют назад а дамки могут бить назад и вперед по диагонали
        Pawn[][] allField = new Pawn[8][8];

        for (int i = 0; i < allField.length; i++) {  // иницилизация пустыми клетками
            for (int j = 0; j < allField[i].length; j++) {
                allField[i][j] = Pawn.EMPTY;
            }
        }

        collorPawn(Pawn.WHITE, allField, 0, 2); //инициализация пешек белых
        collorPawn(Pawn.BLACK, allField, 6, 8); // инициализация пешек черных


//        for (int j = 0; j < allField.length; j += 2) {  //тест дамок белых
//            if (allField[3][j] == Pawn.EMPTY) {
//                allField[3][j] = Pawn.WHITEKING;
//            }
//        }


        for (int i = 0; i < allField.length; i++) {
            for (int j = 0; j < allField[i].length; j++) {
                System.out.print(allField[i][j].value + " "); // все пешки
            }
            System.out.println();
        }


        randPlay(10); // кто ходит


        while (countWhite != 0 && countBlack != 0) {
            if (activPlayer == Player.USER) {
                int str = scanner.nextInt();
                int stlb = scanner.nextInt();
                if (allField[str][stlb] == Pawn.EMPTY) {
                    System.out.println("Здесь фишки нет");
                }
                if (allField[str][stlb] == Pawn.BLACK) { // если эту строку убрать то почемуто при хотьбе дамкой она становится пешкой (если просто без иф 2 строки сначала пешка потом дамка
                    playPawn(Pawn.BLACK, Pawn.BLACKKING, allField, str, stlb, Pawn.WHITE, Pawn.WHITEKING, Player.USER, Player.COMPUTER);
                }

                if (allField[str][stlb] == Pawn.BLACKKING) {
                    playKingPawn(Pawn.BLACKKING, Pawn.BLACK, allField, str, stlb, Pawn.WHITE, Pawn.WHITEKING, Player.USER, Player.COMPUTER);
                }
                writeMap(allField);
            }

            if (activPlayer == Player.COMPUTER) {// если компьютер
                int str = scanner.nextInt();
                int stlb = scanner.nextInt();
                if (allField[str][stlb] == Pawn.EMPTY) {
                    System.out.println("Здесь фишки нет");
                }
                if (allField[str][stlb] == Pawn.WHITE) {
                    playPawn(Pawn.WHITE, Pawn.WHITEKING, allField, str, stlb, Pawn.BLACK, Pawn.BLACKKING, Player.COMPUTER, Player.USER);
                }
                if (allField[str][stlb] == Pawn.WHITEKING) {
                    playKingPawn(Pawn.WHITEKING, Pawn.WHITE, allField, str, stlb, Pawn.BLACK, Pawn.BLACKKING, Player.COMPUTER, Player.USER);
                }
                writeMap(allField);
            }
            if (countWhite == 0) {
                winner = Player.USER;
                System.out.println("Победитель: " + winner.player);
            }
            if (countBlack == 0) {
                winner = Player.COMPUTER;
                System.out.println("Победитель: " + winner.player);
            }
        }
    }
}












