package com.lingmeng.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eg {

    private String name;
    private String v;
    private String segments;
    private String unit;


    @Override
    public String toString() {
        return "Eg{" +
                "name='" + name + '\'' +
                ", v='" + v + '\'' +
                ", segments='" + segments + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
