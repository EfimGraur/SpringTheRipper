package quoters;

import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter {

  public TerminatorQuoter() {
    System.out.println("Phase 1");
    System.out.println(repeat);
  }

  @PostConstruct
  public void init() {
    System.out.println("Phase 2");
    System.out.println(repeat);
  }

  @InjectRandomInt(min = 2, max = 7)
  private int repeat;

  private String message;

  // SPRING XML config prin reflexion foloseste seterul
  public void setMessage(String message) {
    System.out.println("am fost in setter");
    this.message = message;
  }
@PostProxy
  public void sayQuote() {
    System.out.println("Faza 3");
    for (int i = 0; i < repeat; i++) {

      System.out.println("message " + message);
    }
  }
}
