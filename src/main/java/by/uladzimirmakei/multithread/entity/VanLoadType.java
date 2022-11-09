package by.uladzimirmakei.multithread.entity;

public enum VanLoadType {
    EMPTY(0), LITTLE(300), HALF(500), FULL(1000);
    private int goodsAmount;

    VanLoadType(int goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }
}
