package de.bs14.ipcalc;

import javax.swing.*;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

public class Calculator {
    private String adress;
    private int adressAsInt;
    private String adressAsBits;
    private int maxHosts;
    private int cidr;
    private String cidrString;
    private String subnetmask;
    private String range;
    private Function<Object, String> box = a -> JOptionPane.showInputDialog(null, a, "Error", JOptionPane.WARNING_MESSAGE);
    private Supplier<String> input = () -> JOptionPane.showInputDialog(null, "Please enter a valid CIDR", "Error", JOptionPane.WARNING_MESSAGE);

    public Calculator(String adress, int maxHosts) {
        this.adress = adress;
        this.maxHosts = maxHosts;
    }

    public Calculator() {
    }

    public int getMaxHosts(int cidr) {
        return new Double(Math.pow(2, 32-cidr)-2).intValue();
    }

    public String calcAdressAsBits(String ip) {
        adressAsInt = Integer.parseInt(ip.replaceAll(".",""));
        adressAsBits = Integer.toBinaryString(adressAsInt);
        return adressAsBits;
    }

    public int calcCidrFromBits(String subnetmaskAsBits) {
        if (!subnetmaskAsBits.contains("^[.01]")) {
            subnetmaskAsBits.replaceAll(".", "");
            var cidr = 0;
            for (int i = 0; i < subnetmaskAsBits.length(); i++) {
                if (subnetmaskAsBits.charAt(i) == '1') cidr++;
            }
            return cidr;
        }
        return calcCidrFromBits(box.apply("Please enter a valid subnetmask as bits"));
    }

    public int calcCidrFromIp(String subnetmaskAsIp) {
        if (!subnetmaskAsIp.contains("^[0123456789.]")) {
            return calcCidrFromBits(ipToBits(subnetmaskAsIp));
        }
        return calcCidrFromIp(box.apply("Please enter a valid subnetmask as an IP"));
    }

    //needs to be updated
    private String ipToBits(String ip) {
        var sb = new StringBuilder();
        String[] blocks = ip.split("[.]");
        int number;
        for (int i = 0; i < blocks.length; i++) {
            number = Integer.parseInt(blocks[i]);
            for (int j = 0; j < 8; j++) {
                if (number != 0) {
                    sb.append(1);
                    number /= 2;
                } else {
                    sb.append(0);
                }
            }
            if (i < blocks.length-1) sb.append(".");
        }
        return sb.toString();
    }

    public String calcSubnetmaskAsBits(int cidr) {
        try {
            while (!(0 < cidr && cidr < 31)) {
                cidr = Integer.parseInt(input.get());
            }
        } catch (NumberFormatException e) {
            return calcSubnetmaskAsBits(cidr);
        }

        var sb = new StringBuilder();
        int length = 0;
        for (int i = 0; i < cidr; i++) {
            sb.append(1);
            if (++length % 8 == 0 && length < 32) sb.append(".");
        }

        var zeros = 32-cidr;
        for (int i = 0; i < zeros; i++) {
            sb.append(0);
            if (++length % 8 == 0 && length < 32) sb.append(".");
        }

        return sb.toString();
    }

    public String calcSubnetmaskAsBits(String cidrString) {
        try {
             return calcSubnetmaskAsBits(Integer.parseInt(cidrString));
        } catch (NumberFormatException e) {
            return calcSubnetmaskAsBits(input.get());
        }
    }

    public String calcSubnetmaskAsIp(int cidr) {
        try {
            while (!(0 < cidr && cidr < 31)) {
                cidr = Integer.parseInt(input.get());
            }
        } catch (NumberFormatException e) {
            return calcSubnetmaskAsIp(cidr);
        }

        Stack<Integer> bits = new Stack<>();
        Double sum = 0.0;
        var sb = new StringBuilder();
        for (int i = 0; i < 32; i++) if (i >= cidr) bits.add(0); else bits.add(1);
        while (!bits.empty()) {
           sum += bits.pop() * Math.pow(2, 7-(bits.size()%8));
           if (bits.size() % 8 == 0) {
               sb.insert(0, sum.intValue());
               if(!bits.empty())sb.insert(0,".");
               sum = 0.0;
           }
        }
        return sb.toString();
    }

    public String calcSubnetmaskAsIp(String cidrString) {
        try {
            return calcSubnetmaskAsIp(Integer.parseInt(cidrString));
        } catch (NumberFormatException e) {
            return calcSubnetmaskAsIp(input.get());
        }
    }
}