package de.bs14.ipcalc.test;

import de.bs14.ipcalc.Calculator;

public class Test {
    public static void main(String[] args) {
        System.out.println(new Calculator("1",0).calcSubnetmaskAsBits(24));
    }
}
