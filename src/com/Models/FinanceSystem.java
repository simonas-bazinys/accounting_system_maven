package com.Models;


public class FinanceSystem {

    private static FinanceSystem financeSystem = new FinanceSystem();

    /* Static 'instance' method */
    public static FinanceSystem getInstance( ) {
        return financeSystem;
    }

    /* Other methods protected by singleton-ness */
    protected static void demoMethod( ) {
        System.out.println("demoMethod for singleton");
    }
}
