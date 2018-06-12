package com.ningpai.customer.bean;

import com.ningpai.customer.service.CustomerPointServiceMapper;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * CustomerPointLevelTask 会员等级实体 主要功能是 分解给会员设置等级的任务
 *
 * @author djk
 * @date 2016/4/16
 */
public class CustomerPointLevelTask extends RecursiveAction {

    /**
     * 任务开始数
     */
    private int first;

    /**
     * 任务结束数
     */
    private int last;

    /**
     * 会员集合
     */
    private List<Customer> customers;

    /**
     * 会员积分接口
     */
    private CustomerPointServiceMapper customerPointServiceMapper;

    /**
     * 会员等级集合
     */
    private List<CustomerPointLevel> customerPointLevels;

    /**
     * 构造器
     *
     * @param customers                  会员
     * @param customerPointServiceMapper 会员积分接口
     */
    public CustomerPointLevelTask(int first,int last,List<Customer> customers, CustomerPointServiceMapper customerPointServiceMapper, List<CustomerPointLevel> customerPointLevels) {

        this.customerPointLevels = customerPointLevels;
        this.customers = customers;
        this.customerPointServiceMapper = customerPointServiceMapper;
        this.first = first;
        this.last = last;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (last - first < 50) {
            for (int i = first; i < last; i++) {
                Customer customer = customers.get(i);
                CustomerPointLevel customerPointLevel = customerPointServiceMapper.getCustomerPointLevelByPoint(customerPointServiceMapper.getCustomerAllPointSimple(customer.getCustomerId() + ""), customerPointLevels);

                if (null != customerPointLevel) {
                    customer.setPointLevelId(customerPointLevel.getPointLelvelId());
                }
            }
        } else {
            int middle = (last + first) / 2;
            CustomerPointLevelTask left = new CustomerPointLevelTask(first, middle,customers, customerPointServiceMapper, customerPointLevels);
            CustomerPointLevelTask right = new CustomerPointLevelTask(middle, last,customers, customerPointServiceMapper, customerPointLevels);
            invokeAll(left, right);
        }
    }
}
