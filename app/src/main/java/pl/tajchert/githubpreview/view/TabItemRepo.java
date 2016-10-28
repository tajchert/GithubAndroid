package pl.tajchert.githubpreview.view;

/**
 * Created by mtajc on 24.10.2016.
 */

public class TabItemRepo {
  public String title;
  public Integer counter;

  public TabItemRepo(String title) {
    this.title = title;
    this.counter = null;
  }

  public TabItemRepo(String title, int counter) {
    this.title = title;
    this.counter = counter;
  }
}
