package ru.yuldashev.learning.mai.logic.snapshotPattern.impl.testObjects;

public class EdgeInfo {
    private final String weight;
    private final String cellName1;
    private final String cellName2;

    public EdgeInfo(String cellName1, String cellName2, String weight) {
        this.weight = weight;
        this.cellName1 = cellName1;
        this.cellName2 = cellName2;
    }

    public String getWeight() {
        return weight;
    }

    public String getCellName1() {
        return cellName1;
    }

    public String getCellName2() {
        return cellName2;
    }
}
