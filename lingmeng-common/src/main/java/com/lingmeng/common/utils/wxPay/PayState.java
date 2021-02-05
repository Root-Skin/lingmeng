package com.lingmeng.common.utils.wxPay;

public enum PayState {
    /**
     * 未支付0
     * 支付成功1
     * 支付失败2
     */
    NOT_PAY(0),
    SUCCESS(1),
    FAIL(2);

    int value;

    PayState(int value) {
        this.value = value;
    }



    public int getValue() {
        return value;
    }
}
