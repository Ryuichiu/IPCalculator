package de.bs14.ipcalc.test;

import de.bs14.ipcalc.Calculator;

public class Test {
    public static void main(String[] args) {
        var cac = new Calculator();
        var cidr = 25;
        var block = 255;
        System.out.println(cac.calcSubnetmaskAsIp(cidr));
        System.out.println(cac.calcSubnetmaskAsBits(cidr));
        System.out.println("Max Hosts: " + cac.getMaxHosts(cidr));
        System.out.println("CIDR: " + cac.calcCidrFromIp(cac.calcSubnetmaskAsIp(cidr)));
    }
}
