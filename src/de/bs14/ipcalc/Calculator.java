package de.bs14.ipcalc;

public class Calculator {
    private String adress;
    private int adressAsInt;
    private String adressAsBits;
    private int maxHosts;
    private int cidr;
    private String cidrString;
    private String subnetmask;
    private String range;

    public Calculator(String adress, int maxHosts) {
        this.adress = adress;
        this.maxHosts = maxHosts;
    }

    public String calcAdressAsBits() {
        adressAsInt = Integer.parseInt(adress.replaceAll(".",""));
        adressAsBits = Integer.toBinaryString(adressAsInt);
        return adressAsBits;
    }

    public String calcSubnetmask(int cidr) {
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
}
