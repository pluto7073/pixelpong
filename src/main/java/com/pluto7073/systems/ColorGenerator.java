package com.pluto7073.systems;

import java.awt.*;
import java.util.Scanner;

public class ColorGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int r = Integer.parseInt(scanner.nextLine());
        int g = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        Color color = new Color(r, g, b);
        System.out.println(color.getRGB());
    }

}
