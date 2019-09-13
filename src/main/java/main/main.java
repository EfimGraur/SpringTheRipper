package main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoters.Quoter;
import quoters.TerminatorQuoter;

public class main {
  public static void main(String[] args) throws InterruptedException {
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

          Thread.sleep(100);
          context.getBean(Quoter.class).sayQuote();

  }
}
