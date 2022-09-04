import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение(число действие число) и нажмите ENTER!");
        String input = scanner.nextLine();
        String ansver =  calc( input );
        System.out.println(ansver);
    }
    public static String calc(String input) {
        //убираем все пробелы в веденной строке
        String str1 = input.replaceAll(" ", "");
        //получаем тип действия и позицию знака действия в строке без пробеллов
        int index = -1 ;
        char action1 = getTypeOfAction(str1);
        if (action1 == '_') {
            try {
                throw new IOException();
            } catch (IOException e){
                System.out.println("Cтрока не является математической операцией");
                System.exit(0);
            }

        } else {
            index = str1.indexOf(action1);
        }
        //получаем первую переменную(I,II,III,IV,V,VI,VII,VIII,IX,X,1,2,3,4,5,6,7,8,9,10)
        String[] romNums = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] arabNums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int variable1 = -1;
        int variable2 = -1;
        boolean rom = false;//индекс использования римских цифр

        String variable1str = str1.substring(0, index);
        for (int i = 0; i < romNums.length; i++) {
            String variableForCompare = romNums[i];
            if (variable1str.equalsIgnoreCase(variableForCompare)) {
                variable1 = i + 1;
                rom = true;
            }
        }
        for (int i = 0; i < arabNums.length; i++) {
            String variableForCompare = arabNums[i];
            if (variable1str.equalsIgnoreCase(variableForCompare)) {
                variable1 = i + 1;
                rom = false;
            }
        }
        //проверяем правильность введения переменной 1
        if (variable1 == -1) {
            try {
                throw new IOException();
            } catch (IOException e){
                System.out.println("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), программа завершает свою работу");
                System.exit(0);
            }
        }

        //получаем вторую переменную
        String variable2str = str1.substring(index + 1);
        for (int i = 0; i < romNums.length; i++) {
            String variableForCompare = romNums[i];
            if (variable2str.equalsIgnoreCase(variableForCompare)) {
                if (!rom ) {
                    try {
                        throw new IOException();
                    } catch (IOException e){
                        System.out.println("Значения вводимых чисел должны быть однотипными, программа завершает свою работу");
                        System.exit(0);
                    }
                }
                variable2 = i + 1;
            }
        }
        for (int i = 0; i < arabNums.length; i++) {
            String variableForCompare = arabNums[i];
            if (variable2str.equalsIgnoreCase(variableForCompare)) {
                if (rom) {
                    try {
                        throw new IOException();
                    } catch (IOException e){
                        System.out.println("Значения вводимых чисел должны быть однотипными, программа завершает свою работу");
                        System.exit(0);
                    }
                }
                variable2 = i + 1;
            }
        }
        if (variable2 == -1) {
            try {
                throw new IOException();
            } catch (IOException e){
                System.out.println("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *), программа завершает свою работу");
                System.exit(0);
            }
        }
        //производим действия с переменными в обычном виде
        int arabResult = getArabResult(variable1, variable2, action1);
        //если использовались римские цифры и ответ положительный переводим вид ответа в Римский
        String ansverReturn = "";
        if (rom && arabResult > 0) {
            String romResult = IntegerToRomanNumeral(arabResult);
            ansverReturn = "Ответ: " + romResult;
        } else if (!rom) {
            ansverReturn = "Ответ: " + arabResult;
        } else if (arabResult < 0) {
            try {
                throw new IOException();
            } catch (IOException e){
                System.out.println("Полученный результат не допустим в отрицательном виде, программа завершает свою работу");
                System.exit(0);
            }
        }
        return ansverReturn;

    }
    public static char getTypeOfAction(String str1) {
        int index = str1.indexOf("+");
        char actn1 = '_';
        if (index > -1) {
            actn1 = '+';
        }
        if (index == -1) {
            index = str1.indexOf("-");
            if (index > -1) {
                actn1 = '-';
            }
        }
        if (index == -1) {
            index = str1.indexOf("/");
            if (index > -1) {
                actn1 = '/';
            }
        }
        if (index == -1) {
            index = str1.indexOf("*");
            if (index > -1) {
                actn1 = '*';
            }
        }
        return actn1;
    }
    public static int getArabResult(int variable1, int variable2, char action1) {
        int res = 0;
        if (action1 == '+') {
            res = variable1 + variable2;
        }
        if (action1 == '-') {
            res = variable1 - variable2;
        }
        if (action1 == '*') {
            res = variable1 * variable2;
        }
        if (action1 == '/') {
            //проверка деления на 0
            if (variable2 == 0) {
                System.out.println("На ноль делить мы не умеем, программа завершает свою работу");
                System.exit(0);
            }
            res = (variable1 - (variable1 % variable2)) / variable2;
        }
        return res;
    }
    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999) return "Invalid Roman Number Value";
        StringBuilder s = new StringBuilder(" ");
        while (input >= 1000) {
            s.append("M");
            input -= 1000;
        }
        while (input >= 900) {
            s.append("CM");
            input -= 900;
        }
        while (input >= 500) {
            s.append("D");
            input -= 500;
        }
        while (input >= 400) {
            s.append("CD");
            input -= 400;
        }
        while (input >= 100) {
            s.append("C");
            input -= 100;
        }
        while (input >= 90) {
            s.append("XC");
            input -= 90;
        }
        while (input >= 50) {
            s.append("L");
            input -= 50;
        }
        while (input >= 40) {
            s.append("XL");
            input -= 40;
        }
        while (input >= 10) {
            s.append("X");
            input -= 10;
        }
        while (input == 9) {
            s.append("IX");
            input -= 9;
        }
        while (input >= 5) {
            s.append("V");
            input -= 5;
        }
        while (input == 4) {
            s.append("IV");
            input -= 4;
        }
        while (input >= 1) {
            s.append("I");
            input -= 1;
        }
        return s.toString();
    }
}