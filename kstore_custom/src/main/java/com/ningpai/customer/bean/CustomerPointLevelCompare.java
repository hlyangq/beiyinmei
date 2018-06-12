package com.ningpai.customer.bean;

import java.util.Comparator;

/**
 * CustomerPointLevelCompare
 * <p/>
 * 会员等级 比较器
 *
 * @author djk
 * @date 2016/3/22
 */
public class CustomerPointLevelCompare implements Comparator<CustomerPointLevel> {
    @Override
    public int compare(CustomerPointLevel o1, CustomerPointLevel o2) {
        return o1.getMaxPoint().compareTo(o2.getMaxPoint());
    }
}
