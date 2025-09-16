import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    enum Pawn {
        WHITE('w'),
        BLACK('b'),
        DEATH('o'),
        EMPTY('.'),
        WHITEKING('W'),
        BLACKKING('B'),
        ;

        char value;

        Pawn(char value) {
            this.value = value;
        }
    }

    enum Player {
        COMPUTER("Копьютер"),
        USER("Человек"),
        INITIAL("Пока не известно"),
        ;

        String player;

        Player(String player) {
            this.player = player;
        }
    }

    public static void main(String[] args) {
        //Обьявляется ход только в первом раунде
        // Если пешка просто походила то некс след пользователь
        // если пешка побила то ходит еще раз пользователь
        //пешки ходят в одном направлении и не бьют назад а дамки могут бить назад и вперед по диагонали
        Random random = new Random();
        Pawn[][] allField;
        Player activPlayer, winner = Player.INITIAL;

        int countWhite = 1;
        int countBlack = 8;


        allField = new Pawn[8][8];

        for (int i = 0; i < allField.length; i++) {
            for (int j = 0; j < allField[i].length; j++) {
                allField[i][j] = Pawn.EMPTY;
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < allField[i].length; j++) { // инициализация белых
                if ((i + j) % 2 == 1 && allField[i][j] == Pawn.EMPTY) {
                    allField[i][j] = Pawn.WHITE;
                }
            }
        }



        for (int i = 4; i == 4; i++) {
            for (int j = 0; j < 5; j++) { // инициализация белых только по центру
                if ((i + j) % 2 == 1 && allField[i][j] == Pawn.EMPTY) {//
                    allField[i][j] = Pawn.WHITE;//
                }
            }
        }



        for (int i = 6; i < allField.length; i++) {
            for (int j = 0; j < allField[i].length; j++) { //инициализация черных
                if ((i + j) % 2 == 1 && allField[i][j] == Pawn.EMPTY) {
                    allField[i][j] = Pawn.BLACK;
                }
            }
        }
        for (int i = 0; i < allField.length; i++) {
            for (int j = 0; j < allField[i].length; j++) {
                System.out.print(allField[i][j].value + " "); // все пешки
            }
            System.out.println();
        }


        int x = random.nextInt(0,10);   // если меньше 5 то компьютер начинает
        if (x >= 5) { // чей ход
            activPlayer = Player.USER;
            System.out.println("Ходят Черные , человек");
        } else {
            activPlayer = Player.COMPUTER;
            System.out.println("Ходят Белые, компьютер");
        }


        int counterRaund = 0; // число ходов
        while (countWhite != 0 || countBlack != 0) {
            Scanner scanner = new Scanner(System.in);
            if (activPlayer == Player.USER) {
                int str = scanner.nextInt();
                int stlb = scanner.nextInt();
                if (allField[str][stlb] == Pawn.BLACK) {
                    int fromStr = scanner.nextInt();
                    int fromStlb = scanner.nextInt();
                    if (allField[fromStr][fromStlb] == Pawn.EMPTY) {
                        if (fromStr == str - 1 && (fromStlb == stlb - 1 || fromStlb == stlb + 1)) {
                            allField[str][stlb] = Pawn.EMPTY;
                            allField[fromStr][fromStlb] = Pawn.BLACK;
                            counterRaund++;
                            activPlayer = Player.COMPUTER;
                            for (int i = 0; i < allField.length; i++) { // дамка черная
                                if (allField[0][i] == (Pawn.BLACK)) {
                                    allField[0][i] = Pawn.BLACKKING;
                                }
                            }
                        }
                    } else if (allField[fromStr][fromStlb] == Pawn.BLACK) {
                        System.out.println("Так нельзя ходить здесь стоит ваша фишка");
                        activPlayer = Player.USER;

                    } else if (allField[fromStr][fromStlb] == Pawn.WHITE || allField[fromStr][fromStlb] == Pawn.WHITEKING) { // пешка черная  шаг
                        int strRazn = fromStr - (str - fromStr); // на сколько строк
                        int stlbRazn = fromStlb - (stlb - fromStlb);// на сколько столбцов
                        if (strRazn >= 0 && strRazn < 8 && stlbRazn >= 0 && stlbRazn < 8) {
                            if (allField[strRazn][stlbRazn] != Pawn.BLACK && allField[strRazn][stlbRazn] != Pawn.WHITE && allField[strRazn][stlbRazn] != Pawn.BLACKKING && allField[strRazn][stlbRazn] != Pawn.WHITEKING) { // если не какая не стоит
                                allField[strRazn][stlbRazn] = Pawn.BLACK;
                                allField[fromStr][fromStlb] = Pawn.DEATH;
                                allField[str][stlb] = Pawn.EMPTY;
                                countWhite--;
                                counterRaund++;
                                activPlayer = Player.USER;
                            }
                        }
                    }
                }
                if (allField[str][stlb] == Pawn.BLACKKING) {// дамка черная
                    int fromStrK = scanner.nextInt();
                    int fromStlbK = scanner.nextInt();
                    if (allField[fromStrK][fromStlbK] == Pawn.EMPTY) {
                        int raznostStr = Math.abs(str - fromStrK);       // узнаем диагональ ли это
                        int raznostStlb = Math.abs(stlb - fromStlbK);
                        if (raznostStr == raznostStlb) {
                            allField[str][stlb] = Pawn.EMPTY;
                            allField[fromStrK][fromStlbK] = Pawn.BLACKKING;
                            counterRaund++;
                            activPlayer = Player.COMPUTER;
                        }
                    } else if (allField[fromStrK][fromStlbK] == Pawn.WHITE || allField[fromStrK][fromStlbK] == Pawn.WHITEKING) {
                        int stepX = (str < fromStrK) ? 1 : -1; // если наша строка меньше той куда идем тогда плюсуем строку
                        int stepY = (stlb < fromStlbK) ? 1 : -1;
                        if (fromStrK >= 0 && fromStrK < 8 && fromStlbK >= 0 && fromStlbK < 8) {
                            if (allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.BLACK && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.WHITE && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.BLACKKING && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.WHITEKING) {
                                allField[fromStrK + stepX][fromStlbK + stepY] = Pawn.BLACKKING;
                                allField[fromStrK][fromStlbK] = Pawn.DEATH;
                                allField[str][stlb] = Pawn.EMPTY;
                                countWhite--;
                                counterRaund++;
                                activPlayer = Player.USER;
                            }
                        }
                    }
                }
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
            if (activPlayer == Player.COMPUTER) { // если компьютер
                int str = scanner.nextInt();
                int stlb = scanner.nextInt();
                if (allField[str][stlb] == Pawn.WHITE) { //пешка белая
                    int fromStr = scanner.nextInt();
                    int fromStlb = scanner.nextInt();
                    if (allField[fromStr][fromStlb] == Pawn.EMPTY) {
                        if (fromStr == str + 1 && (fromStlb == stlb - 1 || fromStlb == stlb + 1)) {
                            allField[str][stlb] = Pawn.EMPTY;
                            allField[fromStr][fromStlb] = Pawn.WHITE;
                            counterRaund++;
                            activPlayer = Player.USER;
                            for (int i = 0; i < allField.length; i++) { // дамка белая
                                if (allField[0][i] == (Pawn.WHITE)) {
                                    allField[0][i] = Pawn.WHITEKING;
                                }
                            }
                        }
                    } else if (allField[fromStr][fromStlb] == Pawn.WHITE) {
                        System.out.println("Так нельзя ходить здесь стоит ваша фишка");
                        activPlayer = Player.COMPUTER;

                    } else if (allField[fromStr][fromStlb] == Pawn.BLACK || allField[fromStr][fromStlb] == Pawn.BLACKKING) {
                        int strRazn = fromStr - (str - fromStr); // на сколько строк  !!!!!!
                        int stlbRazn = fromStlb - (stlb - fromStlb);// на сколько столбцов
                        if (strRazn >= 0 && strRazn < 8 && stlbRazn >= 0 && stlbRazn < 8) {
                            if (allField[strRazn][stlbRazn] != Pawn.BLACK && allField[strRazn][stlbRazn] != Pawn.WHITE && allField[strRazn][stlbRazn] != Pawn.BLACKKING && allField[strRazn][stlbRazn] != Pawn.WHITEKING) { // если не какая не стоит
                                allField[strRazn][stlbRazn] = Pawn.WHITE;
                                allField[fromStr][fromStlb] = Pawn.DEATH;
                                allField[str][stlb] = Pawn.EMPTY;
                                countBlack--;
                                counterRaund++;
                                activPlayer = Player.COMPUTER;
                            }
                        }
                    }
                }
                if (allField[str][stlb] == Pawn.WHITEKING) {// дамка белая
                    int fromStrK = scanner.nextInt();
                    int fromStlbK = scanner.nextInt();
                    if (allField[fromStrK][fromStlbK] == Pawn.EMPTY) {
                        int raznostStr = Math.abs(str - fromStrK);       // узнаем диагональ ли это
                        int raznostStlb = Math.abs(stlb - fromStlbK);
                        if (raznostStr == raznostStlb) {
                            allField[str][stlb] = Pawn.EMPTY;
                            allField[fromStrK][fromStlbK] = Pawn.WHITEKING;
                            counterRaund++;
                            activPlayer = Player.USER;
                        }
                    } else if (allField[fromStrK][fromStlbK] == Pawn.BLACK || allField[fromStrK][fromStlbK] == Pawn.BLACKKING) {
                        int stepX = (str < fromStrK) ? 1 : -1; //                           если наша строка меньше той куда идем тогда плюсуем строку
                        int stepY = (stlb < fromStlbK) ? 1 : -1;
                        if (fromStrK >= 0 && fromStrK < 8 && fromStlbK >= 0 && fromStlbK < 8) {
                            if (allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.BLACK && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.WHITE && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.BLACKKING && allField[fromStrK + stepX][fromStlbK + stepY] != Pawn.WHITEKING) {
                                allField[fromStrK + stepX][fromStlbK + stepY] = Pawn.BLACKKING;
                                allField[fromStrK][fromStlbK] = Pawn.DEATH;
                                allField[str][stlb] = Pawn.EMPTY;
                                countBlack--;
                                counterRaund++;
                                activPlayer = Player.COMPUTER;
                            }
                        }
                    }
                }
                for (int i = 0; i < allField.length; i++) { //обновляет карту компьютера и убирает смерти
                    for (int j = 0; j < allField[i].length; j++) {
                        System.out.print(allField[i][j].value + " ");
                        if (allField[i][j] == Pawn.DEATH) {
                            allField[i][j] = Pawn.EMPTY;
                        }
                    }
                    System.out.println();
                }
            }

            if (countWhite == 0) { //тут победитель
                System.out.println("Победитель " + Player.USER.player);
                break;
            }
            if (countBlack == 0) {
                System.out.println("Победитель " + Player.COMPUTER);
                break;
            }
        }

    }
}




