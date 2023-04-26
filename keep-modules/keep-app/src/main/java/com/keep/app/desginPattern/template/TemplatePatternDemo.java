package com.keep.app.desginPattern.template;

public class TemplatePatternDemo {
   public static void main(String[] args) {
      AbstractBankTemplate save = new SaveMoney();
      save.doBankBusiness();

      AbstractBankTemplate take = new TakeMoney();
      take.doBankBusiness();
   }
}